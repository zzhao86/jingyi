package com.seglino.jingyi.assets.dto;

import com.seglino.jingyi.common.core.dto.BaseDto;

public class AssetsCategoryListDto extends BaseDto {
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
