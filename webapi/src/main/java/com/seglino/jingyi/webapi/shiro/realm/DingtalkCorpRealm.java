package com.seglino.jingyi.webapi.shiro.realm;

import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.dingtalk.api.response.OapiUserGetuserinfoResponse;
import com.seglino.jingyi.dingtalk.service.DingtalkAuthService;
import com.seglino.jingyi.user.pojo.User;
import com.seglino.jingyi.user.service.UserService;
import com.seglino.jingyi.webapi.shiro.CustomToken;
import com.seglino.jingyi.webapi.shiro.LoginType;

/**
 * 钉钉企业内部应用免登
 * 
 * @author ZZH
 *
 */
public class DingtalkCorpRealm extends AuthorizingRealm {
	@Autowired
	private UserService userService;
	@Autowired
	private DingtalkAuthService authService;

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authToken) throws AuthenticationException {
		CustomToken token = (CustomToken) authToken;
		String code = token.getCode();
		if (StringUtils.isEmpty(code)) {
			throw new AuthenticationException("临时授权码获取失败");
		}
		OapiUserGetuserinfoResponse response = authService.getUserDetailByCorp(code);
		if (response.isSuccess()) {
			String ddUserId = response.getUserid();
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("ddUserId", ddUserId);
			User user = userService.detail(param);
			if (null == user) {
				throw new AuthenticationException("用户不存在");
			}
			if (user.getType() >= 2) {
				throw new AuthenticationException("用户权限不足");
			}
			if (user.getIsDeleted()) {
				throw new DisabledAccountException("账号已被禁用");
			}

			SimpleAuthenticationInfo authInfo = new SimpleAuthenticationInfo(user.getId(), code, LoginType.DINGTALK_CORP.getType());
			return authInfo;
		} else {
			throw new AuthenticationException("授权失败");
		}
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		return null;
	}

	@Override
	public boolean supports(AuthenticationToken token) {
		if (token instanceof CustomToken) {
			return ((CustomToken) token).getLoginType() == LoginType.DINGTALK_CORP;
		} else {
			return false;
		}
	}
}
