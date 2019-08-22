package com.seglino.jingyi.assets.pojo;

import com.seglino.jingyi.common.core.po.BaseEntity;

public class AssetsPosition extends BaseEntity {
	private static final long serialVersionUID = -5786015090655675038L;

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
