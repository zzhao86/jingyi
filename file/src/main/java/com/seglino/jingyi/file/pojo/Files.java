package com.seglino.jingyi.file.pojo;

import com.seglino.jingyi.common.core.po.BaseEntity;

public class Files extends BaseEntity {
	private static final long serialVersionUID = -362360550891641022L;
	
	private String name;
	private String type;
	private String url;
	private String MD5;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMD5() {
		return MD5;
	}

	public void setMD5(String MD5) {
		this.MD5 = MD5;
	}
}
