package com.codingapi.fileserver.api.service;

import com.codingapi.netflix.framework.exception.ServerFeignException;
import com.lorne.core.framework.exception.ServiceException;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;

/**
 * @author modificial
 * @date 2018/4/8
 * @company codingApi
 * @description 文件下载
 */
public interface ApiFileDownloadService {
    /**
     * 根据key值下载文件
     *
     * @param key 文件密钥
     * @return 二进制数据
     * @throws ServiceException 可能抛出业务异常
     */
    ResponseEntity<InputStreamResource> download(String key) throws ServiceException, ServerFeignException;

}
