package com.codingapi.fileserver.api.service.impl;

import com.codingapi.fileserver.api.service.ApiFileUploadService;
import com.codingapi.fileserver.api.service.util.VertifyUtil;
import com.codingapi.fileserver.ato.ao.*;
import com.codingapi.fileserver.ato.vo.FileCheckResult;
import com.codingapi.fileserver.ato.vo.FileResponseData;
import com.codingapi.fileserver.db.fastdfs.utils.InputStreamCacher;
import com.codingapi.fileserver.db.fastdfs.utils.SnowFlakeGenerator;
import com.codingapi.fileserver.db.mysql.entity.FileInfo;
import com.codingapi.fileserver.exception.ResponseCode;
import com.codingapi.fileserver.service.FastDfsService;
import com.codingapi.fileserver.service.FileCheckService;
import com.codingapi.fileserver.service.FileInfoService;
import com.lorne.core.framework.exception.ServiceException;
import com.lorne.core.framework.utils.DateUtil;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

/**
 * @author modificial
 * @date 2018/4/8
 * @company codingApi
 * @description 文件上传主类
 */
@Service
public class ApiFileUploadServiceImpl implements ApiFileUploadService {
    /**
     * 打印日志信息
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ApiFileUploadServiceImpl.class);
    @Autowired
    private FileInfoService fileInfoService;
    @Autowired
    private FastDfsService fastDfsService;
    @Autowired
    private FileCheckService fileCheckService;


    /**
     * 分块上传
     *
     * @param chunckReq 分块信息
     * @return
     * @throws ServiceException
     */
    @Override
    public FileResponseData upload(ChunckReq chunckReq) throws ServiceException {
        VertifyUtil.vertifyParams(chunckReq);
        FileInfo fileInfo = fileInfoService.getFileInfoByKey(chunckReq.getKey());
        InputStreamCacher inputStreamCacher = null;
        try {
            inputStreamCacher = new InputStreamCacher(chunckReq.getMultipartFile().getInputStream());
        } catch (IOException e) {
            LOGGER.error("获取文件输入流失败");
            throw new ServiceException("获取上传输入流错误");
        }
        FileParams fileParams = new FileParams(inputStreamCacher, chunckReq.getBlockIndex(), chunckReq.getBlockNum(), chunckReq.getMd5(),
                fileInfo.getFileType());
        UploadInfo uploadInfo = null;
        uploadInfo = fastDfsService.upload(fileParams);
        //拼装返回数据
        FileResponseData fileResponseData = FileInfo.getFileResponseData(fileInfo);
        fileResponseData = FileResponseData.createFileResponseData("上传文件失败",
                ResponseCode.FILE_UPLOAD_FAILED, false, fileResponseData);
        //MD5检验失败直接返回失败信息
        if (!uploadInfo.isValid()) {
            LOGGER.debug("上传文件失败，请重新上传");
            return fileResponseData;
        }
        //若本次上传成功，保存每一次上传数据
        if (uploadInfo.isUploaded()) {
            FileInfo fileInfo1 = fileInfoService.getFileInfoByKey(chunckReq.getKey());
            fileResponseData = FileResponseData.createFileResponseData("本次上传文件成功", ResponseCode.FILE_SUCCESS_UPLOAD,
                    true, fileResponseData);
            StringBuilder stringBuilder = new StringBuilder(fileInfo1.getBlockPath());
            if (StringUtils.hasText(uploadInfo.getFileId())) {
                stringBuilder.append(uploadInfo.getFileId() + ",");
            }
            StringBuilder stringBuilder1 = new StringBuilder(fileInfo1.getTempPath());
            if (StringUtils.hasText(uploadInfo.getTempPath())) {
                stringBuilder.append(uploadInfo.getTempPath() + ",");
            }
            fileInfo1.setTempPath(stringBuilder1.toString());
            fileInfo1.setBlockPath(stringBuilder.toString());
            fileInfoService.updateFileInfo(fileInfo1);
            LOGGER.debug("本次上传文件成功，开始返回数据{}" + fileResponseData);
            if (uploadInfo.isUploadAll()) {
                FileInfo fileInfo2 = fileInfoService.getFileInfoByKey(chunckReq.getKey());
                fileInfo2.setBlockPath(fileInfo2.getBlockPath().substring(0, fileInfo2.getBlockPath().lastIndexOf(",")));
                fileInfo2.setTempPath(fileInfo2.getTempPath().substring(0, fileInfo2.getTempPath().lastIndexOf(",")));
                fileInfoService.updateFileInfo(fileInfo2);
                boolean isSigined = false;
                String[] tempPaths = fileInfo1.getTempPath().split(",");
                List<String> tempList0 = Arrays.asList(tempPaths);
                isSigined = fileCheckService.checkSign(tempList0, fileInfo.getMd5FileStr(), fileInfo.getFileType());
                if (!isSigined) {
                    return fileResponseData;
                }
                fileResponseData = FileResponseData.createFileResponseData("上传所有文件成功", ResponseCode.FILE_SUCCESS_UPLOAD,
                        true, fileResponseData);
                LOGGER.debug("上传所有文件成功，开始返回数据{}" + fileResponseData);
                return fileResponseData;
            }
            return fileResponseData;
        }
        LOGGER.debug("上传文件失败，开始返回数据{}" + fileResponseData);
        return fileResponseData;
    }

