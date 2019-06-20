package com.seglino.jingyi.dingtalk.service;

import com.dingtalk.api.response.OapiUserGetuserinfoResponse;

public interface AuthService {
	
	/**
	 * 用免登码获取用户信息
	 * 
	 * @param code
	 * @return
	 */
	public OapiUserGetuserinfoResponse getUserInfo(String code);
}
