package com.seglino.jingyi.webapi.vo.back.assets;

import com.seglino.jingyi.common.core.vo.BaseVo;

public class AssetsCategoryListVo extends BaseVo {
	private String name;
	private String parentId;
	private String parentName;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
}
