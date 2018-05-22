package com.codingapi.fileserver.db.fastdfs.client;

import com.codingapi.fileserver.exception.fastDFSException;

import java.io.InputStream;

/**
 * @author modificial
 * @date 2018/4/4
 * @company codingApi
 * @description
 */
public interface FdfsClient {
    /**
     * Point
     */
    String POINT = ".";

    /**
     * 文件名称Key
     */
    String FILENAME = "filename";

    /**
     * 上传文件
     *
     * @param is  流对象
     * @param ext 文件后缀
     * @return 文件上传后的基本信息
     * @throws fastDFSException
     */
    String upload(InputStream is, String ext) throws fastDFSException;

    /**
     * 下载文件
     *
     * @param filepath 文件路径
     * @return 返回文件字节
     * @throws fastDFSException
     */
    byte[] download(String filepath) throws fastDFSException;

    /**
     * 删除文件
     *
     * @param filepath 文件路径
     * @return 删除成功返回 0, 失败返回其它
     * @throws fastDFSException
     */
    int deleteFile(String filepath) throws fastDFSException;

}
