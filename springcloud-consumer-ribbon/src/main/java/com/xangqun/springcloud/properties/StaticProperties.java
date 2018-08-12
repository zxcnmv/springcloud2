package com.xangqun.springcloud.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * https://www.jianshu.com/p/17c47a3d4c14
 */
@Component
public class StaticProperties {

    public static String CUSTOM_NAME;

    @Value("${custom.name}")
    public void setCustomName(String customName) {
        CUSTOM_NAME = customName;
    }

}
