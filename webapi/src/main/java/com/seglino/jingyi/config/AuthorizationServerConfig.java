//package com.seglino.jingyi.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
//import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
//import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
//import org.springframework.security.oauth2.provider.approval.UserApprovalHandler;
//import org.springframework.security.oauth2.provider.token.TokenStore;
//
//@Configuration
//@EnableAuthorizationServer
//public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
//	private static final String PROP_CLIENTID = "clientid";
//	private static final String PROP_SECRET = "secret";
//
//	@Autowired
//	private TokenStore tokenStore;
//
//	@Autowired
//	private UserApprovalHandler userApprovalHandler;
//
//	// 认证方式
//	@Autowired
//	@Qualifier("authenticationManagerBean")
//	private AuthenticationManager authenticationManager;
//
//	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//		// 使用in-memory存储
//		clients.inMemory()
//				// client_id用来标识客户的Id
//				.withClient(PROP_CLIENTID)
//				// secret客户端安全码
//				.secret(PROP_SECRET)
//				// 允许授权范围
//				.scopes("read", "write")
//				// 客户端可以使用的权限
//				// .authorities("ROLE_ADMIN", "ROLE_USER")
//				// 允许授权类型
//				.authorizedGrantTypes("password", "refresh_token")
//				// accessToken有效期2小时
//				.accessTokenValiditySeconds(7200)
//				// refreshToken有效期1天
//				.refreshTokenValiditySeconds(86400);
//	}
//
//	@Override
//	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
//		endpoints.tokenStore(tokenStore).userApprovalHandler(userApprovalHandler).authenticationManager(authenticationManager);
//	}
//
//	@Override
//	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
//		super.configure(security);
//	}
//}
