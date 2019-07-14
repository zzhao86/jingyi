package com.seglino.jingyi.webapi.security.dingtalk;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import com.seglino.jingyi.webapi.security.BackUser;

public class DingtalkAuthenticationProvider implements AuthenticationProvider {

	private DingtalkUserService backUserService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		DingtalkAuthenticationToken authenticationToken = (DingtalkAuthenticationToken) authentication;
		BackUser userDetails = null;
		String type = authenticationToken.getType();
		if ("qrcode".equalsIgnoreCase(type)) {
			userDetails = backUserService.loadUserByQrcode(authenticationToken.getCode());
		} else if ("sso".equalsIgnoreCase(type)) {
			userDetails = backUserService.loadUserBySso(authenticationToken.getCode());
		}

		if (userDetails == null)
			throw new InternalAuthenticationServiceException("未找到用户");

		DingtalkAuthenticationToken authenticationResult = new DingtalkAuthenticationToken(userDetails,
				userDetails.getAuthorities());

		authenticationResult.setDetails(authenticationToken.getDetails());

		return authenticationResult;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return DingtalkAuthenticationToken.class.isAssignableFrom(authentication);
	}

	public DingtalkUserService getBackUserService() {
		return backUserService;
	}

	public void setBackUserService(DingtalkUserService backUserService) {
		this.backUserService = backUserService;
	}
}
