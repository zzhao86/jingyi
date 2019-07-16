package com.seglino.jingyi.webapi.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.seglino.jingyi.common.response.ApiResult;
import com.seglino.jingyi.webapi.shiro.CustomToken;
import com.seglino.jingyi.webapi.shiro.LoginType;

@RestController
@RequestMapping("account")
public class AccountController {
	
private final String BackBaseUrl = "http://192.168.0.8:5052/#/";

	@GetMapping("login/dingtalk_qrcode")
	public ModelAndView loginByDingtalkQrcode(String code, String state) {
		RedirectView redirect = new RedirectView();
		try {
			Subject subject = SecurityUtils.getSubject();
			CustomToken token = new CustomToken(LoginType.DINGTALK_QRCODE, code, code, code, state);
			subject.login(token);
			if (subject.isAuthenticated()) {
				redirect.setUrl(BackBaseUrl);
			} else {
				redirect.setUrl(BackBaseUrl + "login");
			}
		} catch (Exception e) {
			redirect.setUrl(BackBaseUrl + "login");
		}
		return new ModelAndView(redirect);
	}

	@GetMapping("notlogin")
	public ApiResult notlogin() {
		ApiResult aResult = new ApiResult();
		aResult.setData("请先登录");
		return aResult;
	}

	@GetMapping("logout")
	public ApiResult logout() {
		ApiResult aResult = new ApiResult();
		try {
			SecurityUtils.getSubject().logout();
		} catch (Exception e) {
			aResult.AddError(e);
		}
		return aResult;
	}
}
