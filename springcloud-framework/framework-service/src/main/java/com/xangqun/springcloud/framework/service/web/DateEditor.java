/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.xangqun.springcloud.framework.service.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.beans.PropertyEditorSupport;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * DateEditor扩展
 * @author wanghongben
 * @since 2018年1月24日
 */
public class DateEditor extends PropertyEditorSupport {
	
	private static final Logger logger = LoggerFactory.getLogger(DateEditor.class);
	
	@Override
	public void setAsText(String text) {
		Date date = null;
		if (StringUtils.isEmpty(text)) {
			setValue(null);
			return;
		}
		try {
			if (text.length() > 13) {
				date = getTimeformat().parse(text);
			} else {
				date = getDateFormat().parse(text);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		setValue(date);
	}

	@Override
	public String getAsText() {
		Date value = (Date) getValue();
		return (value != null ? getTimeformat().format(value) : "");
	}

	/**
	 * Format 日期时间
	 * @return SimpleDateFormat
	 */
	private static SimpleDateFormat getDateFormat() {
		return new SimpleDateFormat("yyyy-MM-dd");
	}
	/**
   * Format 时间
   * @return SimpleDateFormat
   */
	private static SimpleDateFormat getTimeformat() {
		return new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
	}
}
