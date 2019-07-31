package com.seglino.jingyi.webapi.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.seglino.jingyi.common.response.ApiResult;

@RestController
@RequestMapping("/")
public class HomeController {

	@GetMapping("/")
	public ApiResult index(HttpServletRequest request) {
		ApiResult aResult = new ApiResult();
		aResult.setData("this is webapi home page");
		return aResult;
	}
	
	@GetMapping("back")
	public ModelAndView back(String code) {
		return new ModelAndView(new RedirectView("account/login/dingtalk_sso?code=" + code));		
	}
}
