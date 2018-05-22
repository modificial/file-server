package com.codingapi.fileserver.service;

import com.codingapi.fileserver.ato.ao.FileParams;
import com.codingapi.fileserver.ato.ao.ImageParam;
import com.codingapi.fileserver.ato.ao.UploadInfo;
import com.codingapi.fileserver.db.mysql.entity.ImageInfo;
import com.lorne.core.framework.exception.ServiceException;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * @author modificial
 * @date 2018/4/5 0005
 * @company codingApi
 * @description 文件 上传下载业务层
 */
public interface FastDfsService {
    /**
     * 根据流对象上传文件
     *
     * @param fileParams 上传文件参数
     * @return 文件上传后的基本信息
     * @throws ServiceException 可能抛出业务异常
     */
    UploadInfo upload(FileParams fileParams) throws ServiceException;

    /**
     * 上传小文件
     *
     * @param inputStream 文件输入流
     * @param ext         文件后缀
     * @param md5         文件MD5值
     * @return
     * @throws ServiceException
     */
    UploadInfo upload(InputStream inputStream, String ext, String md5) throws ServiceException;

    /**
     * 下载文件
     *
     * @param filepaths 文件路径
     * @param ext       文件后缀
     * @return 返回文件字节
     * @throws ServiceException 可能抛出业务异常
     */
    String download(List<String> filepaths, String ext) throws ServiceException;

    /**
     * 删除文件
     *
     * @param filepath 文件路径
     * @return 删除成功返回 0, 失败返回其它
     * @@throws ServiceException 可能抛出业务异常
     */
    int deleteFile(List<String> filepath) throws ServiceException;

    /**
     * 上传图片
     *
     * @param imageParam 图片参数
     * @return
     * @throws ServiceException
     */
    ImageInfo uploadImage(ImageParam imageParam) throws ServiceException;

    /**
     * 下载文件
     *
     * @param filePath
     * @param ext
     * @return
     * @throws ServiceException
     */
    Map<String, File> downloadImage(String filePath, String ext) throws ServiceException;

    /**
     * 上传图片根据url
     * @param url 图片url
     * @return
     * @throws ServiceException
     */
    ImageInfo uploadImage(String url) throws ServiceException;

}
