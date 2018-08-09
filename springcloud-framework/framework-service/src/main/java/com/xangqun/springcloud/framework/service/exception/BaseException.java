/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.xangqun.springcloud.framework.service.exception;

/**
 * 异常基类，各个模块的运行期异常均继承与该类
 * 
 * @author douguoqiang
 * @version 2016-05-24
 */
public class BaseException extends RuntimeException {

  /**
   * the serialVersionUID
   */
  private static final long serialVersionUID = 1381325479896057076L;

  /**
   * message key
   */
  private String code;

  /**
   * exception message
   */
  private String message;

  /**
   * exception detail infomation
   */
  private Throwable throwable;

  /**
   * message params
   */
  private transient Object[] values;

  /**
   * @return the code
   */
  public String getCode() {
    return code;
  }

  /**
   * @param code the code to set
   */
  public void setCode(String code) {
    this.code = code;
  }

  /**
   * @return the message
   */
  @Override
  public String getMessage() {
    return message;
  }

  /**
   * @param message the message to set
   */
  public void setMessage(String message) {
    this.message = message;
  }

  /**
   * @return the throwable
   */
  public Throwable getThrowable() {
    return throwable;
  }

  /**
   * @param throwable the throwable to set
   */
  public void setThrowable(Throwable throwable) {
    this.throwable = throwable;
  }

  /**
   * @return the values
   */
  public Object[] getValues() {
    return values;
  }

  /**
   * @param values the values to set
   */
  public void setValues(Object[] values) {
    this.values = values;
  }

  public BaseException(String code, String message, Object[] values, Throwable cause) {
    this(code, message, cause);
    this.values = values;
  }

  private BaseException(String code, String message, Throwable cause) {
    super(message, cause);
    this.message = message;
    this.code = code;
  }

}
