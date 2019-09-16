package com.seglino.jingyi.common.log.dto;

import java.util.Date;

import com.seglino.jingyi.common.core.dto.BaseDto;

public class SysLogListDto extends BaseDto {
	private Integer type;
	private String module;
	private String method;
	private String ip;
	private Date date;
	private String userName;

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

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
