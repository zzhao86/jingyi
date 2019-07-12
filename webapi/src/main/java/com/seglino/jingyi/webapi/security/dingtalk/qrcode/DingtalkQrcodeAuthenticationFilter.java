package com.seglino.jingyi.webapi.security.dingtalk.qrcode;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

public class DingtalkQrcodeAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
	public static final String QRCODE_KEY = "code";

	private String qrcodeParameter = QRCODE_KEY;
	private boolean postOnly = true;

	protected DingtalkQrcodeAuthenticationFilter() {
		super(new AntPathRequestMatcher("/back/dingtalk/login/qrcode", "GET"));
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
		if (postOnly && !request.getMethod().equals("GET")) {
			throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
		}
		String code = obtainQrcode(request);
		if (null == code) {
			code = "";
		}
		code = code.trim();
		DingtalkQrcodeAuthenticationToken authentication = new DingtalkQrcodeAuthenticationToken(code);
		setDetails(request, authentication);
		return this.getAuthenticationManager().authenticate(authentication);
	}

	protected void setDetails(HttpServletRequest request, DingtalkQrcodeAuthenticationToken authentication) {
		authentication.setDetails(authenticationDetailsSource.buildDetails(request));
	}

	protected String obtainQrcode(HttpServletRequest request) {
		return request.getParameter(qrcodeParameter);
	}

	public final String getQrcodeParameter() {
		return qrcodeParameter;
	}

	public void setQrcodeParameter(String qrcodeParameter) {
		this.qrcodeParameter = qrcodeParameter;
	}

	public boolean isPostOnly() {
		return postOnly;
	}

	public void setPostOnly(boolean postOnly) {
		this.postOnly = postOnly;
	}
}
