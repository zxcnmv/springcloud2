/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.vip.saturn.job.console.springboot;

import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.ErrorPageRegistrar;
import org.springframework.boot.web.server.ErrorPageRegistry;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

/**
 * @author laixiangqun
 * @since 2018-8-6
 */
@Configuration
public class ContainerConfig implements ErrorPageRegistrar {
    @Override
    public void registerErrorPages(ErrorPageRegistry registry) {
        ErrorPage[] errorPages = new ErrorPage[2];
        errorPages[0] = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/500");
        errorPages[1] = new ErrorPage(HttpStatus.NOT_FOUND, "/404");
        registry.addErrorPages(errorPages);
    }
}