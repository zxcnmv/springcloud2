package com.xangqun.springcloud.component.base.http;


import com.xangqun.springcloud.component.base.enums.PlatformType;

public class H5PlatformUtil {

	public static PlatformType getPlatformType(String userAgent){
		/**User Agent中文名为用户代理，简称 UA，它是一个特殊字符串头，使得服务器
	    	能够识别客户使用的操作系统及版本、CPU 类型、浏览器及版本、浏览器渲染引擎、浏览器语言、浏览器插件等*/  
	    //客户端类型常量
		PlatformType platformType = PlatformType.PC;
		if (userAgent != null){
			boolean deviceIsWx = userAgent.indexOf("MicroMessenger")>0;
			boolean deviceIsWindowsPhone = userAgent.indexOf("Windows Phone") >= 0;
			boolean deviceIsAndroid = userAgent.indexOf("Android") > 0 && !deviceIsWindowsPhone;
			boolean deviceIsIOS = userAgent.indexOf("iPhone")>0 && !deviceIsWindowsPhone;
		    if(deviceIsWx){
		    	platformType = PlatformType.Wechat;
		    }else if(deviceIsAndroid){
		    	platformType = PlatformType.Android;
		    }else if(deviceIsIOS){
		    	platformType = PlatformType.IPhone;
		    }
		}
	    return platformType;
	}
}
