package com.xangqun.springcloud.component.base.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

public class CheckUtil {
	
	public static void checkNumberParam(String value, String messagePrefix)
	{
		if (!NumberUtils.isDigits(value)){
			throw new IllegalArgumentException("[" + messagePrefix + "]输入非法");
		}
	}
	
	public final static void checkNotNull(Object obj)
	{
		if (obj == null){
			throw new NullPointerException("参数为空[obj=" + obj + "]");
		}
	}
	
	public final static void checkNotNull(Object obj, Object refObj)
	{
		if (obj == null){
			throw new NullPointerException("参数为空[obj=" + obj + "]" + "[refObj=" + refObj + "]");
		}
	}
	
	public final static void NotNull(Object obj, String message)
	{
		if (obj == null){
			throw new NullPointerException(message);
		}
	}
	
	public final static void checkNotEmpty(String string)
	{
		if (StringUtils.isEmpty(string)){
			throw new IllegalArgumentException("非法参数,参数为空");
		}
	}
	
	public final static void checkPhone(String phone)
	{
		if (VerifyUtil.isPhone(phone)){
			return;
		}
		throw new IllegalArgumentException("请输入11位数字手机号码" + "[phone=" + phone + "]");
	}
	
	final static String		strPwd					= "^((?=.*?\\d)(?=.*?[A-Za-z])|(?=.*?\\d)(?=.*?[!@#$%^&*/().,\\]\\[_+{}|:;<>?'\"`~-])|(?=.*?[A-Za-z])(?=.*?[!@#$%^&*/().,\\]\\[_+{}|:;<>?'\"`~-]))[\\dA-Za-z!@#$%^&*/().,\\]\\[_+{}|:;<>?'\"`~-]+$";
	final static Pattern	CHECK_PASSWORD_MATCH	= Pattern.compile(strPwd);
	
	public static void checkPassword(String password)
	{
		if (StringUtils.isEmpty(password)){
			throw new RuntimeException("密码需为8-16位大小写字母、数字、特殊字符，至少包含两种类型，不得与用户名相同");
		}
		if (password.length() < 8 || password.length() > 16){
			throw new RuntimeException("密码需为8-16位大小写字母、数字、特殊字符，至少包含两种类型，不得与用户名相同");
		}
		Matcher matche = CHECK_PASSWORD_MATCH.matcher(password);
		if (!matche.find()){
			throw new RuntimeException("密码需为8-16位大小写字母、数字、特殊字符，至少包含两种类型，不得与用户名相同");
		}
	}
	
	private final static String		emailPattern		= "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
	private final static Pattern	CHECK_EMAIL_MATCH	= Pattern.compile(emailPattern);
	
	public static void checkEmailPattern(String email)
	{
		if (StringUtils.isEmpty(email)){
			throw new RuntimeException("邮箱不能为空");
		}
		Matcher matche = CHECK_EMAIL_MATCH.matcher(email);
		if (!matche.find()){
			throw new RuntimeException("邮箱格式错误:" + email);
		}
	}
	
	private final static String		zipCodePattern		= "^[1-9]\\d{5}$";
	private final static Pattern	CHECK_ZIPCODE_MATCH	= Pattern.compile(zipCodePattern);
	
	public static void checkZipCodePattern(String zipCode)
	{
		// 允许输入的邮编为空
		if (!zipCode.equals("")){
			Matcher matche = CHECK_ZIPCODE_MATCH.matcher(zipCode);
			if (!matche.find()){
				throw new RuntimeException("无效的邮编:" + zipCode);
			}
		}
	}
}
