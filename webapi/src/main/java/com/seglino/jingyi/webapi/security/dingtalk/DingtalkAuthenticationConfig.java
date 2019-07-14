package com.seglino.jingyi.webapi.security.dingtalk;

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
public class DingtalkAuthenticationConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

	@Autowired
	private BackAuthenticationSuccessHandler authenticationSuccessHandler;

	@Autowired
	private BackAuthenticationFailureHandler authenticationFailureHandler;

	@Autowired
	private DingtalkUserService userDetailService;

	@Override
	public void configure(HttpSecurity http) throws Exception {
		DingtalkAuthenticationFilter filter = new DingtalkAuthenticationFilter();
		filter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
		filter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
		filter.setAuthenticationFailureHandler(authenticationFailureHandler);

		DingtalkAuthenticationProvider provider = new DingtalkAuthenticationProvider();
		provider.setBackUserService(userDetailService);

		http.authenticationProvider(provider).addFilterAfter(filter, UsernamePasswordAuthenticationFilter.class);

	}
}
