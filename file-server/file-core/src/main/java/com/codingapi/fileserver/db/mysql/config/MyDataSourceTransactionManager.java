package com.codingapi.fileserver.db.mysql.config;

import org.apache.log4j.Logger;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;
import javax.sql.DataSource;


/**
 * @author modificial
 * @date 2018/4/19 0019
 * @company codingApi
 * @description 自定义事务
 */
@Configuration
@EnableTransactionManagement
public class MyDataSourceTransactionManager extends DataSourceTransactionManagerAutoConfiguration {

    private static Logger log = Logger.getLogger(DataSourceTransactionManager.class);
    /**
     * 自定义事务
     * MyBatis自动参与到spring事务管理中，无需额外配置，只要org.mybatis.spring.SqlSessionFactoryBean引用的数据源与DataSourceTransactionManager引用的数据源一致即可，否则事务管理会不起作用。
     *
     * @return
     */
    @Resource(name = "writeDataSource")
    private DataSource dataSource;

    @Bean(name = "transactionManager")
    public DataSourceTransactionManager transactionManagers() {
        log.info("-------------------- transactionManager init ---------------------");
        return new DataSourceTransactionManager(dataSource);
    }

}
