//package com.seglino.jingyi.config;
//
//import java.io.IOException;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.oauth2.common.OAuth2AccessToken;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
//import org.springframework.security.oauth2.provider.token.TokenStore;
//import org.springframework.security.web.AuthenticationEntryPoint;
//import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
//
//@Configuration
//@EnableResourceServer
//public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
//	private Logger logger = LoggerFactory.getLogger(ResourceServerConfig.class);
//	private static final String BEARER_AUTHENTICATION = "Bearer ";
//	private static final String HEADER_AUTHORIZATION = "authorization";
//
//	@Autowired
//	private TokenStore tokenStore;
//
//	@Override
//	public void configure(HttpSecurity http) throws Exception {
//		http.exceptionHandling()
//				// 认证进入点
//				.authenticationEntryPoint(new AuthenticationEntryPoint() {
//					@Override
//					public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
//						response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Access Denied");
//					}
//				})
//				// 登录退出
//				.and().logout().logoutUrl("/logout").logoutSuccessHandler(new LogoutSuccessHandler() {
//					@Override
//					public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//						String token = request.getHeader(HEADER_AUTHORIZATION);
//						if (token != null && token.startsWith(BEARER_AUTHENTICATION)) {
//							OAuth2AccessToken oAuth2AccessToken = tokenStore.readAccessToken(token.split(" ")[0]);
//							if (oAuth2AccessToken != null) {
//								tokenStore.removeAccessToken(oAuth2AccessToken);
//							}
//						}
//						response.setStatus(HttpServletResponse.SC_OK);
//					}
//				})
//				// 认证接口配置
//				.and().authorizeRequests().antMatchers("/").permitAll().antMatchers("/client/**").authenticated();
//	}
//}
