/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.xangqun.springcloud.mail;

import lombok.Data;

/**
 * @author laixiangqun
 * @since 2018-8-13
 */
@Data
public class JsonResult {
    private int code;
    private String msg;

    public JsonResult() {
    }

    public JsonResult(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
