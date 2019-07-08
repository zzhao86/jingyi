package com.seglino.jingyi.user.dto;

import java.util.List;

public class DeptTreeDto {
	private String id;
	private String parentId;
	private String name;
	private Long ddDeptId;
	private Long ddDeptPid;
	private Integer childCount;
	private List<DeptTreeDto> children;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getDdDeptId() {
		return ddDeptId;
	}

	public void setDdDeptId(Long ddDeptId) {
		this.ddDeptId = ddDeptId;
	}

	public Integer getChildCount() {
		return childCount;
	}

	public void setChildCount(Integer childCount) {
		this.childCount = childCount;
	}

	public Long getDdDeptPid() {
		return ddDeptPid;
	}

	public void setDdDeptPid(Long ddDeptPid) {
		this.ddDeptPid = ddDeptPid;
	}

	public List<DeptTreeDto> getChildren() {
		return children;
	}

	public void setChildren(List<DeptTreeDto> children) {
		this.children = children;
	}
}
