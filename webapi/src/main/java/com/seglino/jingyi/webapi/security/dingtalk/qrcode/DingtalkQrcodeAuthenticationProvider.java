package com.seglino.jingyi.webapi.security.dingtalk.qrcode;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import com.seglino.jingyi.webapi.security.BackUser;

public class DingtalkQrcodeAuthenticationProvider implements AuthenticationProvider {

	private DingtalkQrcodeUserService backUserService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		DingtalkQrcodeAuthenticationToken authenticationToken = (DingtalkQrcodeAuthenticationToken) authentication;
		BackUser userDetails = backUserService.loadUserbyCode((String) authenticationToken.getPrincipal());

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

	public DingtalkQrcodeUserService getBackUserService() {
		return backUserService;
	}

	public void setBackUserService(DingtalkQrcodeUserService backUserService) {
		this.backUserService = backUserService;
	}
}
