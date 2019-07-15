package com.seglino.jingyi.webapi.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dingtalk.api.response.OapiSnsGetuserinfoBycodeResponse;
import com.seglino.jingyi.common.response.ApiResult;
import com.seglino.jingyi.dingtalk.service.AuthService;
import com.seglino.jingyi.webapi.shiro.DingtalkAuthenticationToken;

@RestController
@RequestMapping("account")
public class AccountController {
	
	@Autowired
	private AuthService authService;
	
	
	@GetMapping("notlogin")
	public ApiResult notlogin() {
		ApiResult aResult = new ApiResult();
		aResult.setData("请先登录");
		return aResult;
	}

	@GetMapping("login")
	public ApiResult login(String type, String code) {
		ApiResult aResult = new ApiResult();
		try {
			Subject subject = SecurityUtils.getSubject();
//			if("qrcode".equals(type)) {
//				OapiSnsGetuserinfoBycodeResponse response = authService.getUserDetailByQrCode(code);
//				if(response.isSuccess()) {
//					String unionid = response.getUserInfo().getUnionid();
//					
//				}
//			}
//			UsernamePasswordToken token = new UsernamePasswordToken(code, type);
			DingtalkAuthenticationToken token = new DingtalkAuthenticationToken(type, code);
	        subject.login(token);
	        
		} catch (Exception e) {
			aResult.AddError(e);
		}
		return aResult;
	}
}
