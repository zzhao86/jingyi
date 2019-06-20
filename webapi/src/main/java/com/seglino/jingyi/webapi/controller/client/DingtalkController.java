package com.seglino.jingyi.webapi.controller.client;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dingtalk.api.response.OapiUserGetuserinfoResponse;
import com.seglino.jingyi.common.response.ApiResult;
import com.seglino.jingyi.dingtalk.service.AuthService;
import com.seglino.jingyi.dingtalk.utils.DingtalkAuthUtils;

@RestController
@RequestMapping("client/dingtalk")
public class DingtalkController {

	@Autowired
	private AuthService authService;

	/**
	 * jsapi鉴权参数
	 * 
	 * @param url
	 * @return
	 */
	@GetMapping("jsapi_config")
	public ApiResult jsapiTicket(String url) {
		ApiResult aResult = new ApiResult();
		aResult.setData(DingtalkAuthUtils.getConfig(url));
		return aResult;
	}

	/**
	 * 用免登码获取用户信息
	 * 
	 * @param code
	 * @return
	 */
	@GetMapping("userinfo_code")
	public ApiResult getUserinfoByCode(String code) {
		ApiResult aResult = new ApiResult();

		OapiUserGetuserinfoResponse response = authService.getUserInfo(code);
		if (response.isSuccess()) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("userid", response.getUserid());
			map.put("isSys", response.getIsSys());
			aResult.setData(map);
		}
		return aResult;
	}
}
