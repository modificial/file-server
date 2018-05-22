package com.codingapi.fileserver.db.mysql.entity;

import com.codingapi.fileserver.ato.vo.FileResponseData;
import com.lorne.core.framework.utils.DateUtil;

import java.io.Serializable;
import java.util.Date;

/**
 * @author modificial
 * @date 2018/4/19 0019
 * @company codingApi
 * @description 文件保存下来的信息
 */
public class FileInfo implements Serializable {
    /**
     * 文件访问秘钥
     */
    private String id;
    /**
     * 文件原始名字
     */
    private String orginFileName;
    /**
     * md5加密串
     */
    private String md5FileStr;
    /**
     * 文件类型
     */
    private String fileType;
    /**
     * 文件大小
     */
    private long fileSize;
    /**
     * 分片后每片文件的路径
     */
    private String blockPath;
    /**
     * 上传时间
     */
    private Date uploadTime;
    /**
     * 临时文件路径
     */
    private String tempPath;

    public FileInfo() {

    }

    public FileInfo(String id, String orginFileName, String fileType, long fileSize, Date uploadTime) {
        this.id = id;
        this.orginFileName = orginFileName;
        this.fileType = fileType;
        this.fileSize = fileSize;
        this.uploadTime = uploadTime;
    }

    public String getOrginFileName() {
        return orginFileName;
    }

    public void setOrginFileName(String orginFileName) {
        this.orginFileName = orginFileName;
    }

    public String getMd5FileStr() {
        return md5FileStr;
    }

    public void setMd5FileStr(String md5FileStr) {
        this.md5FileStr = md5FileStr;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public String getBlockPath() {
        return blockPath;
    }

    public void setBlockPath(String blockPath) {
        this.blockPath = blockPath;
    }

    public Date getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }

    public String getTempPath() {
        return tempPath;
    }

    public void setTempPath(String tempPath) {
        this.tempPath = tempPath;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "FileInfo{" +
                "id='" + id + '\'' +
                ", orginFileName='" + orginFileName + '\'' +
                ", md5FileStr='" + md5FileStr + '\'' +
                ", fileType='" + fileType + '\'' +
                ", fileSize=" + fileSize +
                ", blockPath='" + blockPath + '\'' +
                ", uploadTime=" + uploadTime +
                ", tempPath='" + tempPath + '\'' +
                '}';
    }

    public static FileResponseData getFileResponseData(FileInfo fileInfo) {
        FileResponseData fileResponseData = new FileResponseData();
        fileResponseData.setCreateDate(DateUtil.formatDate(fileInfo.getUploadTime()));
        fileResponseData.setFileName(fileInfo.getOrginFileName());
        fileResponseData.setFileSize(fileInfo.getFileSize());
        fileResponseData.setFileType(fileInfo.getFileType());
        fileResponseData.setKey(fileInfo.getId());
        return fileResponseData;
    }
}
