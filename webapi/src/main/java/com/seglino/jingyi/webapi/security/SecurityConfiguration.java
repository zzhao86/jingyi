//package com.seglino.jingyi.webapi.security;
//
//import java.util.Arrays;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.builders.WebSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.CorsConfigurationSource;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//
//import com.seglino.jingyi.webapi.security.dingtalk.DingtalkAuthenticationConfig;
//import com.seglino.jingyi.webapi.security.dingtalk.DingtalkUserService;
//import com.seglino.jingyi.webapi.security.handler.BackLogoutSuccessHandler;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
//
//	@Autowired
//	private DingtalkUserService backUserService;
//
//	@Autowired
//	private DingtalkAuthenticationConfig dingtalkQrcodeAuthenticationConfig;
//
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//
//		// 接口是否需要认证配置
//		http.authorizeRequests().antMatchers("/", "/back/home", "/back/**/login/**").permitAll().anyRequest()
//				.authenticated();
//
//		// 用户登录配置
//		http.formLogin().loginPage("/back/login");
//
//		// 用户注销配置
//		http.logout().permitAll().logoutUrl("/back/logout").logoutSuccessHandler(logoutSuccessHandler())
//				.clearAuthentication(true).invalidateHttpSession(true).deleteCookies("JSESSIONID");
//
//		// 接口跨域配置
//		http.cors().configurationSource(corsConfigurationSource());
//
//		// 应用自定义配置
//		http.apply(dingtalkQrcodeAuthenticationConfig);
//		
//		http.csrf().disable(); 
//	}
//
//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.userDetailsService(backUserService).passwordEncoder(passwordEncoder());
//	}
//
//	/**
//	 * 用户注销成功
//	 * 
//	 * @return
//	 */
//	@Bean
//	public LogoutSuccessHandler logoutSuccessHandler() {
//		return new BackLogoutSuccessHandler();
//	}
//
//	@Bean
//	protected PasswordEncoder passwordEncoder() {
//		return new BCryptPasswordEncoder();
//	}
//
//	/**
//	 * 跨域设置
//	 * 
//	 * @return
//	 */
//	@Bean
//	protected CorsConfigurationSource corsConfigurationSource() {
//		CorsConfiguration configuration = new CorsConfiguration();
//		configuration.setAllowedOrigins(Arrays.asList("*"));
//		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
//		configuration.setAllowCredentials(true);
//		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//		source.registerCorsConfiguration("/**", configuration);
//		return source;
//	}
//
//	/**
//	 * 静态资源配置
//	 */
//	@Override
//	public void configure(WebSecurity web) throws Exception {
//		web.ignoring().antMatchers("/static/**/*");
//	}
//}
