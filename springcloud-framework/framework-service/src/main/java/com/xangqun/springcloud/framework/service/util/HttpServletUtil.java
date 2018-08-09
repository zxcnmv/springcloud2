/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.xangqun.springcloud.framework.service.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * Servlet Utility
 * 
 * @author gaoyanlong
 * @since 2018年1月22日
 */
public class HttpServletUtil {

  private HttpServletUtil() {}

  private static final Logger logger = LoggerFactory.getLogger(HttpServletUtil.class);
  private static final String FORMAT_RES = "Response: %s";
  private static final String PRINTWRITER_ERROR = "PrintWriter error!";
  
  public static void initResponse(ServletResponse resp) {

    HttpServletResponse httpResponse = (HttpServletResponse) resp;
    httpResponse.setCharacterEncoding("UTF-8");
    httpResponse.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
    httpResponse.setHeader("Access-Control-Allow-Origin", "*");
    httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
    httpResponse.setHeader("Access-Control-Allow-Methods", "*");
    httpResponse.setHeader("Access-Control-Allow-Headers", "Content-Type,Authorization");
    httpResponse.setHeader("Access-Control-Expose-Headers", "*");
  }
  
  /**
   * @param req
   * @throws IOException
   */
  public static String readContentBody(HttpServletRequest req) throws IOException {
    BufferedReader br = req.getReader();
    String str;
    StringBuffer wholeStr = new StringBuffer();
    while ((str = br.readLine()) != null) {
      wholeStr.append(str);
    }
    br.close();
    return wholeStr.toString();
  }

}
