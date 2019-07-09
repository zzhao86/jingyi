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

import com.seglino.jingyi.security.token.MobileCodeAuthenticationToken;
import com.seglino.jingyi.user.pojo.User;
import com.seglino.jingyi.user.service.UserService;

public class MobileCodeAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private UserService userService;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String mobile = (authentication.getPrincipal() == null) ? "NONE_PROVIDED" : authentication.getName();
		String code = (String) authentication.getCredentials();
		if (StringUtils.isEmpty(code)) {
			throw new BadCredentialsException("验证码不能为空");
		}
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("mobile", mobile);
		User user = userService.detail(param);
		if (null == user) {
			throw new BadCredentialsException("用户不存在");
		}
		
		// 手机号验证码业务还没有开发，先用4个0验证
		if (!code.equals("0000")) {
			throw new BadCredentialsException("验证码不正确");
		}
		UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(user.getId(), code, listUserGrantedAuthorities(user.getId().toString()));
		result.setDetails(authentication.getDetails());
		return result;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return (MobileCodeAuthenticationToken.class.isAssignableFrom(authentication));
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
