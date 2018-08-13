package com.xangqun.springcloud.component.base.id;

/**
 * ID生成器接口
 */
public interface IdGenerator {
    
    /**
     * 生成long型ID
     * @return long
     */
    long getLong();

}
