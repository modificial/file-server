package com.codingapi.fileserver.api.service.impl;

import com.codingapi.fileserver.api.service.ApiFileDeleteService;
import com.codingapi.fileserver.db.mysql.entity.FileInfo;
import com.codingapi.fileserver.service.FastDfsService;
import com.codingapi.fileserver.service.FileInfoService;
import com.lorne.core.framework.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * @author modificial
 * @date 2018/4/8
 * @company codingApi
 * @description 删除某个文件
 */
@Service
public class ApiFileDeleteServiceImpl implements ApiFileDeleteService {
    /**
     * 打印日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ApiFileDeleteServiceImpl.class);
    @Autowired
    private FileInfoService fileInfoService;

    @Autowired
    private FastDfsService fastDfsService;

    @Override
    public int delete(String key) throws ServiceException {
        int result = 0;

        FileInfo fileInfo = fileInfoService.getFileInfoByKey(key);

        if (fileInfo != null) {
            LOGGER.info("查询文件信息成功{}" + fileInfo);
            String filepaths = fileInfo.getBlockPath();

            String[] filepath = filepaths.split(",");

            result = fastDfsService.deleteFile(Arrays.asList(filepath));
            LOGGER.info("开始返回删除结果{" + result + "}");
        }
        return result;
    }
}
