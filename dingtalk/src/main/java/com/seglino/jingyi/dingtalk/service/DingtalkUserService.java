package com.seglino.jingyi.dingtalk.service;

import com.dingtalk.api.response.OapiUserGetAdminResponse;
import com.dingtalk.api.response.OapiUserGetResponse;

public interface DingtalkUserService {
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
}
