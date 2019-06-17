package com.seglino.jingyi.webapi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.seglino.jingyi.common.response.ApiResult;

@Controller
@RequestMapping("/")
public class HomeController {
	@ResponseBody
	@RequestMapping("/")
	public ApiResult index() {
		ApiResult aResult = new ApiResult();
		aResult.setData("this is webapi home page");
		return aResult;
	}
}
