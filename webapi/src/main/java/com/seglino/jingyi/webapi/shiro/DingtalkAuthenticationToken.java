package com.seglino.jingyi.webapi.shiro;

import org.apache.shiro.authc.HostAuthenticationToken;
import org.apache.shiro.authc.RememberMeAuthenticationToken;

public class DingtalkAuthenticationToken implements HostAuthenticationToken, RememberMeAuthenticationToken {
	private static final long serialVersionUID = 6228921616590590146L;

	private String type;
	private String code;

	private Object principal;
	private Object credential;
	private String host;

	public DingtalkAuthenticationToken(String type, String code) {
		this.type = type;
		this.code = code;
	}

	@Override
	public Object getPrincipal() {
		return principal;
	}

	@Override
	public Object getCredentials() {
		return credential;
	}

	@Override
	public boolean isRememberMe() {
		return false;
	}

	@Override
	public String getHost() {
		return host;
	}

	public void setPrincipal(Object principal) {
		this.principal = principal;
	}

	public void setCredentials(Object credential) {
		this.credential = credential;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
