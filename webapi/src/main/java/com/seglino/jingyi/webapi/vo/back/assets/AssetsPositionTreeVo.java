package com.seglino.jingyi.webapi.vo.back.assets;

import java.util.List;

import com.seglino.jingyi.assets.dto.AssetsCategoryTreeDto;
import com.seglino.jingyi.common.core.vo.BaseVo;

public class AssetsPositionTreeVo extends BaseVo {
	private String label;
	private Object value;
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

	public List<AssetsCategoryTreeDto> getChildren() {
		return children;
	}

	public void setChildren(List<AssetsCategoryTreeDto> children) {
		this.children = children;
	}

}
