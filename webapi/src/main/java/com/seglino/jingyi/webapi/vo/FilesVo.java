package com.seglino.jingyi.webapi.vo;

import com.seglino.jingyi.common.core.vo.BaseVo;

public class FilesVo extends BaseVo {
	private String name;
	private String type;
	private String url;

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
}
