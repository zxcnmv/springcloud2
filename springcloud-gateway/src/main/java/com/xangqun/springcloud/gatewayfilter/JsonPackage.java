/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.xangqun.springcloud.gatewayfilter;

import com.alibaba.fastjson.JSON;

/**
 * @author laixiangqun
 * @since 2018-8-3
 */
public class JsonPackage {
    private int status;
    private String message;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String toJSONString() {
        return JSON.toJSONString(this);
    }

    public static JsonPackage getHystrixJsonPackage(){
        JsonPackage jsonPackage=new JsonPackage();
        return jsonPackage;
    }
}
