package com.codingapi.fileserver.exception;

/**
 * @author lorne
 * @date 2018/4/9
 * @description
 */
public class LevelDbErrorException extends Exception {

    public LevelDbErrorException() {
    }

    public LevelDbErrorException(String message) {
        super(message);
    }

    public LevelDbErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public LevelDbErrorException(Throwable cause) {
        super(cause);
    }

    public LevelDbErrorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
