package com.seglino.jingyi.user.pojo;

import java.io.Serializable;

import com.seglino.jingyi.common.core.po.BaseEntity;

public class Dept extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 6767648668347411050L;
	
	private String parentId;
	private String name;
	private Long ddDeptId;
	private Long ddDeptPid;
	
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
}
