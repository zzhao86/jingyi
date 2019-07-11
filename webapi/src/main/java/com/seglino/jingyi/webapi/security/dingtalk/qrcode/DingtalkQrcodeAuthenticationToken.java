package com.seglino.jingyi.webapi.security.dingtalk.qrcode;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class DingtalkQrcodeAuthenticationToken extends AbstractAuthenticationToken {
	private static final long serialVersionUID = -484323818638077192L;

	private final Object principal;

	public DingtalkQrcodeAuthenticationToken(String code) {
		super(null);
		this.principal = code;
		super.setAuthenticated(false);
	}

	public DingtalkQrcodeAuthenticationToken(Object principal, Collection<? extends GrantedAuthority> authorities) {
		super(authorities);
		this.principal = principal;
		super.setAuthenticated(true);
	}

	@Override
	public Object getCredentials() {
		return null;
	}

	@Override
	public Object getPrincipal() {
		return this.principal;
	}

	@Override
	public void setAuthenticated(boolean authenticated) {
		super.setAuthenticated(authenticated);
	}

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
    }
}
