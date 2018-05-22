package com.codingapi.fileserver.ato.vo;

import com.codingapi.fileserver.exception.ResponseCode;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author modificial
 * @date 2018/4/4
 * @company codingApi
 * @description 上传文件后的数据返回对象，便于前台获取数据.
 */
@Api(value = "文件描述对象")
public class FileResponseData {
    @ApiModelProperty(value = "返回状态编码")
    @JsonInclude(Include.NON_NULL)
    private ResponseCode code;
    @JsonInclude(Include.NON_NULL)
    @ApiModelProperty(value = "文件返回钥匙")
    private String key;
    @ApiModelProperty(value = "返回信息")
    @JsonInclude(Include.NON_NULL)
    private String message;
    @ApiModelProperty("成功标识")
    private boolean success = true;
    @ApiModelProperty("是否上传完毕")
    @JsonInclude(Include.NON_NULL)
    private int isUploadAll;

    @ApiModelProperty(value = "文件名字")
    @JsonInclude(Include.NON_NULL)
    private String fileName;

    @ApiModelProperty(value = "文件类型")
    @JsonInclude(Include.NON_NULL)
    private String fileType;
    @ApiModelProperty("返回文件创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private String createDate;
    @ApiModelProperty(value = "文件大小")
    @JsonInclude(Include.NON_NULL)
    private long fileSize;

    public FileResponseData() {

    }

    public FileResponseData(ResponseCode code, String key, String message, boolean success, int isUploadAll, String fileName, String fileType, String createDate, long fileSize) {
        this.code = code;
        this.key = key;
        this.message = message;
        this.success = success;
        this.isUploadAll = isUploadAll;
        this.fileName = fileName;
        this.fileType = fileType;
        this.createDate = createDate;
        this.fileSize = fileSize;
    }

    public FileResponseData(ResponseCode code, String message, boolean success, int isUploadAll) {
        this.code = code;
        this.message = message;
        this.success = success;
        this.isUploadAll = isUploadAll;
    }

    public ResponseCode getCode() {
        return code;
    }

    public void setCode(ResponseCode code) {
        this.code = code;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getIsUploadAll() {
        return isUploadAll;
    }

    public void setIsUploadAll(int isUploadAll) {
        this.isUploadAll = isUploadAll;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    /**
     * 添加属性
     *
     * @param message
     * @param responseCode
     * @param success
     * @param fileResponseData
     * @return
     */
    public static FileResponseData createFileResponseData(String message, ResponseCode responseCode, boolean success,
                                                          FileResponseData fileResponseData) {
        fileResponseData.setMessage(message);
        fileResponseData.setCode(responseCode);
        fileResponseData.setSuccess(success);
        return fileResponseData;
    }

    @Override
    public String toString() {
        return "FileResponseData{" +
                "code=" + code +
                ", key='" + key + '\'' +
                ", message='" + message + '\'' +
                ", success=" + success +
                ", isUploadAll=" + isUploadAll +
                ", fileName='" + fileName + '\'' +
                ", fileType='" + fileType + '\'' +
                ", createDate='" + createDate + '\'' +
                ", fileSize=" + fileSize +
                '}';
    }
}
