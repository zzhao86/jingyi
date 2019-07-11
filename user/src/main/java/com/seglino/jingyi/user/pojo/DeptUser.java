package com.seglino.jingyi.user.pojo;

import com.seglino.jingyi.common.core.po.BaseEntity;

public class DeptUser extends BaseEntity {
	private static final long serialVersionUID = 7871015740155283568L;

	private String deptId;
	private String userId;
	private Long sortNo;

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Long getSortNo() {
		return sortNo;
	}

	public void setSortNo(Long sortNo) {
		this.sortNo = sortNo;
	}
}
