package com.seglino.jingyi.webapi.shiro;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authc.pam.AtLeastOneSuccessfulStrategy;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.seglino.jingyi.webapi.shiro.realm.DingtalkQrcodeRealm;
import com.seglino.jingyi.webapi.shiro.realm.DingtalkSsoRealm;

@Configuration
public class ShiroConfiguration {

	@Bean
	public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
		ShiroFilterFactoryBean filter = new ShiroFilterFactoryBean();
		filter.setSecurityManager(securityManager);
		filter.setLoginUrl("/account/login");
		filter.setUnauthorizedUrl("/account/login");

		// 权限控制map.
		Map<String, String> filterChainDefinitionMap = new HashMap<String, String>();
		// 公共请求
		filterChainDefinitionMap.put("/back", "anon");
		filterChainDefinitionMap.put("/account/**", "anon");
		// 静态资源
		filterChainDefinitionMap.put("/static/**", "anon");

		// 此处需要添加一个kickout，上面添加的自定义拦截器才能生效
		filterChainDefinitionMap.put("/back/**", "authc");// 表示需要认证才可以访问
		filter.setFilterChainDefinitionMap(filterChainDefinitionMap);
		return filter;
	}

	@Bean
	public SecurityManager securityManager() {
		DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
		manager.setAuthenticator(modularRealmAuthenticator());

		List<Realm> realms = new ArrayList<Realm>();
		realms.add(dingtalkQrcodeRealm());
		realms.add(dingtalkSsoRealm());
		manager.setRealms(realms);
		return manager;
	}

	/**
	 * 自定义的Realm管理，主要针对多realm
	 */
	@Bean("customModularRealmAuthenticator")
	public CustomModularRealmAuthenticator modularRealmAuthenticator() {
		CustomModularRealmAuthenticator authenticator = new CustomModularRealmAuthenticator();
		// 设置realm判断条件
		authenticator.setAuthenticationStrategy(new AtLeastOneSuccessfulStrategy());
		return authenticator;
	}

	@Bean
	public DingtalkQrcodeRealm dingtalkQrcodeRealm() {
		DingtalkQrcodeRealm realm = new DingtalkQrcodeRealm();
		realm.setName(LoginType.DINGTALK_QRCODE.getType());
		return realm;
	}

	@Bean
	public DingtalkSsoRealm dingtalkSsoRealm() {
		DingtalkSsoRealm realm = new DingtalkSsoRealm();
		realm.setName(LoginType.DINGTALK_SSO.getType());
		return realm;
	}

	/***
	 * 授权所用配置
	 *
	 * @return
	 */
	@Bean
	public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
		DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
		defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
		return defaultAdvisorAutoProxyCreator;
	}

	/***
	 * 使授权注解起作用不如不想配置可以在pom文件中加入 <dependency> <groupId>org.springframework.boot</groupId> <artifactId>spring-boot-starter-aop</artifactId> </dependency>
	 * 
	 * @param securityManager
	 * @return
	 */
	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
		AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
		advisor.setSecurityManager(securityManager);
		return advisor;
	}

	/**
	 * Shiro生命周期处理器 此方法需要用static作为修饰词，否则无法通过@Value()注解的方式获取配置文件的值
	 *
	 */
	@Bean
	public static LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}
}
