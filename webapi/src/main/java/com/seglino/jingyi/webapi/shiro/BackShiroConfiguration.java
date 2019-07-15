package com.seglino.jingyi.webapi.shiro;

import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.spring.config.ShiroConfiguration;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BackShiroConfiguration extends ShiroConfiguration {

	@Bean
	public ShiroFilterFactoryBean shiroFilter(org.apache.shiro.mgt.SecurityManager securityManager) {
		ShiroFilterFactoryBean filter = new ShiroFilterFactoryBean();
		// 必须设置 SecurityManager
		filter.setSecurityManager(securityManager);
		// 设置登录页面URL
		filter.setLoginUrl("/login");
		//设置URL拦截器。anon表示不需要权限验证
		Map<String, String> filterChainDefinitionMap = new HashMap<String, String>();
		filterChainDefinitionMap.put("/back/**", "roles[1]");
		filterChainDefinitionMap.put("/account/notlogin", "anon");
		filter.setFilterChainDefinitionMap(filterChainDefinitionMap);
		return filter;
	}
	

    /**
     * 注入 securityManager
     */
    @Bean
    public org.apache.shiro.mgt.SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 设置realm.
        securityManager.setRealm(customRealm());
        return securityManager;
    }
    /**
     * 自定义身份认证 realm;
     * 必须写这个类，并加上 @Bean 注解，目的是注入 CustomRealm，
     * 否则会影响 CustomRealm类 中其他类的依赖注入
     */
    @Bean
    public CustomRealm customRealm() {
        return new CustomRealm();
    }
}
