package com.codingapi.fileserver.api.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author modificial
 * @date 2018/5/15
 * @company codingApi
 * @description SLF4J包装类
 */
//todo 完善
public class LoggerWrap {
    /**
     * 打印日志
     */
    private static Logger LOGGER;
    private static final LoggerWrap LOGGER_WRAP = new LoggerWrap();

    public synchronized static LoggerWrap getInstance(Class<?> clazz) {
        LOGGER = LoggerFactory.getLogger(clazz);
        if (LOGGER_WRAP == null) {
            return new LoggerWrap();
        }
        return LOGGER_WRAP;
    }

    public void info(String info) {
        if(LOGGER.isInfoEnabled()) {
            LOGGER.info(info);
        }
    }
    public void debug(String debug) {
        if(LOGGER.isDebugEnabled()) {
            LOGGER.debug(debug);
        }
    }
    public void trace(String trace) {
        if(LOGGER.isTraceEnabled()) {
            LOGGER.trace(trace);
        }
    }
    public void warn(String warn) {
        if(LOGGER.isWarnEnabled()) {
            LOGGER.warn(warn);
        }
    }
    public void error(String error) {
        if(LOGGER.isErrorEnabled()) {
            LOGGER.info(error);
        }
    }
}
