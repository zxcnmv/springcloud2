/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.xangqun.springcloud.framework.service.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Component;

/**
 * @author songjie
 * @since 2018年2月8日
 */
@Component
public class CommonMessage {
	@Value("${egsc.config.i18n.basenames:}")
	private String basenames;
	  
	@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasenames("classpath:messages/exception-framework", "classpath:messages/exception");
		messageSource.setUseCodeAsDefaultMessage(true);
		messageSource.setDefaultEncoding("UTF-8");
		messageSource.setCacheSeconds(0);
		messageSource.addBasenames("classpath:messages/exception-framework");
	    messageSource.addBasenames("classpath:messages/exception");
	    if(StringUtils.isBlank(basenames)){
	    	return messageSource;
	    }
		String[] basenameArr = basenames.split(",");
		if(null == basenameArr || basenameArr.length == 0){
			return messageSource;
		}
		for(String basename : basenameArr){
			if(StringUtils.isNotBlank(basename)){
				messageSource.addBasenames("classpath:"+basename);
			}
		}
		return messageSource;
	}

}
