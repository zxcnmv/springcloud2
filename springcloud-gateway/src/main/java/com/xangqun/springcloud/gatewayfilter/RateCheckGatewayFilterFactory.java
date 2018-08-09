/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.xangqun.springcloud.gatewayfilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author laixiangqun
 * @since 2018-8-3
 */
@Component
public class RateCheckGatewayFilterFactory extends AbstractGatewayFilterFactory<RateCheckGatewayFilterFactory.Config> implements ApplicationContextAware {
    private static Logger log = LoggerFactory.getLogger(RateCheckGatewayFilterFactory.class);
    private static ApplicationContext applicationContext;
    private RateCheckRedisRateLimiter rateLimiter;
    private KeyResolver keyResolver;

    public RateCheckGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        log.info("RateCheckGatewayFilterFactory.setApplicationContext，applicationContext=" + context);
        applicationContext = context;
    }

    @Override
    public GatewayFilter apply(Config config) {
        this.rateLimiter = applicationContext.getBean(RateCheckRedisRateLimiter.class);
        this.keyResolver = applicationContext.getBean(config.keyResolver, KeyResolver.class);

        return (exchange, chain) -> {
            Route route = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);

            return keyResolver.resolve(exchange).flatMap(key ->
                    // TODO: if key is empty?
                    rateLimiter.isAllowed(route.getId(), key).flatMap(response -> {
                        log.info("response: " + response);
                        // TODO: set some headers for rate, tokens left
                        if (response.isAllowed()) {
                            return chain.filter(exchange);
                        }
                        //超过了限流的response返回值
                        return setRateCheckResponse(exchange);
                    }));
        };
    }

    private Mono<Void> setRateCheckResponse(ServerWebExchange exchange) {
        //超过了限流
        ServerHttpResponse response = exchange.getResponse();
        //设置headers
        HttpHeaders httpHeaders = response.getHeaders();
        httpHeaders.add("Content-Type", "application/json; charset=UTF-8");
        httpHeaders.add("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0");
        //设置body
        JsonPackage jsonPackage = new JsonPackage();
        jsonPackage.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
        jsonPackage.setMessage("系统繁忙，请稍后重试");
        DataBuffer bodyDataBuffer = response.bufferFactory().wrap(jsonPackage.toJSONString().getBytes());

        return response.writeWith(Mono.just(bodyDataBuffer));
    }

    public static class Config {
        private String keyResolver;//限流id

        public String getKeyResolver() {
            return keyResolver;
        }
        public void setKeyResolver(String keyResolver) {
            this.keyResolver = keyResolver;
        }
    }
}