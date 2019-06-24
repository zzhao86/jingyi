//package com.seglino.jingyi.config;
//
//import java.io.IOException;
//import java.io.PrintWriter;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.DisabledException;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.builders.WebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.web.authentication.AuthenticationFailureHandler;
//import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
//
//import com.alibaba.fastjson.JSONObject;
//import com.seglino.jingyi.common.response.ApiResult;
//
//@Configuration
//@EnableGlobalMethodSecurity(prePostEnabled = true)
//public class WebApiSecurityConfig extends WebSecurityConfigurerAdapter {
//	
//	
//	
//
//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.userDetailsService(null).passwordEncoder(new BCryptPasswordEncoder());
//	}
//	
//	@Override
//	public void configure(WebSecurity web) throws Exception {
//		web.ignoring().antMatchers("/static/**", "/favicon.ico");
//	}
//
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		http.authorizeRequests().antMatchers("/", "/login").permitAll().anyRequest().authenticated().and().formLogin().loginPage("/login").failureHandler(new AuthenticationFailureHandler() {
//			@Override
//			public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
//				response.setContentType("application/json;charset=utf-8");
//				PrintWriter out = response.getWriter();
//				ApiResult aResult = new ApiResult();
//				if (exception instanceof UsernameNotFoundException || exception instanceof BadCredentialsException) {
//					aResult.AddError("用户名或密码输入错误");
//				} else if (exception instanceof DisabledException) {
//					aResult.AddError("账户已被禁用");
//				} else {
//					aResult.AddError("登录失败");
//				}
//				out.write(JSONObject.toJSONString(aResult));
//				out.flush();
//				out.close();
//			}
//		}).successHandler(new AuthenticationSuccessHandler() {
//			@Override
//			public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//				PrintWriter out = response.getWriter();
//				ApiResult aResult = new ApiResult();
//				aResult.setData("登录成功");
//				out.write(JSONObject.toJSONString(aResult));
//				out.flush();
//				out.close();
//			}
//		}).and().logout().permitAll().and().csrf().disable();
//	}
//}
