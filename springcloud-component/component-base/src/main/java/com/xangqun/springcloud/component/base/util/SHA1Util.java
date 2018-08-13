package com.xangqun.springcloud.component.base.util;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import lombok.extern.slf4j.Slf4j;

/**
 * hash算法 SHA1
 * @author liangxianyong
 * @date 2018年5月21日
 * @time 上午10:27:14
 */
@Slf4j
public class SHA1Util {

	private static final String HMAC_SHA1_ALGORITHM = "HmacSHA1"; 
	
	public static String signature(String orignal,String signatureKey){
        log.debug("orignal:{}",orignal);
		// sign:
        Mac hmacSha1 = null;
		try {  
            //根据给定的字节数组构造一个密钥,第二参数指定一个密钥算法的名称    
            SecretKeySpec signinKey = new SecretKeySpec(signatureKey.getBytes(), HMAC_SHA1_ALGORITHM);  
            //生成一个指定 Mac 算法 的 Mac 对象    
            hmacSha1 = Mac.getInstance(HMAC_SHA1_ALGORITHM);  
            //用给定密钥初始化 Mac 对象    
            hmacSha1.init(signinKey);  
  
		} catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("No such algorithm: " + e.getMessage());
        } catch (InvalidKeyException e) {
            throw new RuntimeException("Invalid key: " + e.getMessage());
        }  
		byte[] data = orignal.getBytes(StandardCharsets.UTF_8);
		byte[] hash = hmacSha1.doFinal(data);
		byte[] hashWithData = new byte[hash.length+data.length];  
        System.arraycopy(hash, 0, hashWithData, 0, hash.length);  
        System.arraycopy(data, 0, hashWithData, hash.length, data.length);  
		
		return Base64.getEncoder().encodeToString(hashWithData);
	}
	
	
}
