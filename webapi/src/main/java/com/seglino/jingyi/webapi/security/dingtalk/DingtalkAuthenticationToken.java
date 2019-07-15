//package com.seglino.jingyi.webapi.security.dingtalk;
//
//import java.util.Collection;
//
//import org.springframework.security.authentication.AbstractAuthenticationToken;
//import org.springframework.security.core.GrantedAuthority;
//
//public class DingtalkAuthenticationToken extends AbstractAuthenticationToken {
//	private static final long serialVersionUID = -484323818638077192L;
//
//	private final Object principal;
//	
//	private String code;
//	private String type;
//
//	public DingtalkAuthenticationToken(String type,String code) {
//		super(null);
//		this.type = type;
//		this.principal = this.code = code;
//		super.setAuthenticated(false);
//	}
//
//	public DingtalkAuthenticationToken(Object principal, Collection<? extends GrantedAuthority> authorities) {
//		super(authorities);
//		this.principal = principal;
//		super.setAuthenticated(true);
//	}
//
//	@Override
//	public Object getCredentials() {
//		return null;
//	}
//
//	@Override
//	public Object getPrincipal() {
//		return this.principal;
//	}
//
//	@Override
//	public void setAuthenticated(boolean authenticated) {
//		super.setAuthenticated(authenticated);
//	}
//
//    @Override
//    public void eraseCredentials() {
//        super.eraseCredentials();
//    }
//
//	public String getCode() {
//		return code;
//	}
//
//	public void setCode(String code) {
//		this.code = code;
//	}
//
//	public String getType() {
//		return type;
//	}
//
//	public void setType(String type) {
//		this.type = type;
//	}
//}
