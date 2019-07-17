package com.seglino.jingyi.webapi.controller;

import javax.security.sasl.AuthenticationException;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.seglino.jingyi.common.response.ApiResult;
import com.seglino.jingyi.common.utils.AutoMapper;
import com.seglino.jingyi.user.pojo.User;
import com.seglino.jingyi.webapi.shiro.CustomToken;
import com.seglino.jingyi.webapi.shiro.LoginType;
import com.seglino.jingyi.webapi.vo.back.user.UserDetailVo;

@RestController
@RequestMapping("account")
public class AccountController {

	private final String BackBaseUrl = "http://192.168.0.8:5052/#/";
	private final String BackLoginUrl = BackBaseUrl + "login";

	@GetMapping("login/dingtalk_qrcode")
	public ModelAndView loginByDingtalkQrcode(String code, String state) {
		RedirectView redirect = new RedirectView();
		try {
			Subject subject = SecurityUtils.getSubject();
			CustomToken token = new CustomToken(LoginType.DINGTALK_QRCODE, code, state, code, code);
			subject.login(token);
			if (subject.isAuthenticated()) {
				redirect.setUrl(BackBaseUrl);
			} else {
				redirect.setUrl(BackLoginUrl);
			}
		} catch (Exception e) {
			redirect.setUrl(BackLoginUrl);
		}
		return new ModelAndView(redirect);
	}

	@GetMapping("login/dingtalk_sso")
	public ModelAndView loginByDingtalkSso(String code) {
		RedirectView redirect = new RedirectView();
		try {
			Subject subject = SecurityUtils.getSubject();
			subject.getSession().setTimeout(1);
			CustomToken token = new CustomToken(LoginType.DINGTALK_SSO, code, code, code);
			subject.login(token);
			if (subject.isAuthenticated()) {
				redirect.setUrl(BackBaseUrl);
			} else {
				redirect.setUrl(BackLoginUrl);
			}
		} catch (Exception e) {
			redirect.setUrl(BackLoginUrl);
		}
		return new ModelAndView(redirect);
	}

	@GetMapping("user")
	public ApiResult user() {
		ApiResult aResult = new ApiResult();
		Subject subject = SecurityUtils.getSubject();
		if (subject.isAuthenticated()) {
			User user = (User) subject.getPrincipal();
			aResult.setData(AutoMapper.mapper(user, UserDetailVo.class));
		} else {
			aResult.setCode(401);
		}
		return aResult;
	}

	@GetMapping("login")
	public ApiResult notlogin(HttpServletResponse response) {
		ApiResult aResult = new ApiResult();
		aResult.setCode(401);
		aResult.addError("");
		return aResult;
	}

	@GetMapping("logout")
	public ApiResult logout() {
		ApiResult aResult = new ApiResult();
		try {
			Subject subject = SecurityUtils.getSubject();
			if (subject.isAuthenticated()) {
				subject.logout();
			}
		} catch (Exception e) {
			aResult.addError(e);
		}
		return aResult;
	}
}
