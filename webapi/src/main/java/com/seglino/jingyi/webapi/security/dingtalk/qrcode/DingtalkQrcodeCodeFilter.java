package com.seglino.jingyi.webapi.security.dingtalk.qrcode;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.web.filter.OncePerRequestFilter;

public class DingtalkQrcodeCodeFilter extends OncePerRequestFilter {
	@Autowired
	private AuthenticationFailureHandler authenticationFailureHandler;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		if ("/back/dingtalk/login".equalsIgnoreCase(request.getRequestURI()) && "post".equalsIgnoreCase(request.getMethod())) {
			try {
				validateCode(request);
			} catch (AuthenticationException e) {
				authenticationFailureHandler.onAuthenticationFailure(request, response, e);
				return;
			}
		}
		filterChain.doFilter(request, response);
	}

	private void validateCode(HttpServletRequest request) {
		// 验证code的正确性

	}
}
