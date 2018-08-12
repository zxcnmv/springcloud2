package com.xangqun.springcloud.redispubsub;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PublisherServiceImpl implements PublisherService {
    //通道名称
    public static final String CHANNEL = "test_channel";

    @Autowired
    private RedisService redisService;

    @Override
    public String pushMsg(String params) {
        log.info(" 又开始发布消息 .......... ");
        //直接使用convertAndSend方法即可向指定的通道发布消息
        redisService.sendChannelMess(CHANNEL,"我又开始发布消息了,你那边有没有收到呢?");
        return "success";
    }
}
