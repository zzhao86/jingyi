package com.seglino.jingyi.dingtalk.service;

import com.dingtalk.api.response.OapiSsoGettokenResponse;
import com.dingtalk.api.response.OapiUserGetuserinfoResponse;

public interface AuthService {

	/**
	 * 用免登码获取用户信息
	 * 
	 * @param code
	 * @return
	 */
	public OapiUserGetuserinfoResponse getUserDetail(String code);

	/**
	 * 获取管理后台免登接口凭证
	 * 
	 * @return
	 */
	public OapiSsoGettokenResponse getSsoToken();
}