    /**
     * 检测文件是否需要分块上传
     *
     * @param fileInfoReq 文件详细信息
     * @return
     * @throws ServiceException
     */
    @Override
    public FileCheckResult check(FileInfoReq fileInfoReq) throws ServiceException {
        LOGGER.info("开始检查文件是否需要分块上传");
        VertifyUtil.vertifyFileInfoReq(fileInfoReq);
        FileInfo fileInfo = FileInfoReq.toBeans(fileInfoReq);
        FileCheckResult fileCheckResult = fileCheckService.check(fileInfoReq);
        fileInfoService.saveFileInfo(fileInfo);
        LOGGER.info("开始返回检查数据{" + fileCheckResult + "}");
        return fileCheckResult;
    }

    /**
     * 无需分块直接上传
     *
     * @param fileUploadReq 上传文件必要参数
     * @return
     * @throws ServiceException
     */
    @Override
    public FileResponseData upload(FileUploadReq fileUploadReq) throws ServiceException {
        //首先验证参数的合法性
        VertifyUtil.vertify(fileUploadReq);
        MultipartFile multipartFile = fileUploadReq.getMultipartFile();
        String md5 = fileUploadReq.getMd5();
        InputStream inputStream = null;
        try {
            inputStream = multipartFile.getInputStream();
        } catch (IOException e) {
            LOGGER.error("获取文件输入流失败");
            throw new ServiceException("获取文件输入流失败");
        }
        String ext = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
        UploadInfo uploadInfo = fastDfsService.upload(inputStream, ext, md5);
        if (uploadInfo.isUploaded() && uploadInfo.isValid()) {
            String key = SnowFlakeGenerator.nextId();
            FileResponseData fileResponseData = new FileResponseData(ResponseCode.FILE_SUCCESS_UPLOAD, key, "文件上传成功",
                    true, 1, multipartFile.getOriginalFilename(), ext, DateUtil.getCurrentDateFormat(), multipartFile.getSize());
            FileInfo fileInfo = new FileInfo(key, multipartFile.getOriginalFilename(), ext, multipartFile.getSize(), DateUtil.getCurrentDate());
            fileInfo.setBlockPath(uploadInfo.getFileId());
            fileInfo.setMd5FileStr(md5);

            return fileResponseData;
        }
        LOGGER.warn("本次上传文件失败");
        return new FileResponseData(ResponseCode.FILE_UPLOAD_FAILED, "上传文件失败", false, 0);
    }

}
