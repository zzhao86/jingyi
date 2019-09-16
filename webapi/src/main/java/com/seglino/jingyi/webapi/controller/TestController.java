package com.seglino.jingyi.webapi.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seglino.jingyi.common.log.annotation.ControllerLog;
import com.seglino.jingyi.common.log.dto.SysLogEntryData;
import com.seglino.jingyi.common.response.ApiResult;
import com.seglino.jingyi.user.service.UserService;

@RestController
@RequestMapping("test")
@ControllerLog("测试日志")
public class TestController {
	@Autowired
	private UserService userService;

	@ControllerLog("测试日志")
	@GetMapping("")
	public ApiResult index(HttpServletRequest request) {
		ApiResult aResult = new ApiResult();
		try {
			SysLogEntryData.getTypeList();
//			aResult.setData(userService.adminList("张志豪"));
		} catch (Exception e) {
			aResult.addError(e);
		}
		return aResult;
	}
}
