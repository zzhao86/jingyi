package com.seglino.jingyi.webapi.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class BackUserService implements UserDetailsService {
	private Logger logger = LoggerFactory.getLogger(getClass());


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		logger.info("用户：{}", username);


		// 参数分别是：用户名，密码，用户权限
		User user = new User(username, "123456", AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_ADMI"));
		return user;
	}

}
