package com.seglino.jingyi.dingtalk.service;

import com.dingtalk.api.response.OapiDepartmentListResponse;

public interface DingtalkDeptService {

	/**
	 * 获取部门列表
	 * 
	 * @param pid 钉钉父部门ID
	 * @return
	 */
	public OapiDepartmentListResponse getDeptList(String pid);

	/**
	 * 从钉钉组织架构中初始化部门数据到系统中
	 */
	public void initDeptData();
}
