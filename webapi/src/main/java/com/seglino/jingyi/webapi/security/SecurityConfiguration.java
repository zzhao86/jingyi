package com.seglino.jingyi.webapi.security;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import com.seglino.jingyi.webapi.security.dingtalk.qrcode.DingtalkQrcodeUserService;
import com.seglino.jingyi.webapi.security.dingtalk.qrcode.DingtalkQrcodeAuthenticationConfig;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private DingtalkQrcodeUserService backUserService;

	@Autowired
	private DingtalkQrcodeAuthenticationConfig dingtalkQrcodeAuthenticationConfig;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.formLogin()
				.loginPage("/back/login")
			.and()
				.logout()
				.permitAll()
//				.logoutUrl("/back/logout")
				.logoutSuccessHandler(logoutSuccessHandler())
				.clearAuthentication(true)
				.invalidateHttpSession(true)
				.deleteCookies("JSESSIONID")
			.and()
				.authorizeRequests()
				.antMatchers("/", "/back/home", "/back/logout", "/back/**/login/**")
				.permitAll()
				.anyRequest()
				.authenticated()
			.and()
				.cors()
			.and()
				.apply(dingtalkQrcodeAuthenticationConfig);
	}

	@Bean
	public LogoutSuccessHandler logoutSuccessHandler() {
		return new LogoutSuccessHandler() {
			@Override
			public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
				try {
                    BackUser user = (BackUser) authentication.getPrincipal();
                    System.out.println(user.toString());
                } catch (Exception e) {
                }
				response.sendRedirect("/back/home");
			}
        };
	}

	@Bean
	protected PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(backUserService).passwordEncoder(passwordEncoder());
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/static/**/*");
	}
}
