package com.seglino.jingyi.webapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seglino.jingyi.common.response.ApiResult;
import com.seglino.jingyi.dingtalk.utils.DingtalkBean;

@RestController
@RequestMapping("/")
public class HomeController {

	@GetMapping("/")
	public ApiResult index() {
		ApiResult aResult = new ApiResult();
		aResult.setData("this is webapi home page");
		return aResult;
	}

	@GetMapping("/dt/token")
	public ApiResult dingtalkToken() {
		ApiResult aResult = new ApiResult();
		aResult.setData(DingtalkBean.AccessToken);
		return aResult;
	}
}
