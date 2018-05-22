package com.codingapi.fileserver.db.mysql.config;

import com.codingapi.fileserver.db.mysql.enums.DataSourceEnums;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author modificial
 * @date 2018/4/19 0019
 * @company codingApi
 * @description 路由
 */
public class MyAbstractRoutingDataSource extends AbstractRoutingDataSource {

    private final int dataSourceNumber;
    private AtomicInteger count = new AtomicInteger(0);

    public MyAbstractRoutingDataSource(int dataSourceNumber) {
        this.dataSourceNumber = dataSourceNumber;
    }

    @Override
    protected Object determineCurrentLookupKey() {
        String typeKey = DataSourceContextHolder.getJdbcType();
        if (typeKey.equals(DataSourceEnums.write.getType())) {
            return DataSourceEnums.write.getType();
        }
        // 读 简单负载均衡  
        int number = count.getAndAdd(1);
        int lookupKey = number % dataSourceNumber;
        return new Integer(lookupKey);
    }

}
