package com.seglino.jingyi.webapi.vo.back.log;

import java.util.Date;

import com.seglino.jingyi.common.core.vo.BaseVo;
import com.seglino.jingyi.common.log.dto.SysLogEntryData;

@SuppressWarnings("unused")
public class SysLogListVo extends BaseVo {
	private int type;
	private String typeName;
	private String module;
	private String method;
	private String ip;
	private Date date;
	private String userName;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getTypeName() {
		return SysLogEntryData.getTypeName(type);
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
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
