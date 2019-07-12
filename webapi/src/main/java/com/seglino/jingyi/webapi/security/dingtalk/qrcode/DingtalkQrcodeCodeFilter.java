//package com.seglino.jingyi.webapi.security.dingtalk.qrcode;
//
//import java.io.IOException;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import com.seglino.jingyi.webapi.security.handler.BackAuthenticationFailureHandler;
//
//@Component
//public class DingtalkQrcodeCodeFilter extends OncePerRequestFilter {
//	@Autowired
//	private BackAuthenticationFailureHandler authenticationFailureHandler;
//
//	@Override
//	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//		if ("/back/dingtalk/login/qrcode".equalsIgnoreCase(request.getRequestURI())/* && "post".equalsIgnoreCase(request.getMethod())*/) {
//			try {
//				validateCode(request);
//			} catch (AuthenticationException e) {
//				authenticationFailureHandler.onAuthenticationFailure(request, response, e);
//				return;
//			}
//		}
//		filterChain.doFilter(request, response);
//	}
//
//	private void validateCode(HttpServletRequest request) {
//		// 验证code的正确性
//
//	}
//}
