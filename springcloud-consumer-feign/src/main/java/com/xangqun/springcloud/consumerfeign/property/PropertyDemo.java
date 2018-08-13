/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.xangqun.springcloud.consumerfeign.property;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Properties;

/**
 * @author laixiangqun
 * @since 2018-8-13
 */
@Component
public class PropertyDemo {
    private static final String SPRING_BOOT_HELLO = "spring-boot.hello";
    private static final String APPLICATION_YML = "application.yaml";
    private static final String SPRING_BOOT_PREFIX = "spring-boot";
    private static final String UNDEFINED = "undefined";
    private static final String APPLICATION_PROPERTIES = "application.properties";

    @Value("${spring-boot.hello:}")
    private String str;

    @Resource
    private Environment environment;

    @Resource
    private Attribute attribute;

    /**
     * 1. 通过注入普通字符串
     */
    public void getAttrByValue() {
        System.out.println("2-1. 通过value获取值: " + str);
    }

    /**
     * 2. 通过注入Environment获取值
     */
    public void getAttrByEnvironment() {
        String property = environment.getProperty(SPRING_BOOT_HELLO);
        System.out.println("2-1. 通过注入Environment获取值: " + property);
    }
    /**
     * 3. 通过@ConfigurationProperties注入对象属性获取
     */
    public void getAttrByConfigurationPropertiesAnnotation() {
        System.out.println("3. 通过@ConfigurationProperties注入对象属性获取: " + attribute);
    }

    /**
     * 4. 通过PropertiesLoaderUtils获取(注意，此工具类仅可处理.properties或.xml配置文件)
     */
    public void getAttrByPropertiesLoaderUtils() {
        try {
            ClassPathResource resource = new ClassPathResource(APPLICATION_PROPERTIES);
            Properties properties = PropertiesLoaderUtils.loadProperties(resource);
            String property = properties.getProperty(SPRING_BOOT_HELLO, UNDEFINED);
            System.out.println("4. 通过PropertiesLoaderUtils获取: " + property);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Data
    @Component
    @PropertySource("classpath:" + APPLICATION_YML)
    @ConfigurationProperties(prefix = SPRING_BOOT_PREFIX)
    class Attribute {
        private String hello;
        private String world;
    }

}
