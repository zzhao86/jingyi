package com.seglino.jingyi.webapi.shiro;

public enum LoginType {
	/**
	 * 通用登录方式
	 */
	COMMON("common"),
	
	/**
	 * 账号密码登录
	 */
	USER_PASSWORD("user_password"),
	
	/**
	 * 手机验证码登录
	 */
	PHONE_CODE("phone_code"),
	
	/**
	 * 钉钉企业内部免登
	 */
	DINGTALK_CORP("dingtalk_corp"),
	
	/**
	 * 钉钉管理后台应用免登
	 */
	DINGTALK_SSO("dingtalk_sso"),
	
	/**
	 * 钉钉扫码登录
	 */
	DINGTALK_QRCODE("dingtalk_qrcode");

	private String type;

	private LoginType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}
}
