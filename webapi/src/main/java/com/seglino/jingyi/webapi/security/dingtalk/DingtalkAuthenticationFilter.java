//package com.seglino.jingyi.webapi.security.dingtalk;
//
//import java.io.IOException;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.security.authentication.AuthenticationServiceException;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
//import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
//
//public class DingtalkAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
//	public static final String TYPE_KEY = "type";
//	public static final String CODE_KEY = "code";
//
//	private String typeParameter = TYPE_KEY;
//	private String codeParameter = CODE_KEY;
//	private boolean postOnly = true;
//
//	protected DingtalkAuthenticationFilter() {
//		super(new AntPathRequestMatcher("/back/dingtalk/login", "GET"));
//	}
//
//	@Override
//	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
//			throws AuthenticationException, IOException, ServletException {
//		if (postOnly && !request.getMethod().equals("GET")) {
//			throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
//		}
//
//		String type = obtainTypeParameter(request);
//		String code = obtainCodeParameter(request);
//		DingtalkAuthenticationToken authentication = new DingtalkAuthenticationToken(type,
//				null == code ? "" : code.trim());
//		authentication.setDetails(authenticationDetailsSource.buildDetails(request));
//		return this.getAuthenticationManager().authenticate(authentication);
//	}
//
//	protected String obtainCodeParameter(HttpServletRequest request) {
//		return request.getParameter(codeParameter);
//	}
//
//	protected String obtainTypeParameter(HttpServletRequest request) {
//		return request.getParameter(typeParameter);
//	}
//
//	public boolean isPostOnly() {
//		return postOnly;
//	}
//
//	public void setPostOnly(boolean postOnly) {
//		this.postOnly = postOnly;
//	}
//}
