package com.seglino.jingyi.common.log.pojo;

import com.seglino.jingyi.common.core.po.BaseEntity;

public class SysLog extends BaseEntity {
	private static final long serialVersionUID = 782425579822603297L;

	private Integer type;
	private String module;
	private String method;
	private String parameter;
	private String ip;
	private String detail;

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}
}
