/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.xangqun.springcloud.framework.service.util;

import com.xangqun.springcloud.framework.service.dto.ResponseDto;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

/**
 * @Class Name MessageUtils
 * @author gaoyanlong
 * @since 2018年1月3日
 */
public class MessageUtils {
  
  private MessageUtils() {
  }
  
  private static final Logger logger = LoggerFactory.getLogger(MessageUtils.class);

  public static ResponseDto getResponseByMessage(String message) {
    ResponseDto result = null;
    String responseCode = "";
    String responseMessage = message;

    String[] messageValues = StringUtils.split(message, ":");
    if (messageValues.length > 1) {
      responseCode = messageValues[0];
      responseMessage = messageValues[1];
    } else {
      logger.warn(String.format("message code is not set in %s", message));
    }

    result = new ResponseDto(responseCode, "", responseMessage);
    return result;
  }

  public static ResponseDto getResponseByMessageKey(String msgKey, String defaultMessage,
      HttpServletRequest request) {
    ServletContext servletContext = request.getServletContext();
    WebApplicationContext context = (WebApplicationContext) servletContext
        .getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
    String message = context.getMessage(msgKey, null, defaultMessage, request.getLocale());

    if (message != null && message.equals(msgKey)) {
      logger.warn(String.format("message key : %s is not set!", msgKey));
    }
    return getResponseByMessage(message);
  }

}
