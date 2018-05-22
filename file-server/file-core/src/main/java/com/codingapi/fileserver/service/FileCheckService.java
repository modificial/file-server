package com.codingapi.fileserver.service;

import com.codingapi.fileserver.ato.ao.FileInfoReq;
import com.codingapi.fileserver.ato.vo.FileCheckResult;
import com.lorne.core.framework.exception.ServiceException;

import java.util.List;

/**
 * @author modificial
 * @date 2018/4/19 0019
 * @company codingApi
 * @description
 */
public interface FileCheckService {
    /**
     * 检验文件是否需要分片
     *
     * @param fileInfoReq 必要参数
     * @return 检验结果
     */
    FileCheckResult check(FileInfoReq fileInfoReq);

    /**
     * 文件上传完毕验证和原始签名是否一致
     *
     * @param tempLists 临时文件保存路径列表
     * @param md5       原始文件md5值
     * @param fileType  文件类型
     * @return
     * @throws ServiceException 可能抛出业务异常
     */
    boolean checkSign(List<String> tempLists, String md5, String fileType) throws ServiceException;
}
