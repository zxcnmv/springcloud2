package com.xangqun.springcloud.component.base.enums;

import java.util.HashMap;
import java.util.Map;

public enum PlatformType {
	
	PC("pc","PC"),
	Wechat("wechat","微信"),
	IPhone("iphone", "苹果"),
	Android("Android", "安卓");

	private final String value;
	private final String desc;

	private PlatformType(String value, String desc) {
		this.value = value;
		this.desc = desc;
	}

	public String getValue() {
		return value;
	}

	public String getDesc() {
		return this.desc;
	}

	private static final Map<String, PlatformType> ENUMMAP = new HashMap<String, PlatformType>();
	static {
		for (PlatformType _enum : PlatformType.values()) {
			ENUMMAP.put(_enum.getValue(), _enum);
		}
	}

	public static PlatformType fromValue(String value) {
		PlatformType mType = ENUMMAP.get(value);
		return mType;
	}

	@Override
	public String toString() {
		return getValue();
	}
}
