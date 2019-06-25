package com.seglino.jingyi.webapi.controller.back;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("back/dingtalk/login")
public class DingtalkLoginBackController {
	
	/**
	 * 钉钉
	 * @param code
	 */
	@GetMapping("sso")
	public void ssoLogin(String code) {
		
	}
}
