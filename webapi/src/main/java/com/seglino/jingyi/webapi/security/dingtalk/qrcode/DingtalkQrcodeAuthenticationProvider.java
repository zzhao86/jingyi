package com.seglino.jingyi.webapi.security.dingtalk.qrcode;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

import com.seglino.jingyi.webapi.security.BackUserService;

public class DingtalkQrcodeAuthenticationProvider implements AuthenticationProvider {

	private BackUserService backUserService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		DingtalkQrcodeAuthenticationToken authenticationToken = (DingtalkQrcodeAuthenticationToken) authentication;
		UserDetails userDetails = backUserService.loadUserByUsername((String) authenticationToken.getPrincipal());

		if (userDetails == null)
			throw new InternalAuthenticationServiceException("未找到用户");

		DingtalkQrcodeAuthenticationToken authenticationResult = new DingtalkQrcodeAuthenticationToken(userDetails, userDetails.getAuthorities());

		authenticationResult.setDetails(authenticationToken.getDetails());

		return authenticationResult;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return DingtalkQrcodeAuthenticationToken.class.isAssignableFrom(authentication);
	}

	public BackUserService getBackUserService() {
		return backUserService;
	}

	public void setBackUserService(BackUserService backUserService) {
		this.backUserService = backUserService;
	}
}
