package com.seglino.jingyi.security.provider;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.util.StringUtils;

import com.seglino.jingyi.user.pojo.User;
import com.seglino.jingyi.user.service.UserService;

public class UsernamePasswordAuthenticationProvider implements AuthenticationProvider {
	@Autowired
	private UserService userService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String account = (authentication.getPrincipal() == null) ? "NONE_PROVIDED" : authentication.getName();
		String password = (String) authentication.getCredentials();
		if (StringUtils.isEmpty(password)) {
			throw new BadCredentialsException("密码不能为空");
		}
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("id", account);
		User user = userService.detail(param);
		if (null == user) {
			throw new BadCredentialsException("用户不存在");
		}
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user.getId(), password, listUserGrantedAuthorities(user.getId().toString()));
		token.setDetails(authentication.getDetails());
		return token;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
	}

	private Set<GrantedAuthority> listUserGrantedAuthorities(String userid) {
		Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
		if (StringUtils.isEmpty(userid)) {
			return authorities;
		}
		authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
		return authorities;
	}
}
