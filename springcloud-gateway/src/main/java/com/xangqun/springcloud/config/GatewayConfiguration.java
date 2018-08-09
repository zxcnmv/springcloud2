/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.xangqun.springcloud.config;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

/**
 * @author laixiangqun
 * @since 2018-8-3
 */
@Configuration
public class GatewayConfiguration {

    @Bean(name="apiKeyResolver")
    public KeyResolver apiKeyResolver() {
        //根据api接口来限流
        return exchange -> {
            return Mono.just(exchange.getRequest().getPath().value());
        };
    }

}