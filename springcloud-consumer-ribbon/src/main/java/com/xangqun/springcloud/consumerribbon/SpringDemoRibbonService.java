package com.xangqun.springcloud.consumerribbon;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.xangqun.springcloud.properties.StaticProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class SpringDemoRibbonService {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    LoadBalancerClient loadBalancerClient;

    @HystrixCommand(fallbackMethod = "portFallback")
    public String port() {
        log.error(StaticProperties.CUSTOM_NAME);
        this.loadBalancerClient.choose("spring-demo-service");  //随机访问策略
        return restTemplate.getForObject("http://SPRING-DEMO-SERVICE/port", String.class);
    }

    public String portFallback() {
        return "sorry ribbon, it's error!";
    }
}
