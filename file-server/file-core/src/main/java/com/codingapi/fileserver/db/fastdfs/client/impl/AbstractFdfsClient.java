package com.codingapi.fileserver.db.fastdfs.client.impl;

import com.codingapi.fileserver.db.fastdfs.pool.TrackerServerPool;
import com.codingapi.fileserver.exception.ResponseCode;
import com.codingapi.fileserver.exception.fastDFSException;
import org.apache.commons.lang.StringUtils;
import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.StorageClient1;
import org.csource.fastdfs.TrackerServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import static com.codingapi.fileserver.db.fastdfs.client.FdfsClient.FILENAME;

/**
 * @author modificial
 * @date 2018/4/4
 * @company codingApi
 * @description 基于Fdfs实现上传下载客户端，为抽象类，用户可以继承此类继续扩展
 */
@ConfigurationProperties("classpath:application.properties")
public abstract class AbstractFdfsClient {
    public static final String PATTERN = "yyyy-MM-dd HH:mm:ss";
    /**
     * 路径分隔符
     */
    public static final String SEPARATOR = "/";
    /**
     * Point
     */
    public static final String POINT = ".";
    /**
     * 用于日志的打印
     */
    private static final Logger logger = LoggerFactory.getLogger(AbstractFdfsClient.class);

    protected AbstractFdfsClient() {

    }

    @Value("${fastdfs.maxFileSize}")
    protected long maxFileSize;

    /**
     * 重载，根据指定文件，文件描述上传
     *
     * @param file 文件
     * @return 文件上传后的文件基本信息
     * @throws fastDFSException
     */
    protected String upload(File file) throws fastDFSException, FileNotFoundException {
        return upload(new FileInputStream(file), null);
    }

    /**
     * 上传通用方法
     *
     * @param is  文件输入流
     * @param ext 文件后缀
     * @return 文件上传后的基本信息
     * @throws fastDFSException
     */
    protected String upload(InputStream is, String ext) throws fastDFSException {
        String path = null;
        if (is == null) {
            throw new fastDFSException(ResponseCode.FILE_ISNULL.code, ResponseCode.FILE_ISNULL.message);
        }

        try {
            if (is.available() > maxFileSize) {
                throw new fastDFSException(ResponseCode.FILE_OUT_SIZE.code, ResponseCode.FILE_OUT_SIZE.message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        TrackerServer trackerServer = TrackerServerPool.borrowObject();
        StorageClient1 storageClient = new StorageClient1(trackerServer, null);
        try {
            // 读取流
            byte[] fileBuff = new byte[is.available()];
            is.read(fileBuff, 0, fileBuff.length);

            // 上传
            path = storageClient.upload_file1(fileBuff, ext, null);

            if (StringUtils.isBlank(path)) {
                throw new fastDFSException(ResponseCode.FILE_UPLOAD_FAILED.code, ResponseCode.FILE_UPLOAD_FAILED.message);
            }

            if (logger.isDebugEnabled()) {
                logger.debug("upload file success, return path is {}", path);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new fastDFSException(ResponseCode.FILE_UPLOAD_FAILED.code, ResponseCode.FILE_UPLOAD_FAILED.message);
        } catch (MyException e) {
            e.printStackTrace();
            throw new fastDFSException(ResponseCode.FILE_UPLOAD_FAILED.code, ResponseCode.FILE_UPLOAD_FAILED.message);
        } finally {
            // 关闭流
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        // 返还对象
        TrackerServerPool.returnObject(trackerServer);
        return path;
    }

    /**
     * 下载文件
     *
     * @param filepath 文件路径
     * @return 返回文件字节
     * @throws fastDFSException
     */
    protected byte[] download(String filepath) throws fastDFSException {
        if (StringUtils.isBlank(filepath)) {
            throw new fastDFSException(ResponseCode.FILE_PATH_ISNULL.code, ResponseCode.FILE_PATH_ISNULL.message);
        }

        TrackerServer trackerServer = TrackerServerPool.borrowObject();
        StorageClient1 storageClient = new StorageClient1(trackerServer, null);
        byte[] fileByte = null;
        try {
            fileByte = storageClient.download_file1(filepath);

            if (fileByte == null) {
                throw new fastDFSException(ResponseCode.FILE_NOT_EXIST.code, ResponseCode.FILE_NOT_EXIST.message);
            }
        } catch (IOException e) {
            if (logger.isErrorEnabled()) {
                logger.error("发生了IO异常");
            }
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
            throw new fastDFSException(ResponseCode.FILE_DOWNLOAD_FAILED.code, ResponseCode.FILE_DOWNLOAD_FAILED.message);
        }
        // 返还对象
        TrackerServerPool.returnObject(trackerServer);

        return fileByte;
    }

    /**
     * 删除文件
     *
     * @param filepath 文件路径
     * @return 删除成功返回 0, 失败返回其它
     */
    protected int deleteFile(String filepath) throws fastDFSException {
        if (StringUtils.isBlank(filepath)) {
            throw new fastDFSException(ResponseCode.FILE_PATH_ISNULL.code, ResponseCode.FILE_PATH_ISNULL.message);
        }

        TrackerServer trackerServer = TrackerServerPool.borrowObject();
        StorageClient1 storageClient = new StorageClient1(trackerServer, null);
        int success = 0;
        try {
            success = storageClient.delete_file1(filepath);
        } catch (IOException e) {
            if (logger.isErrorEnabled()) {
                logger.error("发生了IO异常");
            }
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
            throw new fastDFSException(ResponseCode.FILE_DELETE_FAILED.code, ResponseCode.FILE_DELETE_FAILED.message);
        }
        // 返还对象
        TrackerServerPool.returnObject(trackerServer);

        return success;
    }

    /**
     * 获取文件描述信息
     *
     * @param filepath 文件路径
     * @return 文件描述信息
     */
    protected Map<String, Object> getFileDescriptions(String filepath) throws fastDFSException {
        TrackerServer trackerServer = TrackerServerPool.borrowObject();
        StorageClient1 storageClient = new StorageClient1(trackerServer, null);
        NameValuePair[] nvps = null;
        try {
            nvps = storageClient.get_metadata1(filepath);
        } catch (IOException e) {
            if (logger.isErrorEnabled()) {
                logger.error("发生了IO异常");
            }
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }
        // 返还对象
        TrackerServerPool.returnObject(trackerServer);

        Map<String, Object> infoMap = null;

        if (nvps != null && nvps.length > 0) {
            infoMap = new HashMap<>(nvps.length);

            for (NameValuePair nvp : nvps) {
                infoMap.put(nvp.getName(), nvp.getValue());
            }
        }

        return infoMap;
    }

    /**
     * 获取源文件的文件名称
     *
     * @param filepath 文件的路径
     * @return 文件名称
     */
    protected String getOriginalFilename(String filepath) throws fastDFSException {
        Map<String, Object> descriptions = getFileDescriptions(filepath);
        if (descriptions == null || descriptions.isEmpty()) {
            File file = new File(filepath);
            return file.getName();
        }
        if (descriptions.get(FILENAME) != null) {
            return (String) descriptions.get(FILENAME);
        }
        if (logger.isWarnEnabled()) {
            logger.warn("返回文件原始名称失败");
        }
        return null;
    }
}
