package com.seglino.jingyi.common.settings.pojo;

import com.seglino.jingyi.common.core.po.BaseEntity;

public class Settings extends BaseEntity {

	private static final long serialVersionUID = -835865453023461449L;

	private String code;
	private String name;
	private String value;
	private String type;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
