package com.seglino.jingyi.webapi.security.dingtalk.qrcode;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.dingtalk.api.response.OapiSnsGetuserinfoBycodeResponse;
import com.seglino.jingyi.dingtalk.service.AuthService;
import com.seglino.jingyi.user.pojo.User;
import com.seglino.jingyi.user.service.UserService;
import com.seglino.jingyi.webapi.security.BackUser;

@Component
public class DingtalkQrcodeUserService implements UserDetailsService {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private AuthService authService;

	@Autowired
	private UserService userService;

	public BackUser loadUserbyCode(String code) throws UsernameNotFoundException {
		if (StringUtils.isEmpty(code)) {
			throw new UsernameNotFoundException("临时授权码无效");
		} else {
			OapiSnsGetuserinfoBycodeResponse response = authService.getUserDetailByQrCode(code);
			if (response.isSuccess()) {
				String unionid = response.getUserInfo().getUnionid();
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("unionid", unionid);
				User user = userService.detail(param);
				if (null != user) {
					if (user.getType() > 2) {
						throw new UsernameNotFoundException("用户权限不足");
					}
					return new BackUser(user.getId().toString(), user.getName(), user.getMobile(), user.getTel(), user.getEmail(), user.getPosition(), user.getAvatar(),
							AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_ADMI"));
				} else {
					throw new UsernameNotFoundException("未找到关联用户");
				}
			} else {
				throw new UsernameNotFoundException("临时授权码无效或授权失败，请重试！");
			}
		}
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		String code = username;
		if (StringUtils.isEmpty(code)) {
			logger.error("未发现临时授权码");
			return null;
		} else {
			OapiSnsGetuserinfoBycodeResponse response = authService.getUserDetailByQrCode(code);
			if (response.isSuccess()) {
				String unionid = response.getUserInfo().getUnionid();
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("unionid", unionid);
				User user = userService.detail(param);
				if (null != user) {
					return new BackUser(user.getId().toString(), user.getName(), user.getMobile(), user.getTel(), user.getEmail(), user.getPosition(), user.getAvatar(),
							AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_ADMI"));
				} else {
					return null;
				}
			} else {
				logger.error("临时授权码错误或授权失败，请重试！");
			}
		}
		return null;
	}
}
