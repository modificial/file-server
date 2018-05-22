package com.codingapi.fileserver.service;

import com.codingapi.fileserver.db.mysql.entity.FileInfo;
import com.lorne.core.framework.exception.ServiceException;

/**
 * @author modificial
 * @date 2018/5/15 0015
 * @company codingApi
 * @description
 */
public interface FileInfoService {
    /**
     * 查询存储文件信息
     *
     * @param key 文件访问秘钥
     * @return
     */
    FileInfo getFileInfoByKey(String key) throws ServiceException;

    /**
     * 保存文件信息
     *
     * @param fileInfo 文件信息
     * @return
     */
    int saveFileInfo(FileInfo fileInfo) throws ServiceException;

    /**
     * 删除文件信息
     *
     * @param key 文件访问秘钥
     * @return
     */
    int deleteFileInfoByKey(String key) throws ServiceException;

    /**
     * 更新文件信息
     *
     * @param fileInfo 文件信息
     * @return
     */
    int updateFileInfo(FileInfo fileInfo) throws ServiceException;
}
