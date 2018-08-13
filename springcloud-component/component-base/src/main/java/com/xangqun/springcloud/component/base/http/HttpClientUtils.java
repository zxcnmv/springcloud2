/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.xangqun.springcloud.component.base.http;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * HttpClient Utility
 * 
 * @author douguoqiang
 * @since 2018年1月15日
 */
public class HttpClientUtils {
  private static final CloseableHttpClient httpClient;
  public static final String CHARSET = "UTF-8";
  private static final Logger logger = LoggerFactory.getLogger(HttpClientUtils.class);

  static {
    RequestConfig config =
        RequestConfig.custom().setConnectTimeout(60000).setSocketTimeout(15000).build();
    httpClient = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
  }

  private HttpClientUtils() {

  }

  /**
   * HTTP Get 获取内容
   * 
   * @param url 请求的url地址 ?之前的地址
   * @param params 请求的参数
   * @param charset 编码格式
   * @return 页面内容
   */
  public static String doGet(String url, Map<String, String> params, String charset,
      String authorization) {
    if (StringUtils.isEmpty(url)) {
      return null;
    }
    try {
      if (params != null && !params.isEmpty()) {
        List<NameValuePair> pairs = new ArrayList<NameValuePair>(params.size());
        for (Map.Entry<String, String> entry : params.entrySet()) {
          String value = entry.getValue();
          if (value != null) {
            pairs.add(new BasicNameValuePair(entry.getKey(), value));
          }
        }
        url += "?" + EntityUtils.toString(new UrlEncodedFormEntity(pairs, charset));
      }
      HttpGet httpGet = new HttpGet(url);
      httpGet.addHeader("Authorization", "Bearer " + authorization);

      CloseableHttpResponse response = httpClient.execute(httpGet);
      int statusCode = response.getStatusLine().getStatusCode();
      if (statusCode != 200) {
        httpGet.abort();
        throwErrorCode(statusCode);
      }
      return getResResult(response);
    } catch (Exception e) {
      logger.error(e.getMessage());
    }
    return null;
  }

  /**
   * HTTP Post 获取内容
   * 
   * @param url 请求的url地址 ?之前的地址
   * @param params 请求的参数
   * @param charset 编码格式
   * @return 页面内容
   */
  public static String doPost(String url, Map<String, String> params, String charset) {

    logger.debug(charset);
    if (StringUtils.isEmpty(url)) {
      return null;
    }
    try {
      List<NameValuePair> pairs = null;
      if (params != null && !params.isEmpty()) {
        pairs = new ArrayList<NameValuePair>(params.size());
        for (Map.Entry<String, String> entry : params.entrySet()) {
          String value = entry.getValue();
          if (value != null) {
            pairs.add(new BasicNameValuePair(entry.getKey(), value));
          }
        }
      }
      HttpPost httpPost = new HttpPost(url);
      if (pairs != null && !pairs.isEmpty()) {
        httpPost.setEntity(new UrlEncodedFormEntity(pairs, CHARSET));

      }
      httpPost.addHeader("Authorization", "Basic Y2xpZW50YXBwOjEyMzQ1Ng==");
      CloseableHttpResponse response = httpClient.execute(httpPost);
      int statusCode = response.getStatusLine().getStatusCode();
      if (statusCode != 200) {
        httpPost.abort();
        throwErrorCode(statusCode);
      }
      return getResResult(response);
    } catch (Exception e) {
      logger.error(e.getMessage());
    }
    return null;
  }

  /**
   * getResResult
   * @param response
   * @return String
   */
  private static String getResResult(CloseableHttpResponse response) {
    HttpEntity entity = response.getEntity();
    String result = null;
    try {
      if (entity != null) {
        result = EntityUtils.toString(entity, "utf-8");
      }
      EntityUtils.consume(entity);
      response.close();
    } catch (Exception e) {
      logger.error(e.getMessage());
    }
    return result;
  }
  
  /**
   * throwErrorCode
   * @param statusCode void
   */
  private static void throwErrorCode(int statusCode){
    throw new RuntimeException( "HttpClient,error status code :" + statusCode);
  }
}
