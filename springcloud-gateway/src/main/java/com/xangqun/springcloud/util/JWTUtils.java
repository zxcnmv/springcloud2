/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.xangqun.springcloud.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Instant;
import java.util.Date;


/**
 * JWT Utility
 * @author douguoqiang
 * @since 2018年1月15日
 */
public class JWTUtils {
  
  private JWTUtils() {
  }

  private static final Logger logger = LoggerFactory.getLogger(JWTUtils.class);

  private static final RSAPrivateKey PRIVATE_KEY;
  private static final RSAPublicKey PUBLIC_KEY;
  static {
    String modulus =
        "120749774428185480467622030722535804071445078993623309709775427878906293937338059423076695854937532244466465395164541641368876529295825453848760144835049363522545908104302024165873971414491110512342791594610742544380402908598585190494003507524195754273822268813447403290817343077571516216147839402414940310061";
    String publicExponent = "65537";
    String privateExponent =
        "73923469942286919561803730971048133587965873619209827001168953680477872428610977313161774128992838682156392947263251899461404460204267953359475632559803617319478756560848229397545070273747796303141458040475889804016062973476403760709402857872540300632704514872361476749953797952016730690123983122643596231873";
    PRIVATE_KEY = RSAUtils.getPrivateKey(modulus, privateExponent);
    PUBLIC_KEY = RSAUtils.getPublicKey(modulus, publicExponent);
  }

  /**
   * 获取Token
   * 
   * @param uid 用户ID
   * @param exp 失效时间，单位分钟
   * @return
   */
  public static String getToken(String uid, int exp) {
    long endTime = Instant.now().toEpochMilli() + 1000L * 60 * exp;
    return Jwts.builder().setSubject(uid).setExpiration(new Date(endTime))
        .signWith(SignatureAlgorithm.RS512, PRIVATE_KEY).compact();
  }

  /**
   * 获取Token
   * 
   * @param uid 用户ID
   * @param exp 失效时间，单位毫秒
   * @return
   */
  public static String getQuickToken(String uid, int exp) {
    long endTime = Instant.now().toEpochMilli() + exp;
    return Jwts.builder().setSubject(uid).setExpiration(new Date(endTime))
        .signWith(SignatureAlgorithm.RS512, PRIVATE_KEY).compact();
  }

  /**
   * 获取Token
   * 
   * @param uid 用户ID
   * @return
   */
  public static String getToken(String uid) {
    long endTime = Instant.now().toEpochMilli() + 1000L * 60 * 30;
    return Jwts.builder().setSubject(uid).setExpiration(new Date(endTime))
        .signWith(SignatureAlgorithm.RS512, PRIVATE_KEY).compact();
  }

  /**
   * 检查Token是否合法
   * 
   * @param token
   * @return JWTResult
   */
  public static JWTResult checkToken(String token) {
    try {
      Claims claims = Jwts.parser().setSigningKey(PUBLIC_KEY).parseClaimsJws(token).getBody();
      String sub = claims.get("sub", String.class);
      logger.debug("token.sub : " + sub);
      return new JWTResult(true, sub, "合法请求", HttpStatus.OK.value());
    } catch (ExpiredJwtException e) {
      // 在解析JWT字符串时，如果‘过期时间字段’已经早于当前时间，将会抛出ExpiredJwtException异常，说明本次请求已经失效
      return new JWTResult(false, null, "token已过期", HttpStatus.PAYMENT_REQUIRED.value());
    } catch (io.jsonwebtoken.SignatureException e) {
      // 在解析JWT字符串时，如果密钥不正确，将会解析失败，抛出SignatureException异常，说明该JWT字符串是伪造的
      return new JWTResult(false, null, "非法请求", HttpStatus.FORBIDDEN.value());
    } catch (Exception e) {
      return new JWTResult(false, null, "非法请求", HttpStatus.FORBIDDEN.value());
    }
  }

  public static class JWTResult {
    private boolean status;
    private String uid;
    private String msg;
    private int code;

    public JWTResult() {
      super();
    }

    public JWTResult(boolean status, String uid, String msg, int code) {
      super();
      this.status = status;
      this.uid = uid;
      this.msg = msg;
      this.code = code;
    }

    public int getCode() {
      return code;
    }

    public void setCode(int code) {
      this.code = code;
    }

    public String getMsg() {
      return msg;
    }

    public void setMsg(String msg) {
      this.msg = msg;
    }

    public boolean isSuccess() {
      return status;
    }

    public void setStatus(boolean status) {
      this.status = status;
    }

    public String getUid() {
      return uid;
    }

    public void setUid(String uid) {
      this.uid = uid;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
      return "JWTResult [status=" + status + ", uid=" + uid + ", msg=" + msg + ", code=" + code
          + "]";
    }
  }
}
