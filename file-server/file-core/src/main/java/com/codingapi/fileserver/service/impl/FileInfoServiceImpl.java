package com.codingapi.fileserver.service.impl;

import com.codingapi.fileserver.db.mysql.dao.FileInfoDao;
import com.codingapi.fileserver.db.mysql.entity.FileInfo;
import com.codingapi.fileserver.service.FileInfoService;
import com.lorne.core.framework.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @author modificial
 * @date 2018/5/15 0015
 * @company codingApi
 * @description 文件信息存储
 */
@Service
public class FileInfoServiceImpl implements FileInfoService {
    /**
     * 打印日志信息
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(FileInfoServiceImpl.class);

    @Autowired
    private FileInfoDao fileInfoDao;

    /**
     * @param key 文件访问秘钥
     * @return
     */
    @Override
    public FileInfo getFileInfoByKey(String key) throws ServiceException {
        if (StringUtils.isEmpty(key)) {
            LOGGER.error("文件访问秘钥不能为空");
            throw new ServiceException("文件访问秘钥不能为空");
        }
        return fileInfoDao.getFileInfoByKey(key);
    }

    /**
     * 保存文件信息
     *
     * @param fileInfo 文件信息
     * @return
     * @throws ServiceException
     */
    @Override
    public int saveFileInfo(FileInfo fileInfo) throws ServiceException {
        if (fileInfo == null) {
            LOGGER.error("文件信息不能为空");
            throw new ServiceException("文件信息不能为空");
        }

        return fileInfoDao.insertFileInfo(fileInfo);
    }

    /**
     * 删除文件信息
     *
     * @param key 文件访问秘钥
     * @return
     * @throws ServiceException
     */
    @Override
    public int deleteFileInfoByKey(String key) throws ServiceException {
        if (StringUtils.isEmpty(key)) {
            LOGGER.error("文件访问秘钥不能为空");
            throw new ServiceException("文件访问秘钥不能为空");
        }
        return fileInfoDao.deleteFileInfoByKey(key);
    }

    /**
     * 更新文件信息
     *
     * @param fileInfo 文件信息
     * @return
     * @throws ServiceException
     */
    @Override
    public int updateFileInfo(FileInfo fileInfo) throws ServiceException {
        if (fileInfo == null) {
            LOGGER.error("文件信息不能为空");
            throw new ServiceException("文件信息不能为空");
        }
        return fileInfoDao.updateFileInfo(fileInfo);
    }
}
