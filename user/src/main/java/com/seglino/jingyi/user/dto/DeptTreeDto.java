package com.seglino.jingyi.user.dto;

public class DeptTreeDto {
	private String id;
	private String parentId;
	private String name;
	private Long ddDeptId;
	private Long ddDeptPid;
	private Integer childCount;
	
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
	public Long getDdDeptPid() {
		return ddDeptPid;
	}
	public void setDdDeptPid(Long ddDeptPid) {
		this.ddDeptPid = ddDeptPid;
	}
	public Integer getChildCount() {
		return childCount;
	}
	public void setChildCount(Integer childCount) {
		this.childCount = childCount;
	}
}
