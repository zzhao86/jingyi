package com.seglino.jingyi.webapi.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seglino.jingyi.common.response.ApiResult;
import com.seglino.jingyi.common.utils.IpUtils;

@RestController
@RequestMapping("test")
public class TestController {

	@GetMapping("")
	public ApiResult index(HttpServletRequest request) {
		ApiResult aResult = new ApiResult();
		aResult.setData(IpUtils.getClientIpAddress(request));
		return aResult;
	}
}
