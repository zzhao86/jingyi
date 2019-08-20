package com.seglino.jingyi.assets.pojo;

import com.seglino.jingyi.common.core.po.BaseEntity;

public class AssetsCategory extends BaseEntity {
	private static final long serialVersionUID = -4233874337375817676L;

	private String name;
	private String parentId;

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
}
