/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.xangqun.springcloud.component.rabbitmq;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

/**
 * @author laixiangqun
 * @since 2018-8-1
 */
public class RabbitmqConfig {

    /**
     * 实例化rabbitTemplate
     */
    @Bean(name = "rabbitTemplate")
    @ConditionalOnMissingBean(RabbitTemplate.class)
    public RabbitTemplate rabbitTemplate(CachingConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
         //mq发送应答
//        template.setMandatory(true);
//        template.setMessageConverter(new Jackson2JsonMessageConverter());
        template.setMessageConverter(new Gson2JsonMessageConverter());
        //发布确认
        template.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            //消息发送到queue时就执行
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                if (!ack){
//                   throw new RuntimeException("send error " + cause);
                }
            }
        });
        return template;
    }
}
