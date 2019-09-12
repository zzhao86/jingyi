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
import org.springframework.context.annotation.Lazy;
import org.springframework.util.StringUtils;

import com.dingtalk.api.response.OapiSnsGetuserinfoBycodeResponse;
import com.seglino.jingyi.dingtalk.service.DingtalkAuthService;
import com.seglino.jingyi.user.pojo.User;
import com.seglino.jingyi.user.service.UserService;
import com.seglino.jingyi.webapi.shiro.CustomToken;
import com.seglino.jingyi.webapi.shiro.LoginType;

/**
 * 钉钉扫码登系统录管理后台
 * 
 * @author ZZH
 *
 */
public class DingtalkQrcodeRealm extends AuthorizingRealm {
	@Lazy
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
		OapiSnsGetuserinfoBycodeResponse response = authService.getUserDetailByQrCode(code);
		if (response.isSuccess()) {
			String unionid = response.getUserInfo().getUnionid();
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("unionid", unionid);
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

			SimpleAuthenticationInfo authInfo = new SimpleAuthenticationInfo(user.getId(), code, LoginType.DINGTALK_QRCODE.getType());
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
			return ((CustomToken) token).getLoginType() == LoginType.DINGTALK_QRCODE;
		} else {
			return false;
		}
	}
}
