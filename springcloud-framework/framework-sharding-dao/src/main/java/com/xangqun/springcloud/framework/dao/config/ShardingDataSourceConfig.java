/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.xangqun.springcloud.framework.dao.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.google.common.collect.Maps;
import io.shardingjdbc.core.api.MasterSlaveDataSourceFactory;
import io.shardingjdbc.core.api.ShardingDataSourceFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;

/**
 * @author laixiangqun
 * @since 2018-8-8
 */
@Slf4j
@Configuration
@EnableConfigurationProperties({ShardingMasterSlaveConfig.class, DruidProperties.class,ShardingConfig.class})
public class ShardingDataSourceConfig {

    @Autowired(required = false)
    private ShardingMasterSlaveConfig shardingMasterSlaveConfig;

    @Autowired(required = false)
    private ShardingConfig shardingConfig;

    @Autowired(required = false)
    private DruidProperties druidProperties;

    /**
     * 读写分离
     * @return
     * @throws SQLException
     */
    @Bean("dataSource")
    @ConditionalOnProperty({"sharding.jdbc.data-sources.ds_master.url", "sharding.jdbc.master-slave-rule.master-data-source-name"})
    @ConditionalOnExpression("${sharding.jdbc.shardingRule.default-database-strategy.inline.sharding-column} == null")
    public DataSource masterSlaveDataSource() throws SQLException {
        shardingMasterSlaveConfig.getDataSources().forEach((k, v) -> configDataSource(v));
        Map<String, DataSource> dataSourceMap = Maps.newHashMap();
        dataSourceMap.putAll(shardingMasterSlaveConfig.getDataSources());
        DataSource dataSource = MasterSlaveDataSourceFactory.createDataSource(dataSourceMap, shardingMasterSlaveConfig.getMasterSlaveRule(), Maps.newHashMap());
        log.info("masterSlaveDataSource config complete");
        return dataSource;
    }

    /**
     * 分库分表（包含读写分离支持）
     * @return
     * @throws SQLException
     */
    @Bean("dataSource")
    @ConditionalOnProperty({"sharding.jdbc.shardingRule.default-database-strategy.inline.sharding-column"})
    public DataSource shardingConfigDataSource() throws SQLException {
        shardingConfig.getDataSources().forEach((k, v) -> configDataSource(v));
        Map<String, DataSource> dataSourceMap = Maps.newHashMap();
        dataSourceMap.putAll(shardingConfig.getDataSources());
        DataSource dataSource = ShardingDataSourceFactory.createDataSource(dataSourceMap, shardingConfig.getShardingRule().getShardingRuleConfiguration(), Maps.newHashMap(),new Properties());
        log.info("shardingConfigDataSource config complete");
        return dataSource;
    }

    private void configDataSource(DruidDataSource druidDataSource) {
        druidDataSource.setMaxActive(druidProperties.maxActive);
        druidDataSource.setInitialSize(druidProperties.initialSize);
        druidDataSource.setMaxWait(druidProperties.maxWait);
        druidDataSource.setMinIdle(druidProperties.minIdle);
        druidDataSource.setTimeBetweenEvictionRunsMillis(druidProperties.timeBetweenEvictionRunsMillis);
        druidDataSource.setMinEvictableIdleTimeMillis(druidProperties.minEvictableIdleTimeMillis);
        druidDataSource.setValidationQuery("SELECT 1");
        druidDataSource.setTestWhileIdle(druidProperties.testWhileIdle);
        druidDataSource.setTestOnBorrow(druidProperties.testOnBorrow);
        druidDataSource.setTestOnReturn(druidProperties.testOnReturn);
        druidDataSource.setPoolPreparedStatements(druidProperties.poolPreparedStatements);
        druidDataSource.setMaxOpenPreparedStatements(druidProperties.maxOpenPreparedStatements);
        druidDataSource.setUseGlobalDataSourceStat(druidProperties.useGlobalDataSourceStat);
        try {
            druidDataSource.setFilters(druidProperties.filters);
        } catch (SQLException e) {
            log.error("druid configuration initialization filter", e);
        }
    }
}
