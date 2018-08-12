package com.xangqun.springcloud.redispubsub;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

/**
 * https://www.jianshu.com/p/660141a93619
 */
@Component
public class RedisService {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private CountDownLatch latch;

    /**
     * 向通道发送消息的方法
     * @param channel
     * @param message
     */
    public void sendChannelMess(String channel, String message) {
        try {
            stringRedisTemplate.convertAndSend(channel, message);
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

