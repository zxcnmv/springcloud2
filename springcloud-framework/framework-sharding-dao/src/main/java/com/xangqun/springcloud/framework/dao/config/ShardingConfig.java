/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.xangqun.springcloud.framework.dao.config;

import com.alibaba.druid.pool.DruidDataSource;
import io.shardingjdbc.core.yaml.sharding.YamlShardingRuleConfiguration;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * @author laixiangqun
 * @since 2018-8-9
 */
@Data
@ConfigurationProperties(prefix = "sharding.jdbc")
public class ShardingConfig {

    private Map<String, DruidDataSource> dataSources = new HashMap<>();

    private YamlShardingRuleConfiguration shardingRule;
}
