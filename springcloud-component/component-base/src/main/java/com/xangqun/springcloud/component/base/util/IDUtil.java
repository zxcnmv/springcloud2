package com.xangqun.springcloud.component.base.util;

import java.util.UUID;

/**
 * ID主键工具类。
 * @author Administrator
 *
 */
public class IDUtil {

    /**
     * 
     * 获取32位UUID。
     * @return String
     */
    public static String getUUID() {
        String uuid = UUID.randomUUID().toString();
        uuid = uuid.replaceAll("-", "");
        return uuid;
    }
}
