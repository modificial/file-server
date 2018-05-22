package com.codingapi.fileserver.api.service;

import com.lorne.core.framework.exception.ServiceException;

/**
 * @author modificial
 * @date 2018/4/8
 * @company codingApi
 * @description 文件删除
 */
public interface ApiFileDeleteService {
    /**
     * 根据key值删除文件
     *
     * @param key 文件密钥
     * @return 0 文件删除成功 失败返回其他
     * @throws ServiceException 可能抛出业务异常
     */
    int delete(String key) throws ServiceException;
}
