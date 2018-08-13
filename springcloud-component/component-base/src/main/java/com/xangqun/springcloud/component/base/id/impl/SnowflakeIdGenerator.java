package com.xangqun.springcloud.component.base.id.impl;


import com.xangqun.springcloud.component.base.id.IdGenerator;
import com.xangqun.springcloud.component.base.util.NetworkUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;


/**
 * IdGenerator的Snowflake实现
 */
@Configuration
@Component
@Slf4j
public class SnowflakeIdGenerator implements IdGenerator {

    /**
     * 默认工作节点ID
     */
    private static final int DEFAULT_WORKER_ID = 1;

    /**
     * 工作节点ID
     */
    @Value("${ftcsp.id.workerId:-1}")
    private int workerId = DEFAULT_WORKER_ID;

    /**
     * 根据配置设置数据中心ID，默认为1
     */
    @Value("${ftcsp.id.dataCenterId:1}")
    private int dataCenterId;

    /**
     * Snowflake算法ID生成器
     */
    private SnowflakeIdWorker snowflakeIdWorker;

    @PostConstruct
    public void initWorkerId() {
        if (this.workerId == -1) {
            this.workerId = getWorkerId();
        }
        log.info("workerId:{}", workerId);
        snowflakeIdWorker = new SnowflakeIdWorker(this.workerId, dataCenterId);
    }

    private int getWorkerId() {
        String localIp = NetworkUtil.findPrivateHosts(false).iterator().next();
        String[] ips = localIp.split("\\.");
        String lastThreeNum = ips[ips.length - 1];
        int workerId = Integer.parseInt(lastThreeNum);

        // 128=2^7, 因为workerId占用7bit,所以ip后三位要截取
        if (workerId >= 128) {
            workerId = workerId % 100;
        }
        return workerId;
    }

    @Override
    public long getLong() {
        return snowflakeIdWorker.nextId();
    }

}
