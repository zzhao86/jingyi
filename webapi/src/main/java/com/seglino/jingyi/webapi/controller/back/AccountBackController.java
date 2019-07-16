package com.seglino.jingyi.webapi.controller.back;

import javax.security.sasl.AuthenticationException;
import javax.servlet.http.HttpServletRequest;

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
import com.seglino.jingyi.webapi.vo.back.user.UserDetailVo;

@RestController
@RequestMapping("back")
public class AccountBackController {

	private String backBaseUrl = "http://192.168.0.8:5052/#/";

	@GetMapping("home")
	public ModelAndView home(HttpServletRequest request) {
		return new ModelAndView(new RedirectView(backBaseUrl));
	}

	@GetMapping("login")
	public ApiResult login(HttpServletRequest request) {
		ApiResult aResult = new ApiResult();
		aResult.setCode(401);
		aResult.AddError("请登录");
		return aResult;
	}

	@GetMapping("user")
	public ApiResult user() {
		ApiResult aResult = new ApiResult();
		Subject subject = SecurityUtils.getSubject();
		if(subject.isAuthenticated()) {
			User user = (User)subject.getPrincipal();
			aResult.setData(AutoMapper.mapper(user, UserDetailVo.class));
		} else {
			aResult.AddError(new AuthenticationException("请先登录"));
		}
		return aResult;
	}
}
