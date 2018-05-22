package com.codingapi.fileserver.api.service.util;

import com.codingapi.fileserver.ato.ao.*;
import com.lorne.core.framework.exception.ServiceException;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;

/**
 * @author Administrator
 * @date 2018/4/20 0020
 * @company codingApi
 * @description 验证参数工具类
 */
public class VertifyUtil {
    /**
     * 打印日志信息
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(VertifyUtil.class);

    /**
     * 验证参数的有效性
     *
     * @param fileInfoReq
     * @throws ServiceException
     */
    public static void vertifyFileInfoReq(FileInfoReq fileInfoReq) throws ServiceException {
        if (fileInfoReq == null || fileInfoReq.getFileSize() < 0 || StringUtils.isEmpty(fileInfoReq.getFileName()) || StringUtils.isEmpty(fileInfoReq
                .getMd5FileStr())
                || StringUtils.isEmpty(fileInfoReq.getFileType())) {
            LOGGER.error("参数不完整或者不合法");
            throw new ServiceException("参数不完整或者不合法");
        }
    }

    /**
     * 检验文件上传参数有效性
     *
     * @param chunckReq
     * @throws ServiceException
     */
    public static void vertifyParams(ChunckReq chunckReq) throws ServiceException {
        if (chunckReq == null || StringUtils.isEmpty(chunckReq.getMd5()) || StringUtils.isEmpty(chunckReq.getKey())
                || chunckReq.getBlockIndex() == null || chunckReq.getBlockNum() == null) {
            LOGGER.error("请求参数不完整");
            throw new ServiceException("参数不完整");
        }
        if (StringUtils.isEmpty(chunckReq.getToken())) {
            LOGGER.warn("token 不能为空");
            throw new ServiceException("token 不能为空");
        }
        if (chunckReq.getBlockIndex() > chunckReq.getBlockNum()) {
            LOGGER.error("分片索引不能大于总片数");
            throw new ServiceException("分片索引不能大于总片数");
        }
    }

    /**
     * 检验传入的md5和真实存储文件的md5是否相同
     *
     * @param inputStream
     * @param md5
     * @param tempDir     临时文件目录
     * @return
     * @throws ServiceException
     */
    public static TempFileRes checkMd5(String tempDir, InputStream inputStream, String md5) throws ServiceException {
        File tempDictionary = new File(tempDir);
        try {
            if (!tempDictionary.exists()) {
                tempDictionary.mkdirs();
            }
            File temp = new File(tempDictionary, System.currentTimeMillis() + "");
            Files.copy(inputStream, temp.toPath(), StandardCopyOption.REPLACE_EXISTING);
            String realMd5 = DigestUtils.md5DigestAsHex(Files.newInputStream(temp.toPath(), StandardOpenOption.CREATE));
            TempFileRes tempFileRes = new TempFileRes();
            tempFileRes.setValid(realMd5.equals(md5));
            tempFileRes.setTempFilePath(temp.getAbsolutePath());
            return tempFileRes;
        } catch (IOException e) {
            LOGGER.error("临时存储文件失败");
            throw new ServiceException("临时存储文件失败");
        } finally {
            if (inputStream != null) {
                try {
                    LOGGER.info("开始关闭文件输入流");
                    inputStream.close();
                } catch (IOException e) {
                    LOGGER.error("文件流关闭失败");
                    throw new ServiceException("文件流关闭失败");
                } finally {
                    IOUtils.closeQuietly(inputStream);
                }
            }
        }
    }

    public static boolean checkMd5(InputStream inputStream, String md5) {
        if (!StringUtils.hasText(md5)) {
            return false;
        }
        String realMd5 = "";
        try {
            realMd5 = DigestUtils.md5DigestAsHex(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
        return md5.equals(realMd5);
    }

    /**
     * 检查请求参数的有效性
     *
     * @param fileParams 请求参数
     * @throws ServiceException
     */
    public static void vertify(FileParams fileParams) throws ServiceException {
        if (fileParams == null || fileParams.getInputStreamCacher() == null) {
            LOGGER.error("请求参数不完整{}" + fileParams);
            throw new ServiceException("请求参数不完整");
        }
    }

    public static void vertify(String key, String token, Integer width, Integer height) throws ServiceException {
        if (StringUtils.isEmpty(key)) {
            LOGGER.error("key值不能为空");
            throw new ServiceException("key值不能为空");
        }
        if (width == null || height == null) {
            LOGGER.error("图片高度或宽度未指定");
            throw new ServiceException("图片高度或宽度未指定");
        }
        if (width <= 0 || height <= 0) {
            LOGGER.error("图片高度或宽度值不合法");
            throw new ServiceException("图片高度或宽度值不合法");
        }
    }

    public static void vertify(FileUploadReq fileUploadReq) throws ServiceException {
        if (fileUploadReq == null) {
            LOGGER.error("没有要上传的文件");
            throw new ServiceException("没有要上传的文件");
        }
        if (StringUtils.isEmpty(fileUploadReq.getMd5())) {
            LOGGER.error("文件md5值是必须的");
            throw new ServiceException("文件md5值是必须的");
        }
        if (StringUtils.isEmpty(fileUploadReq.getToken())) {
            LOGGER.error("token的值不合法");
            throw new ServiceException("token的值不合法");
        }
    }
}
