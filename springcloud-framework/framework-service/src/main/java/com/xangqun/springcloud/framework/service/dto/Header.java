/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.xangqun.springcloud.framework.service.dto;

import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * Header
 * @author wanghongben
 * @since 2018年1月24日
 */
public class Header {

	@NotNull(message = "framework.request.head.businessId.null")
	private String businessId;
	@NotNull(message = "framework.request.head.srcSysId.null")
	private String sourceSysId;
	@NotNull(message = "framework.request.head.tgtSysId.null")
	private String targetSysId;
	@NotNull(message = "createTimestamp must not be blank in request head!")
	private long createTimestamp;
	@NotNull(message = "charset must not be blank in request head!")
	private String charset;
	@NotNull(message = "contentType must not be blank in request head!")
	private String contentType;

	private Map<String, Object> extAttributes;

	/**
	 * @Return the String businessId
	 */
	public String getBusinessId() {
		return businessId;
	}

	/**
	 * @Param String businessId to set
	 */
	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	/**
	 * @Return the String sourceSysId
	 */
	public String getSourceSysId() {
		return sourceSysId;
	}

	/**
	 * @Param String sourceSysId to set
	 */
	public void setSourceSysId(String sourceSysId) {
		this.sourceSysId = sourceSysId;
	}

	/**
	 * @Return the String targetSysId
	 */
	public String getTargetSysId() {
		return targetSysId;
	}

	/**
	 * @Param String targetSysId to set
	 */
	public void setTargetSysId(String targetSysId) {
		this.targetSysId = targetSysId;
	}

	/**
	 * @Return the long createTimestamp
	 */
	public long getCreateTimestamp() {
		return createTimestamp;
	}

	/**
	 * @Param long createTimestamp to set
	 */
	public void setCreateTimestamp(long createTimestamp) {
		this.createTimestamp = createTimestamp;
	}

	/**
	 * @Return the String charset
	 */
	public String getCharset() {
		return charset;
	}

	/**
	 * @Param String charset to set
	 */
	public void setCharset(String charset) {
		this.charset = charset;
	}

	/**
	 * @Return the String contentType
	 */
	public String getContentType() {
		return contentType;
	}

	/**
	 * @Param String contentType to set
	 */
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	/**
	 * @Return the Map<String,Object> extAttributes
	 */
	public Map<String, Object> getExtAttributes() {
		return extAttributes;
	}

	/**
	 * @Param Map<String,Object> extAttributes to set
	 */
	public void setExtAttributes(Map<String, Object> extAttributes) {
		this.extAttributes = extAttributes;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Header [businessId=" + businessId + ", sourceSysId="
				+ sourceSysId + ", targetSysId=" + targetSysId
				+ ", createTimestamp=" + createTimestamp + ", charset="
				+ charset + ", contentType=" + contentType + ", extAttributes="
				+ extAttributes + "]";
	}

}
