package com.seglino.jingyi.webapi.shiro;

import org.apache.shiro.authc.UsernamePasswordToken;

public class CustomToken extends UsernamePasswordToken {
	private static final long serialVersionUID = 1838313092149529085L;

	private LoginType loginType;

	private String code;
	private String state;

	public CustomToken(LoginType loginType, final String username, final String password) {
		super(username, password);
		this.loginType = loginType;
	}

	public CustomToken(LoginType loginType, String username, String password, String code, String state) {
		super(username, password);
		this.loginType = loginType;
		this.code = code;
		this.state = state;
	}

	public LoginType getLoginType() {
		return loginType;
	}

	public void setLoginType(LoginType loginType) {
		this.loginType = loginType;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
}
