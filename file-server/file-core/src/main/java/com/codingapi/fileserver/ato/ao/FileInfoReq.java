package com.codingapi.fileserver.ato.ao;

import com.codingapi.fileserver.db.mysql.entity.FileInfo;
import io.swagger.annotations.ApiParam;

/**
 * @author modificial
 * @date 2018/4/19 0019
 * @company codingApi
 * @description 文件校验参数
 */
public class FileInfoReq {
    /**
     * 文件Md5加密串
     */
    @ApiParam(value = "文件md5加密字符串")
    private String md5FileStr;
    /**
     * 文件大小
     */
    @ApiParam(value = "文件大小")
    private long fileSize;
    /**
     * 文件类型
     */
    @ApiParam(value = "文件类型")
    private String fileType;
    /**
     * 文件名字
     */
    @ApiParam(value = "文件名字")
    private String fileName;

    /**
     * 访问令牌
     */
    @ApiParam(value = "访问令牌")
    private String token;

    public String getMd5FileStr() {
        return md5FileStr;
    }

    public void setMd5FileStr(String md5FileStr) {
        this.md5FileStr = md5FileStr;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public static FileInfo toBeans(FileInfoReq fileInfoReq) {
        FileInfo fileInfo = new FileInfo();
        if (fileInfoReq.getFileSize() > 0) {
            fileInfo.setFileSize(fileInfoReq.getFileSize());
        }
        fileInfo.setFileType(fileInfoReq.getFileType());
        fileInfo.setMd5FileStr(fileInfoReq.getMd5FileStr());
        fileInfo.setOrginFileName(fileInfoReq.getFileName());
        return fileInfo;
    }

    @Override
    public String toString() {
        return "FileInfoReq{" +
                "md5FileStr='" + md5FileStr + '\'' +
                ", fileSize=" + fileSize +
                ", fileType='" + fileType + '\'' +
                ", fileName='" + fileName + '\'' +
                '}';
    }
}
