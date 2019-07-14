package com.seglino.jingyi.webapi.security.dingtalk;

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
import com.dingtalk.api.response.OapiSsoGetuserinfoResponse;
import com.seglino.jingyi.dingtalk.service.AuthService;
import com.seglino.jingyi.user.pojo.User;
import com.seglino.jingyi.user.service.UserService;
import com.seglino.jingyi.webapi.security.BackUser;

@Component
public class DingtalkUserService implements UserDetailsService {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private AuthService authService;

	@Autowired
	private UserService userService;

	/**
	 * 钉钉扫码登录
	 * 
	 * @param code
	 * @return
	 * @throws UsernameNotFoundException
	 */
	public BackUser loadUserByQrcode(String code) throws UsernameNotFoundException {
		if (StringUtils.isEmpty(code)) {
			logger.info("临时授权码无效");
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
						logger.info("用户权限不足");
						throw new UsernameNotFoundException("用户权限不足");
					}
					return new BackUser(user.getId().toString(), user.getName(), user.getMobile(), user.getTel(),
							user.getEmail(), user.getPosition(), user.getAvatar(),
							AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_ADMI"));
				} else {
					logger.info("未找到关联用户");
					throw new UsernameNotFoundException("未找到关联用户");
				}
			} else {
				logger.info("临时授权码无效或授权失败，请重试！");
				throw new UsernameNotFoundException("临时授权码无效或授权失败，请重试！");
			}
		}
	}

	/**
	 * 钉钉后台应用免登
	 * 
	 * @param code
	 * @return
	 * @throws UsernameNotFoundException
	 */
	public BackUser loadUserBySso(String code) throws UsernameNotFoundException {
		if (StringUtils.isEmpty(code)) {
			logger.info("临时授权码无效");
			throw new UsernameNotFoundException("临时授权码无效");
		} else {
			OapiSsoGetuserinfoResponse response = authService.getUserDetailBySso(code);
			if (response.isSuccess()) {
				String ddUserId = response.getUserInfo().getUserid();
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("ddUserId", ddUserId);
				User user = userService.detail(param);
				if (null != user) {
					if (user.getType() > 2) {
						logger.info("用户权限不足");
						throw new UsernameNotFoundException("用户权限不足");
					}
					return new BackUser(user.getId().toString(), user.getName(), user.getMobile(), user.getTel(),
							user.getEmail(), user.getPosition(), user.getAvatar(),
							AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_ADMI"));
				} else {
					logger.info("未找到关联用户");
					throw new UsernameNotFoundException("未找到关联用户");
				}
			} else {
				logger.info("临时授权码无效或授权失败，请重试！");
				throw new UsernameNotFoundException("临时授权码无效或授权失败，请重试！");
			}
		}
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return null;
	}

}
