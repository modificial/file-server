package com.codingapi.fileserver.db.mysql.config;

import org.apache.log4j.Logger;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;


/**
 * @author modificial
 * @date 2018/4/19 0019
 * @company codingApi
 * @description aop协调主从分离
 */
@Aspect
@Component
public class DataSourceAop {
    private static Logger log = Logger.getLogger(DataSourceAop.class);

    @Before("execution(* com.codingapi.fileserver.db.mysql.dao..*.get*(..))")
    public void setReadDataSourceType() {
        DataSourceContextHolder.read();
        log.info("dataSource切换到：Read");
    }

    @Before("execution(* com.codingapi.fileserver.db.mysql.dao..*.insert*(..)) || " +
            "execution(* com.codingapi.fileserver.db.mysql.dao..*.delete*(..)) || " +
            "execution(* com.codingapi.fileserver.db.mysql.dao..*.update*(..))")
    public void setWriteDataSourceType() {
        DataSourceContextHolder.write();
        log.info("dataSource切换到：write");
    }
}
