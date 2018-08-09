/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.xangqun.springcloud.framework.service.dto;

import java.io.Serializable;

/**
 * ResponseDto
 * @author wanghongben
 * @since 2018年1月24日
 */
public class ResponseDto<T> implements Serializable {

  private String code;
  private T data;
  private String message;

  public String getCode() {
    return code == null ? "" : code.trim();
  }

  public void setCode(String code) {
    this.code = code;
  }

  public Object getData() {
    return data;
  }

  public void setData(T data) {
    this.data = data;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  @Override
  public String toString() {
    return "ResponseDto [code=" + code + ", data=" + data + ", message=" + message + "]";
  }

  public ResponseDto(String code, T data, String message) {
    this.code = code;
    this.data = data;
    this.message = message;
  }

  public ResponseDto() {

  }
}
