/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.xangqun.springcloud.gatewayfilter;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author laixiangqun
 * @since 2018-8-3
 */
@RestController
public class HystrixCommandController {
    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    @RequestMapping("/hystrixTimeout")
    public JsonPackage hystrixTimeout() {
        log.error("i5xforyou-service-gateway触发了断路由");
        return JsonPackage.getHystrixJsonPackage();
    }

    @HystrixCommand(commandKey="authHystrixCommand")
    public JsonPackage authHystrixCommand() {
        return JsonPackage.getHystrixJsonPackage();
    }

}
