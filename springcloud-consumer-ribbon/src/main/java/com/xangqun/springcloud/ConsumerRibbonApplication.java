package com.xangqun.springcloud;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableEurekaClient
@EnableCircuitBreaker
@EnableHystrix
@MapperScan("com.xangqun.springcloud")
public class ConsumerRibbonApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(ConsumerRibbonApplication.class);
        app.addListeners(new ApplicationPidFileWriter());//把运行的pid打印到文件里
        app.run(args);
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public IRule ribbonRule() {
        //这里配置策略，和配置文件对应
        return new RandomRule();
    }
}
