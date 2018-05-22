package com.codingapi.fileserver.service.impl;

import com.codingapi.fileserver.api.service.util.FileCreateUtils;
import com.codingapi.fileserver.ato.ao.FileInfoReq;
import com.codingapi.fileserver.ato.vo.FileCheckResult;
import com.codingapi.fileserver.db.fastdfs.utils.FileCheckUtils;
import com.codingapi.fileserver.db.fastdfs.utils.SnowFlakeGenerator;
import com.codingapi.fileserver.service.FileCheckService;
import com.lorne.core.framework.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Objects;

/**
 * @author modificial
 * @date 2018/4/19 0019
 * @company codingApi
 * @description 检查文件是否需要分片上传
 */
@Service
public class FileCheckServiceImpl implements FileCheckService {
    /**
     * 打印日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(FileCheckServiceImpl.class);
    /**
     * 分块大小
     */
    private static final Integer blockSize = 1024 * 1024;
    /**
     * 临时文件目录
     */
    private static final String tempDir = "/opt/temp";

    /**
     * 判断文件是否需要分片上传
     *
     * @param fileInfoReq 文件检验参数
     * @return
     */
    @Override
    public FileCheckResult check(FileInfoReq fileInfoReq) {
        int result = FileCheckUtils.checkFileSize(fileInfoReq.getFileSize());
        String key = SnowFlakeGenerator.nextId();
        FileCheckResult fileCheckResult = new FileCheckResult();
        fileCheckResult.setIsFragmentation(result);
        if (result == 1) {
            fileCheckResult.setBlockSize(blockSize);
            int number = (int) ((fileInfoReq.getFileSize() % blockSize == 0) ? (fileInfoReq.getFileSize() / blockSize) : (fileInfoReq
                    .getFileSize() / blockSize + 1));
            fileCheckResult.setBlockNumber(number);
        }
        fileCheckResult.setKey(key);
        return fileCheckResult;
    }

    /**
     * 检验文件md5值的有效性
     *
     * @param tempLists 临时文件保存路径列表
     * @param md5       原始文件md5值
     * @param fileType  文件类型
     * @return
     * @throws ServiceException
     */
    @Override
    public boolean checkSign(List<String> tempLists, String md5, String fileType) throws ServiceException {
        File temp = FileCreateUtils.createFile(tempDir + File.separator + System.currentTimeMillis() + "." + fileType);
        FileCreateUtils.merge(temp.getAbsolutePath(), tempLists);
        try {
            return Objects.deepEquals(md5, DigestUtils.md5DigestAsHex(Files.newInputStream(temp.toPath())));
        } catch (IOException e) {
            LOGGER.error("读取文件发生错误");
            throw new ServiceException("读取文件发生错误");
        } finally {
            LOGGER.info("开始删除临时文件{}" + tempLists);
            //通知垃圾回收器回收无用对象，避免删除临时文件失败
            System.gc();
            FileCreateUtils.deleteAll(tempLists);
        }
    }
}
