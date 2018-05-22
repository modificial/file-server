package com.codingapi.fileserver.db.mysql.config;

import com.codingapi.fileserver.db.mysql.enums.DataSourceEnums;
import org.apache.log4j.Logger;

/**
 * @author modificial
 * @date 2018/4/19 0019
 * @company codingApi
 * @description 数据库连接池配置
 */
public class DataSourceContextHolder {


    private static Logger log = Logger.getLogger(DataSourceContextHolder.class);

    private static final ThreadLocal<String> local = new ThreadLocal<String>();

    public static ThreadLocal<String> getLocal() {
        return local;
    }

    /**
     * 读可能是多个库
     */
    public static void read() {

        local.set(DataSourceEnums.read.getType());
    }

    /**
     * 写只有一个库
     */
    public static void write() {
        log.debug("writewritewrite");
        local.set(DataSourceEnums.write.getType());
    }

    public static String getJdbcType() {
        return local.get();
    }

}
