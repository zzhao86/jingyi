package com.seglino.jingyi.webapi.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seglino.jingyi.common.log.annotation.OperationLog;
import com.seglino.jingyi.common.response.ApiResult;
import com.seglino.jingyi.user.service.UserService;

@RestController
@RequestMapping("test")
public class TestController {
	@Autowired
	private UserService userService;

	@OperationLog("测试日志")
	@GetMapping("")
	public ApiResult index(HttpServletRequest request) {
		ApiResult aResult = new ApiResult();
		aResult.setData(userService.adminList(""));
		return aResult;
	}
}
