/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.xangqun.springcloud.component.lock;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.ClusterServersConfig;
import org.redisson.config.Config;
import org.redisson.config.SentinelServersConfig;
import org.redisson.config.SingleServerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;


/**
 * redis配置类
 * 
 * @author songjie
 * @since 2018年1月17日
 */
@Configuration
@ConditionalOnClass(Config.class)
@EnableConfigurationProperties(RedissonProperties.class)
public class RedissonConfig {

	@Autowired
	private RedissonProperties redissonProperties;

	/**
	 * 哨兵模式自动装配
	 * @return
	 */
	@Bean
	@ConditionalOnProperty(name="redisson.master-name")
	RedissonClient redissonSentinel() {
		Config config = new Config();
		SentinelServersConfig serverConfig = config.useSentinelServers().addSentinelAddress(redissonProperties.getSentinelAddressesNew())
				.setMasterName(redissonProperties.getMasterName())
				.setTimeout(redissonProperties.getTimeout())
				.setMasterConnectionPoolSize(redissonProperties.getMasterConnectionPoolSize())
				.setSlaveConnectionPoolSize(redissonProperties.getSlaveConnectionPoolSize());

		if(!StringUtils.isEmpty(redissonProperties.getPassword())) {
			serverConfig.setPassword(redissonProperties.getPassword());
		}
		return Redisson.create(config);
	}

	/**
	 * 单机模式自动装配
	 * @return
	 */
	@Bean
	@ConditionalOnProperty(name="redisson.address")
	RedissonClient redissonSingle() {
		Config config = new Config();
		SingleServerConfig serverConfig = config.useSingleServer()
				.setAddress(redissonProperties.getAddress())
				.setTimeout(redissonProperties.getTimeout())
				.setConnectionPoolSize(redissonProperties.getConnectionPoolSize())
				.setConnectionMinimumIdleSize(redissonProperties.getConnectionMinimumIdleSize());

		if(!StringUtils.isEmpty(redissonProperties.getPassword())) {
			serverConfig.setPassword(redissonProperties.getPassword());
		}

		return Redisson.create(config);
	}

	@Bean
	@ConditionalOnProperty(name="redisson.clusterAddresses")
	public RedissonClient redissonClient() {
		Config config = new Config();
		ClusterServersConfig serverConfig = config.useClusterServers().addNodeAddress(redissonProperties.getClusterAddressesNew())
				.setTimeout(redissonProperties.getTimeout())
				.setMasterConnectionPoolSize(redissonProperties.getMasterConnectionPoolSize())
				.setSlaveConnectionPoolSize(redissonProperties.getSlaveConnectionPoolSize());

		if(!StringUtils.isEmpty(redissonProperties.getPassword())) {
			serverConfig.setPassword(redissonProperties.getPassword());
		}
		return Redisson.create(config);
	}

	/**
	 * 装配locker类，并将实例注入到RedissLockUtil中
	 * @return
	 */
	@Bean
	DistributedLock distributedLocker(RedissonClient redissonClient) {
		RedisDistributedLock locker = new RedisDistributedLock();
		locker.setRedissonClient(redissonClient);
		RedisLockUtil.setLocker(locker);
		return locker;
	}

}
