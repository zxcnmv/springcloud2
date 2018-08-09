package com.xangqun.springcloud.framework.service.config;


import org.apache.catalina.filters.RemoteIpFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.filter.OrderedCharacterEncodingFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.filter.HttpPutFormContentFilter;
import org.springframework.web.filter.CorsFilter;

import java.nio.charset.Charset;

@Configuration
public class ConfigurationFilter {
	private static final Logger log = LoggerFactory.getLogger(ConfigurationFilter.class);

	@Value("${characterEncoding:UTF-8}")
	protected String characterEncoding;
	@Value("${allow.cross.domain:*}")
	private String allowDomain;

	@Bean
	public FilterRegistrationBean characterEncodingFilter() {
		log.debug("autoconfig characterEncodingFilter ...");
		FilterRegistrationBean reg = new FilterRegistrationBean();
		OrderedCharacterEncodingFilter filter = new OrderedCharacterEncodingFilter();
		filter.setEncoding(characterEncoding);
		filter.setForceResponseEncoding(true);
		filter.setForceEncoding(true);
		reg.setFilter(filter);
		reg.setOrder(-1);//设置优先级
		reg.addUrlPatterns("/*");
		return reg;
	}

	@Bean
	public RemoteIpFilter remoteIpFilter() {
		return new RemoteIpFilter();
	}

	@Bean
	public FilterRegistrationBean<CorsFilter> corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		// 设置你要允许的网站域名，如果全允许则设为 *
		config.addAllowedOrigin("*");
		// 如果要限制 HEADER 或 METHOD 请自行更改
		config.addAllowedHeader("*");
		config.addAllowedMethod("*");
		source.registerCorsConfiguration("/**", config);
		FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean(new CorsFilter(source));
		// 这个顺序很重要哦，为避免麻烦请设置在最前
		bean.setOrder(0);
		return bean;
	}

	@Bean
	public FilterRegistrationBean<HttpPutFormContentFilter> httpPutFormContentFilter() {
		FilterRegistrationBean<HttpPutFormContentFilter> registration = new FilterRegistrationBean<>();
		registration.setFilter(new HttpPutFormContentFilter());//添加过滤器
		registration.addUrlPatterns("/*");//设置过滤路径，/*所有路径
		registration.setName("HttpPutFormContentFilter");//设置优先级
		registration.setOrder(2);//设置优先级
		return registration;
	}
	@Bean
	public FilterRegistrationBean<HiddenHttpMethodFilter> hiddenHttpMethodFilter() {
		FilterRegistrationBean<HiddenHttpMethodFilter> registration = new FilterRegistrationBean<>();
		registration.setFilter(new HiddenHttpMethodFilter());//添加过滤器
		registration.addUrlPatterns("/*");//设置过滤路径，/*所有路径
		registration.setName("HiddenHttpMethodFilter");//设置优先级
		registration.setOrder(2);//设置优先级
		return registration;
	}

	@Bean
	public StringHttpMessageConverter stringHttpMessageConverter() {
		log.debug("autoconfig stringHttpMessageConverter ...");
		return new StringHttpMessageConverter(Charset.forName(characterEncoding));
	}

	@Bean
	public RequestContextListener requestContextListener() {
		log.debug("autoconfig requestContextListener ...");
		return new RequestContextListener();
	}

}
