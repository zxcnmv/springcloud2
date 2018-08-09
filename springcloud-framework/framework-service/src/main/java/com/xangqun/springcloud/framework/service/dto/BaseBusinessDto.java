/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.xangqun.springcloud.framework.service.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * BaseBusinessDto
 * @author wanghongben
 * @since 2018年1月24日
 */
public class BaseBusinessDto implements Serializable {

	/**
	 * @Field long serialVersionUID
	 */
	private static final long serialVersionUID = -806096574275586422L;
	private Object traceId;
	private String spanId;
	private String businessId;
	private String sourceSysId;
	private String targetSysId;
	private Map<String, Object> extAttributes = new HashMap<String, Object>();

	public Object getTraceId() {
		return traceId;
	}

	public void setTraceId(Object traceId) {
		this.traceId = traceId;
	}

	public String getSpanId() {
		return spanId;
	}

	public void setSpanId(String spanId) {
		this.spanId = spanId;
	}

	public String getBusinessId() {
		return businessId;
	}

	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	public String getSourceSysId() {
		return sourceSysId;
	}

	public void setSourceSysId(String sourceSysId) {
		this.sourceSysId = sourceSysId;
	}

	public String getTargetSysId() {
		return targetSysId;
	}

	public void setTargetSysId(String targetSysId) {
		this.targetSysId = targetSysId;
	}

	public Map<String, Object> getExtAttributes() {
		return extAttributes;
	}

	public void setExtAttributes(Map<String, Object> extAttributes) {
		this.extAttributes = extAttributes;
	}

	public void clearMetaData() {
		this.traceId = null;
		this.spanId = null;
		this.businessId = null;
		this.sourceSysId = null;
		this.targetSysId = null;
		this.extAttributes = null;
	}

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("traceId", traceId)
                .append("spanId", spanId)
                .append("businessId", businessId)
                .append("sourceSysId", sourceSysId)
                .append("targetSysId", targetSysId)
                .append("extAttributes", extAttributes)
                .toString();
    }

}
