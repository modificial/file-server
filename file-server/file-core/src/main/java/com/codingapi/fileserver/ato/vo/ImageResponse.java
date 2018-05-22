package com.codingapi.fileserver.ato.vo;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author modificial
 * @date 2018/4/24 0024
 * @company codingApi
 * @description 图片上传返回信息
 */
public class ImageResponse {
    /**
     * 文件访问秘钥
     */
    private String key;
    /**
     * 图片原始名字
     */
    @ApiModelProperty(value = "图片的名字", notes = "图片原始名字")
    private String orginName;
    /**
     * 图片类型
     */
    @ApiModelProperty(value = "图片类型", notes = "图片格式")
    private String fileType;
    /**
     * 图片大小
     */
    @ApiModelProperty(value = "图片大小", notes = "图片的大小")
    private long fileSize;
    /**
     * 是否有效
     */
    @ApiModelProperty(value = "是否有效", notes = "md5是否相同")
    private boolean valid;

    public ImageResponse() {

    }

    public ImageResponse(String orginName, String fileType, long fileSize, String key) {
        this.orginName = orginName;
        this.fileType = fileType;
        this.fileSize = fileSize;
        this.key = key;
    }

    public ImageResponse(String orginName, String fileType, long fileSize, boolean valid) {
        this.orginName = orginName;
        this.fileType = fileType;
        this.fileSize = fileSize;
        this.valid = valid;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getOrginName() {
        return orginName;
    }

    public void setOrginName(String orginName) {
        this.orginName = orginName;
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

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    @Override
    public String toString() {
        return "ImageResponse{" +
                "key='" + key + '\'' +
                ", orginName='" + orginName + '\'' +
                ", fileType='" + fileType + '\'' +
                ", fileSize=" + fileSize +
                '}';
    }

}
