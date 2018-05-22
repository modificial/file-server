package com.codingapi.fileserver.exception;

/**
 * @author modificial
 * @date 2018/4/4
 * @company codingApi
 * @description 上传下载时可能会出现一些错误信息，可抛出此异常
 */
public class fastDFSException extends Exception {

    /**
     * 错误码
     */
    private String code;

    /**
     * 错误消息
     */
    private String message;

    public fastDFSException() {
    }

    public fastDFSException(String code, String message) {
        this.code = code;
        this.message = message;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
