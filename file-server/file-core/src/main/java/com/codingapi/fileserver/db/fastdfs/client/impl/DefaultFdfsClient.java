package com.codingapi.fileserver.db.fastdfs.client.impl;

import com.codingapi.fileserver.db.fastdfs.client.FdfsClient;
import com.codingapi.fileserver.exception.fastDFSException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.Map;

/**
 * @author modificial
 * @date 2018/4/4
 * @company codingApi
 * @description FastDFS默认实现
 */
@Component
public class DefaultFdfsClient extends AbstractFdfsClient implements FdfsClient {
    /**
     * org.slf4j.Logger
     * 用于打印日志信息
     */
    private static Logger logger = LoggerFactory.getLogger(DefaultFdfsClient.class);

    /**
     * 上传通用方法
     *
     * @param is  文件输入流
     * @param ext 文件后缀
     * @return 上传文件基本信息
     * @throws fastDFSException
     */
    @Override
    public String upload(InputStream is, String ext) throws fastDFSException {
        return super.upload(is, ext);
    }

    /**
     * 下载文件
     *
     * @param filepath 文件路径
     * @return 返回文件字节
     * @throws fastDFSException
     */
    @Override
    public byte[] download(String filepath) throws fastDFSException {

        return super.download(filepath);
    }

    /**
     * 删除文件
     *
     * @param filepath 文件路径
     * @return 删除成功返回 0, 失败返回其它
     */
    @Override
    public int deleteFile(String filepath) throws fastDFSException {

        return super.deleteFile(filepath);
    }

    /**
     * 根据文件路径获取文件描述信息
     *
     * @param filepath 文件路径
     * @return
     * @throws fastDFSException
     */
    @Override
    public Map<String, Object> getFileDescriptions(String filepath) throws fastDFSException {

        return super.getFileDescriptions(filepath);
    }

    /**
     * 根据文件路径获取文件原始名字
     *
     * @param filepath 文件路径
     * @return 文件基本信息
     * @throws fastDFSException
     */
    @Override
    public String getOriginalFilename(String filepath) throws fastDFSException {
        return super.getOriginalFilename(filepath);
    }
}
