package com.codingapi.fileserver.api.service.impl;

import com.codingapi.fileserver.api.service.ApiFileDownloadService;
import com.codingapi.fileserver.db.mysql.entity.FileInfo;
import com.codingapi.fileserver.service.FastDfsService;
import com.codingapi.fileserver.service.FileInfoService;
import com.lorne.core.framework.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

/**
 * @author modificial
 * @date 2018/4/8
 * @company codingApi
 * @description 文件下载
 */
@Service
public class ApiFileDownloadServiceImpl implements ApiFileDownloadService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApiFileDownloadServiceImpl.class);
    @Autowired
    private FileInfoService fileInfoService;
    @Autowired
    private FastDfsService fastDfsService;

    @Override
    public ResponseEntity<InputStreamResource> download(String key) throws ServiceException {
        String filepaths;
        String fileName = null;
        String ext = null;
        FileInfo fileInfo = fileInfoService.getFileInfoByKey(key);
        filepaths = fileInfo.getBlockPath();
        fileName = fileInfo.getOrginFileName();
        ext = fileInfo.getFileType();
        String filePath = fastDfsService.download(Arrays.asList(filepaths.split(",")), ext);
        FileSystemResource file = new FileSystemResource(filePath);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", fileName));
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        InputStream inputStream = null;
        ResponseEntity<InputStreamResource> resourceResponseEntity;
        try {
            inputStream = file.getInputStream();
            resourceResponseEntity = ResponseEntity
                    .ok()
                    .headers(headers)
                    .contentLength(file.contentLength())
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(new InputStreamResource(inputStream));
            return resourceResponseEntity;
        } catch (IOException e) {
            throw new ServiceException("下载文件失败");
        } finally {
            LOGGER.warn("下载文件完毕");
        }
    }
}

