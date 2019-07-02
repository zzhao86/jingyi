package com.seglino.jingyi.dingtalk.service;

import com.dingtalk.api.response.OapiUserGetAdminResponse;
import com.dingtalk.api.response.OapiUserGetDeptMemberResponse;
import com.dingtalk.api.response.OapiUserGetResponse;

public interface DingtalkUserService {
	
	/**
	 * 获取部门用户userid列表
	 * @param deptId 钉钉部门ID
	 * @return
	 */
	public OapiUserGetDeptMemberResponse getUserIds(String deptId);
	
	/**
	 * 获取用户详情
	 * 
	 * @param userid 钉钉用户ID
	 * @return
	 */
	public OapiUserGetResponse getUserDetail(String userid);

	/**
	 * 获取管理员列表
	 * 
	 * @return
	 */
	public OapiUserGetAdminResponse getAdminList();

	/**
	 * 将钉钉管理员初始化为系统管理员
	 * 
	 * @return
	 */
	public int initAdmin();

	/**
	 * 从钉钉通讯录中初始化用户数据
	 * 
	 * @param deptId 钉钉部门ID
	 * @return
	 */
	public int initUserData(String deptId);
}
