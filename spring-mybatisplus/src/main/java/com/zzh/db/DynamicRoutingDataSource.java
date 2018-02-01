package com.zzh.db;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 *
 * @ClassName DynamicRoutingDataSource
 * @Description (动态切换数据源)
 * @author zzh
 * @Date 2017年5月17日 下午5:23:15
 * @version 1.0.0
 */
public class DynamicRoutingDataSource extends AbstractRoutingDataSource {
    /**
     * 取得当前使用哪个数据源
     * @return
     */
    @Override
    protected Object determineCurrentLookupKey() {
        return DbContextHolder.getDbType();
    }
}