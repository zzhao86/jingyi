package com.seglino.jingyi.dingtalk.service;

import com.dingtalk.api.response.OapiDepartmentGetResponse;
import com.dingtalk.api.response.OapiDepartmentListResponse;

public interface DingtalkDeptService {

	/**
	 * 获取部门列表
	 * 
	 * @param pid 钉钉父部门ID
	 * @param fetchChild 是否递归部门的全部子部门
	 * @return
	 */
	public OapiDepartmentListResponse getDeptList(String pid, boolean fetchChild);

	/**
	 * 获取部门列表
	 * 
	 * @param pid 钉钉父部门ID
	 * @return
	 */
	public OapiDepartmentListResponse getDeptList(String pid);
	
	/**
	 * 获取部门详情
	 * @param id 钉钉部门ID
	 * @return
	 */
	public OapiDepartmentGetResponse getDeptDetail(String id);
	

	/**
	 * 从钉钉组织架构中初始化部门数据到系统中
	 * @param pid 系统父部门ID，默认一级部门为空
	 * @param ddpid 钉钉父部门ID，默认一级部门为1
	 */
	public void initDeptData(String pid, String ddpid);
}
