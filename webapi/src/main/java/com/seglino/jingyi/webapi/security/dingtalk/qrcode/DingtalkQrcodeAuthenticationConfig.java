package com.seglino.jingyi.webapi.security.dingtalk.qrcode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import com.seglino.jingyi.webapi.security.handler.BackAuthenticationFailureHandler;
import com.seglino.jingyi.webapi.security.handler.BackAuthenticationSuccessHandler;

@Component
public class DingtalkQrcodeAuthenticationConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

	@Autowired
	private BackAuthenticationSuccessHandler authenticationSuccessHandler;

	@Autowired
	private BackAuthenticationFailureHandler authenticationFailureHandler;

	@Autowired
	private DingtalkQrcodeUserService userDetailService;

	@Override
	public void configure(HttpSecurity http) throws Exception {
		DingtalkQrcodeAuthenticationFilter filter = new DingtalkQrcodeAuthenticationFilter();
		filter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
		filter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
		filter.setAuthenticationFailureHandler(authenticationFailureHandler);

		DingtalkQrcodeAuthenticationProvider provider = new DingtalkQrcodeAuthenticationProvider();
		provider.setBackUserService(userDetailService);

		http.authenticationProvider(provider).addFilterAfter(filter, UsernamePasswordAuthenticationFilter.class);

	}
}
