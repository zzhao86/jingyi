package com.seglino.jingyi.webapi.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seglino.jingyi.common.response.ApiResult;
import com.seglino.jingyi.common.utils.ExcelUtils;

@RestController
@RequestMapping("test")
public class TestController {
	
	@PostMapping("import")
	public ApiResult importExcel(HttpServletRequest request) {
		ApiResult aResult = new ApiResult();
		try {
			ExcelUtils.
		} catch (Exception e) {
			aResult.addError(e);
		}
		return aResult;
	}

}
