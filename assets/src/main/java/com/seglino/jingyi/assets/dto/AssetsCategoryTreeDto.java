package com.seglino.jingyi.assets.dto;

import java.util.List;

import com.seglino.jingyi.common.core.dto.BaseDto;

public class AssetsCategoryTreeDto extends BaseDto {
	private String label;
	private Object value;
	private int childCount;
	private List<AssetsCategoryTreeDto> children;

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public int getChildCount() {
		return childCount;
	}

	public void setChildCount(int childCount) {
		this.childCount = childCount;
	}

	public List<AssetsCategoryTreeDto> getChildren() {
		return children;
	}

	public void setChildren(List<AssetsCategoryTreeDto> children) {
		this.children = children;
	}
}
