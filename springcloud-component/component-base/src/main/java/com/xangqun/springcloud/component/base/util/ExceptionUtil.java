/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.xangqun.springcloud.component.base.util;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Exception Utility
 * 
 * @author douguoqiang
 * @since 2018年1月15日
 */
public class ExceptionUtil {

	private ExceptionUtil() {
	}

	public static String getErrorInfoFromException(Exception e) {
		try {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			return "\r\n" + sw.toString() + "\r\n";
		} catch (Exception e2) {
			return "bad getErrorInfoFromException";
		}
	}
}
