package com.codingapi.fileserver.api.service;

import com.codingapi.fileserver.ato.ao.ChunckReq;
import com.codingapi.fileserver.ato.ao.FileInfoReq;
import com.codingapi.fileserver.ato.ao.FileUploadReq;
import com.codingapi.fileserver.ato.vo.FileCheckResult;
import com.codingapi.fileserver.ato.vo.FileResponseData;
import com.lorne.core.framework.exception.ServiceException;

/**
 * @author modificial
 * @date 2018/4/8
 * @company codingApi
 * @description 文件上传
 */
public interface ApiFileUploadService {

    /**
     * 根据文件上传
     *
     * @param chunckReq 分块信息
     * @return 文件上传后的基本信息
     * @throws ServiceException 可能抛出业务异常
     */
    FileResponseData upload(ChunckReq chunckReq) throws ServiceException;

    /**
     * 检查文件是否需要分片处理
     *
     * @param fileInfoReq 文件详细信息
     * @return
     * @throws ServiceException 可能抛出业务异常
     */
    FileCheckResult check(FileInfoReq fileInfoReq) throws ServiceException;

    /**
     * 上传文件
     *
     * @param fileUploadReq 上传文件参数
     * @return
     * @throws ServiceException 可能抛出业务异常
     */
    FileResponseData upload(FileUploadReq fileUploadReq) throws ServiceException;


}
