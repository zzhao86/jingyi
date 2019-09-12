package com.seglino.jingyi.common.log;

/**
 * 日志类型
 * 
 * @author ZZH
 *
 */
public enum LogType {
	/**
	 * 操作日志
	 */
	OPERATION("operation"),
	/**
	 * 异常日志
	 */
	THROWABLE("throwable"),
	/**
	 * 后台管理登录日志
	 */
	BACK_LOGIN("back_login"),
	/**
	 * 后台管理登出日志
	 */
	BACK_LOGOUT("back_logout"),
	/**
	 * 前端登录日志
	 */
	CLIENT_LOGIN("client_login"),
	/**
	 * 前端登出日志
	 */
	CLIENT_LOGOUT("client_logout");

	private LogType(String type) {
		this.type = type;
	}

	private String type;

	public String getType() {
		return this.type;
	}
}
