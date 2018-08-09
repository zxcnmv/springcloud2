/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.xangqun.springcloud.framework.service.util;

import org.springframework.util.StreamUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author gaoyanlong
 * @since 2018年2月25日
 */
public class RequestWrapper extends HttpServletRequestWrapper {

  private final byte[] body;

  public RequestWrapper(HttpServletRequest request) throws IOException {
    super(request);
    body = StreamUtils.copyToByteArray(request.getInputStream());
  }

  @Override
  public BufferedReader getReader() throws IOException {
    return new BufferedReader(new InputStreamReader(getInputStream()));
  }

  @Override
  public ServletInputStream getInputStream() throws IOException {
    final ByteArrayInputStream bais = new ByteArrayInputStream(body);
    return new ServletInputStream() {
      @Override
      public int read() throws IOException {
        return bais.read();
      }

      @Override
      public boolean isFinished() {
        return false;
      }

      @Override
      public boolean isReady() {
        return false;
      }

      @Override
      public void setReadListener(ReadListener listener) {
        // Do nothing
      }
    };
  }
}
