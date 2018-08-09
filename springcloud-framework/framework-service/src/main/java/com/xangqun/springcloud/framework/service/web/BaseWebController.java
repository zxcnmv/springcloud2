/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.xangqun.springcloud.framework.service.web;

import org.springframework.beans.propertyeditors.CustomBooleanEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import java.util.Date;

/**
 * BaseWebController
 * @author wanghongben
 * @since 2018年1月24日
 */
public class BaseWebController {

	@InitBinder
	protected void initBaseBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new DateEditor());
		binder.registerCustomEditor(Boolean.class, new CustomBooleanEditor(
				"true", "false", true));
	}
}
