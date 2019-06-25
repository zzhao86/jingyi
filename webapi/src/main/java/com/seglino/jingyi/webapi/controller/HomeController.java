package com.seglino.jingyi.webapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seglino.jingyi.common.response.ApiResult;
import com.seglino.jingyi.dingtalk.service.DingtalkUserService;

@RestController
@RequestMapping("/")
public class HomeController {
	@Autowired
	private DingtalkUserService dingtalkUserService;
	
	@GetMapping("/")
	public ApiResult index() {
		ApiResult aResult = new ApiResult();
		aResult.setData("this is webapi home page");
		return aResult;
	}
	
	@GetMapping("init_admin")
	public ApiResult initAdmin() {
		ApiResult aResult = new ApiResult();
		int count = dingtalkUserService.initAdmin();
		if(count <= 0) {
			aResult.AddError("初始化失败");
		}
		return aResult;
	}
}
