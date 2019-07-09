package com.seglino.jingyi.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.TokenApprovalStore;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {
	@Autowired
	@Qualifier("authenticationManagerBean")
	private AuthenticationManager authenticationManager;
	@Autowired
	@Qualifier("customClientDetailsService")
	private ClientDetailsService clientDetailsService;
	@Autowired
	private UserDetailsService customUserDetailsService;
	@Autowired
    private DataSource dataSource;
	@Autowired
	private TokenStore tokenStore;

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.withClientDetails(clientDetailsService);
		/*clients.inMemory()
			.withClient("client")
			// $2a$10$dzJhPcN86a3sc/FEOuzEWunUhkOyu8.76oS4yIZbXQr2L.v6tBkuK
			.secret(new BCryptPasswordEncoder().encode("secret"))
			.authorizedGrantTypes("client_credentials", "password", "refresh_token", "authorization_code")
			.scopes("all", "user_info")
			.autoApprove(false) // true: 不会跳转到授权页面
			.redirectUris("http://localhost:8080/login");*/
	}
	
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints
			.authenticationManager(this.authenticationManager)
			.tokenStore(tokenStore)
			.userDetailsService(customUserDetailsService);
	}
	
	@Override
	public void configure(AuthorizationServerSecurityConfigurer oauthServer)
			throws Exception {
		oauthServer
			.tokenKeyAccess("permitAll()")
			.checkTokenAccess("isAuthenticated()");
	}
	
	@Bean
	public TokenStore tokenStore() {
		// return new InMemoryTokenStore();
		return new JdbcTokenStore(dataSource);
	}
	
	@Bean
    public ApprovalStore approvalStore() {
		TokenApprovalStore store = new TokenApprovalStore();
		store.setTokenStore(tokenStore);
        return store;
    }
	
	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
