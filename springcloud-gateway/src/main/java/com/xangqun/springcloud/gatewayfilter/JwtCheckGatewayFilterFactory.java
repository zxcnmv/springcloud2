/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.xangqun.springcloud.gatewayfilter;

import com.xangqun.springcloud.util.JWTUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * @author laixiangqun
 * @since 2018-8-3
 */
@Component
public class JwtCheckGatewayFilterFactory extends AbstractGatewayFilterFactory<RateCheckGatewayFilterFactory.Config> {

    @Override
    public GatewayFilter apply(RateCheckGatewayFilterFactory.Config config) {
        return (exchange, chain) -> {
            String token = exchange.getRequest().getHeaders().getFirst("Authorization");
            String openId =  exchange.getRequest().getQueryParams().getFirst("openId");
            //check token
            if (StringUtils.isNotBlank(token)) {
                String tokenOpenID = JWTUtils.checkToken(token).getUid();
                if(StringUtils.isNotBlank(tokenOpenID)) {
                    if(openId != null) {
                        if(openId.equals(tokenOpenID)) {
                            return chain.filter(exchange);
                        }
                    } else {
                        return chain.filter(exchange);
                    }
                }

            }

            //不合法
            ServerHttpResponse response = exchange.getResponse();
            //设置headers
            HttpHeaders httpHeaders = response.getHeaders();
            httpHeaders.add("Content-Type", "application/json; charset=UTF-8");
            httpHeaders.add("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0");
            //设置body
            JsonPackage jsonPackage = new JsonPackage();
            jsonPackage.setStatus(110);
            jsonPackage.setMessage("未登录或登录超时");
            DataBuffer bodyDataBuffer = response.bufferFactory().wrap(jsonPackage.toJSONString().getBytes());

            return response.writeWith(Mono.just(bodyDataBuffer));
        };
    }
}