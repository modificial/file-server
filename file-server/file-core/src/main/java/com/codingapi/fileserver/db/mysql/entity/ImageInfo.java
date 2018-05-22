package com.codingapi.fileserver.db.mysql.entity;

import java.util.Date;

/**
 * @author modificial
 * @date 2018/4/24 0024
 * @company codingApi
 * @description 图片信息
 */
public class ImageInfo {
    /**
     * 图片访问秘钥
     */
    private String id;
    /**
     * 原始图片名字
     */
    private String orginName;
    /**
     * 保存路径
     */
    private String orginPath;
    /**
     * 图片大小
     */
    private long imageSize;
    /**
     * 图片类型
     */
    private String fileType;

    /**
     * md5值是否有效
     *
     * @return
     */
    private Integer isValid;
    /**
     * 上传时间
     */
    private Date uploadTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrginName() {
        return orginName;
    }

    public void setOrginName(String orginName) {
        this.orginName = orginName;
    }

    public String getOrginPath() {
        return orginPath;
    }

    public void setOrginPath(String orginPath) {
        this.orginPath = orginPath;
    }

    public long getImageSize() {
        return imageSize;
    }

    public void setImageSize(long imageSize) {
        this.imageSize = imageSize;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public Integer isValid() {
        return isValid;
    }

    public void setValid(Integer valid) {
        isValid = valid;
    }

    public Date getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }

    @Override
    public String toString() {
        return "ImageInfo{" +
                "id='" + id + '\'' +
                ", orginName='" + orginName + '\'' +
                ", orginPath='" + orginPath + '\'' +
                ", imageSize=" + imageSize +
                ", fileType='" + fileType + '\'' +
                ", isValid=" + isValid +
                ", uploadTime=" + uploadTime +
                '}';
    }
}
