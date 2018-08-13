/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.xangqun.springcloud.component.base.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * ip工具类
 * 
 * @author songjie
 * @since 2018年1月17日
 */
public class IPUtils {
	private static final Logger logger = LoggerFactory.getLogger(IPUtils.class);

	public static ThreadLocal<String> IP_THREADLOCAL=new ThreadLocal();
	/**
	 * ip是否活动
	 * 
	 * @param ips
	 * @return boolean
	 */
	public static boolean isRunable(String ips) {
		String localIp = getIp();
		if (StringUtils.isNotBlank(ips)) {
			for (String configIp : ips.split(",")) {
				if (localIp.equals(configIp)) {
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * 多IP处理，可以得到最终ip
	 * 
	 * @return String
	 */
	public static String getIp() {
		if(IP_THREADLOCAL.get()!=null){
			return IP_THREADLOCAL.get();
		}
		String localip = null;// 本地IP，如果没有配置外网IP则返回它
		String netip = null;// 外网IP
		try {
			Enumeration<NetworkInterface> netInterfaces = NetworkInterface
					.getNetworkInterfaces();
			InetAddress ip = null;
			boolean finded = false;// 是否找到外网IP
			while (netInterfaces.hasMoreElements() && !finded) {
				NetworkInterface ni = netInterfaces.nextElement();
				Enumeration<InetAddress> address = ni.getInetAddresses();
				while (address.hasMoreElements()) {
					ip = address.nextElement();
					if (!ip.isSiteLocalAddress() && !ip.isLoopbackAddress()
							&& ip.getHostAddress().indexOf(":") == -1) {// 外网IP
						netip = ip.getHostAddress();
						finded = true;
						break;
					} else if (ip.isSiteLocalAddress()
							&& !ip.isLoopbackAddress()
							&& ip.getHostAddress().indexOf(":") == -1) {// 内网IP
						localip = ip.getHostAddress();
					}
				}
			}
		} catch (SocketException e) {
			logger.error(e.getMessage());
		}
		if (netip != null && !"".equals(netip)) {
			IP_THREADLOCAL.set(netip);
			return netip;
		} else {
			IP_THREADLOCAL.set(localip);
			return localip;
		}
	}

	/**
	 * 将字符串表示的ip地址转换为long表示.
	 *
	 * @param ip ip地址
	 * @return 以32位整数表示的ip地址
	 */
	public static final long ip2Long(final String ip) {
		String regexpIp="(\\d{1,3}\\.){3}\\d{1,3}";
		if ( !ip.matches(regexpIp)) {
			throw new IllegalArgumentException("[" + ip + "]不是有效的ip地址");
		}
		final String[] ipNums = ip.split("\\.");
		return (Long.parseLong(ipNums[0]) << 24)
				|(Long.parseLong(ipNums[1]) << 16)
				| (Long.parseLong(ipNums[2]) << 8)
				| (Long.parseLong(ipNums[3]));
	}

	public static  String longToIp(long ip) {
		return ((ip >> 24) & 0xFF) + "."
				+ ((ip >> 16) & 0xFF) + "."
				+ ((ip >> 8) & 0xFF) + "."
				+ (ip & 0xFF);
	}
}
