/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.xangqun.springcloud.component.base.util;

import com.alibaba.fastjson.JSON;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * <pre>
 * TODO。
 * </pre>
 *
 * @author HeShuyuan@foresee.com.cn
 * @date 2018年3月21日
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录 
 *    修改后版本:     修改人：  修改日期:     修改内容:
 *          </pre>
 */

public class HashUtil {
    
    public static String md5Hex(String data){
        return DigestUtils.md5Hex(data).toUpperCase();
    }
    
    
    public static String md5Hex(Object data){
        String json = JSON.toJSONString(data);
        return DigestUtils.md5Hex(json).toUpperCase();
    }
    
    
    public static String sha256Hex(String data){
        return DigestUtils.sha256Hex(data).toUpperCase();
    }
    
    public static String sha256Hex(Object data){
        String json = JSON.toJSONString(data);
        return DigestUtils.sha256Hex(json).toUpperCase();
    }
}
