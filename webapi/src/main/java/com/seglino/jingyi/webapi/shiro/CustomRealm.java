package com.seglino.jingyi.webapi.shiro;

import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.seglino.jingyi.user.pojo.User;
import com.seglino.jingyi.user.service.UserService;

public class CustomRealm extends AuthorizingRealm {
	@Autowired
	private UserService userService;

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
		String userid = (String) SecurityUtils.getSubject().getPrincipal();
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		// 获得该用户角色
		User user = userService.detailById(userid);

		// 设置该用户拥有的角色
		Set<String> set = new HashSet<String>();
		set.add(user.getType().toString());
		info.setRoles(set);
		return info;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
//		UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
//		User user = userService.detailById(token.getUsername());
//		return new SimpleAuthenticationInfo(token.getPrincipal(), "qrcode", user.getName());
		DingtalkAuthenticationToken token = (DingtalkAuthenticationToken)authenticationToken;
		System.out.println(token.getCode());
		System.out.println(token.getType());
		
		return new SimpleAuthenticationInfo();
	}

}
