/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.xangqun.springcloud.framework.service.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author songjie
 * @since 2018年2月8日
 */
@Component
public class I18nUtils implements ApplicationContextAware {
  private static MessageSource messageSource;

  /**
   * 
   * @param key
   * @return String
   */
  public static String getMessage(String key) {
    return getMessage(key, null);
  }

  /**
   * 
   * @param key
   * @param args
   * @return String
   */
  public static String getMessage(String key, String[] args) {
    return getMessage(key, args, null);
  }

  /**
   * 
   * @param key
   * @param args
   * @param request
   * @return String
   */
  public static String getMessage(String key, String[] args, HttpServletRequest request) {
    return getMessage(key, args, null, request);
  }

  /**
   * 
   * @param key
   * @param args
   * @param defaultMessage
   * @param request
   * @return String
   */
  public static String getMessage(String key, String[] args, String defaultMessage,
      HttpServletRequest request) {
    Locale locale = null;
    if (null == request) {
      locale = Locale.getDefault();
    } else {
      locale = request.getLocale();
    }
    return getMessageByLocale(key, args, defaultMessage, locale);
  }

  /**
   * 
   * @param key
   * @param args
   * @param locale
   * @return String
   */
  public static String getMessageByLocale(String key, String[] args, Locale locale) {
    return getMessageByLocale(key, args, null, locale);
  }

  /**
   * 
   * @param key
   * @param args
   * @param defaultMessage
   * @param locale
   * @return String
   */
  public static String getMessageByLocale(String key, String[] args, String defaultMessage,
      Locale locale) {
    if (null == locale) {
      locale = Locale.getDefault();
    }
    if (null == defaultMessage) {
      return messageSource.getMessage(key, getArgsMessage(args, locale), locale);
    } else {
      return messageSource.getMessage(key, getArgsMessage(args, locale),
          messageSource.getMessage(defaultMessage, null, locale), locale);
    }
  }

  /**
   * 
   * @param args
   * @param locale
   * @return Object[]
   */
  private static Object[] getArgsMessage(String[] args, Locale locale) {
    if (null == args || args.length == 0) {
      return args;
    }
    List<String> msgs = new ArrayList<String>();
    for (String arg : args) {
      if (null != arg) {
        msgs.add(messageSource.getMessage(arg, null, locale));
      }
    }
    return msgs.toArray();
  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
     messageSource=applicationContext.getBean(MessageSource.class);
  }

}
