package com.codingapi.fileserver.ato.ao;

/**
 * @author modificial
 * @date 2018/4/19 0019
 * @company codingApi
 * @description 上传返回信息
 */
public class UploadInfo {
    /**
     * 上传之后返回的文件路径
     */
    private String fileId;
    /**
     * 是否已上传完毕
     */
    private boolean isUploaded;
    /**
     * 该片是否有效
     */
    private boolean isValid;

    /**
     * 是否上传完所有片
     */
    private boolean isUploadAll;
    /**
     * 保存的临时文件目录
     */
    private String tempPath;

    public UploadInfo() {
    }

    public UploadInfo(boolean isUploaded, boolean isValid) {
        this.isUploaded = isUploaded;
        this.isValid = isValid;
    }

    public UploadInfo(String fileId, boolean isUploaded, boolean isValid) {
        this.fileId = fileId;
        this.isUploaded = isUploaded;
        this.isValid = isValid;
    }

    public UploadInfo(String fileId, boolean isUploaded, boolean isValid, boolean isUploadAll, String tempPath) {
        this.fileId = fileId;
        this.isUploaded = isUploaded;
        this.isValid = isValid;
        this.isUploadAll = isUploadAll;
        this.tempPath = tempPath;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public boolean isUploaded() {
        return isUploaded;
    }

    public void setUploaded(boolean uploaded) {
        isUploaded = uploaded;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    public boolean isUploadAll() {
        return isUploadAll;
    }

    public void setUploadAll(boolean uploadAll) {
        isUploadAll = uploadAll;
    }

    public String getTempPath() {
        return tempPath;
    }

    public void setTempPath(String tempPath) {
        this.tempPath = tempPath;
    }

    @Override
    public String toString() {
        return "UploadInfo{" +
                "fileId='" + fileId + '\'' +
                ", isUploaded=" + isUploaded +
                ", isValid=" + isValid +
                ", isUploadAll=" + isUploadAll +
                ", tempPath='" + tempPath + '\'' +
                '}';
    }
}
