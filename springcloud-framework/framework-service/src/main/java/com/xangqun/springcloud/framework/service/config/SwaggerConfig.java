/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.xangqun.springcloud.framework.service.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static com.google.common.base.Predicates.or;
import static springfox.documentation.builders.PathSelectors.regex;

/**
 * @author laixiangqun
 * @since 2018-8-1
 */
@Configuration
@EnableSwagger2
@ConditionalOnProperty(name = "swagger.enable", havingValue = "true")
public class SwaggerConfig {

    @Bean
    public Docket createRestApi(@Value("${api.swagger.title}") String title,
                                @Value("${api.swagger.description}") String description,
                                @Value("${api.swagger.version}") String version,
                                @Value("${api.swagger.basePackage}") String basePackage) {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo(title, description, version)).select()
                .apis(RequestHandlerSelectors.basePackage(basePackage))
                .paths(or(regex("/*/.*"))).build();
    }

    private ApiInfo apiInfo(String title, String description, String version) {
        ApiInfo apiInfo = new ApiInfoBuilder().title(title).description(description)
                .version(version).build();

        return apiInfo;
    }
}
