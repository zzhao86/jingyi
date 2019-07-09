package com.seglino.jingyi.webapi.controller.back;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.dingtalk.api.response.OapiSnsGetuserinfoBycodeResponse;
import com.dingtalk.api.response.OapiSsoGetuserinfoResponse;
import com.seglino.jingyi.common.response.ApiResult;
import com.seglino.jingyi.common.settings.pojo.Settings;
import com.seglino.jingyi.common.settings.service.SettingsService;
import com.seglino.jingyi.dingtalk.service.AuthService;
import com.seglino.jingyi.user.service.UserService;

@RestController
@RequestMapping("back/dingtalk/login")
public class DingtalkLoginBackController {
	@Autowired
	private AuthService authService;

	@Autowired
	private SettingsService settingsService;
	
	@Autowired
	private UserService userService;

	private String backBaseUrl = "http://192.168.0.238:5052/#/";

	/**
	 * 钉钉
	 * 
	 * @param code
	 */
	@GetMapping("sso")
	public ModelAndView ssoLogin(String code, ServletResponse response) {
		if (isAlreadySetting()) {
			return new ModelAndView("user_sso");
		} else {
			return new ModelAndView(new RedirectView(backBaseUrl + "dingtalk/settings"));
		}
	}

	@GetMapping("user_sso")
	public ApiResult getUserDetailBySso(String code) {
		ApiResult aResult = new ApiResult();
		try {
			OapiSsoGetuserinfoResponse response = authService.getUserDetailBySso(code);
			if (response.isSuccess()) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("corpInfo", response.getCorpInfo());
				map.put("isSys", response.getIsSys());
				map.put("userInfo", response.getUserInfo());
				aResult.setData(map);
			}
		} catch (Exception e) {
			aResult.AddError(e);
		}
		return aResult;
	}

	@GetMapping("qrcode")
	public ApiResult qrcodeLogin(String code) {
		ApiResult aResult = new ApiResult();
		try {
			OapiSnsGetuserinfoBycodeResponse response = authService.getUserDetailByQrCode(code);
			String unionid = response.getUserInfo().getUnionid();
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("unionid", unionid);
			aResult.setData(userService.detail(param));
		} catch (Exception e) {
			aResult.AddError(e);
		}
		return aResult;
	}

	/**
	 * 判断是否已设置钉钉的必要参数
	 * 
	 * @return
	 */
	private boolean isAlreadySetting() {
		boolean isSetting = true;
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("type", "DINGTALK");
		List<Settings> list = settingsService.list(param);
		for (Settings settings : list) {
			// 是否设置CorpId
			if ("DINGTALK_CORPID".equals(settings.getCode()) && settings.getValue().isEmpty()) {
				isSetting = false;
				break;
			}
			// 是否设置Appkey
			if ("DINGTALK_APPKEY".equals(settings.getCode()) && settings.getValue().isEmpty()) {
				isSetting = false;
				break;
			}
			// 是否设置Appsecret
			if ("DINGTALK_APPSECRET".equals(settings.getCode()) && settings.getValue().isEmpty()) {
				isSetting = false;
				break;
			}
			// 是否设置Ssosecret
			if ("DINGTALK_SSOSECRET".equals(settings.getCode()) && settings.getValue().isEmpty()) {
				isSetting = false;
				break;
			}
			// 是否设置AgentId
			if ("DINGTALK_AGENTID".equals(settings.getCode()) && settings.getValue().isEmpty()) {
				isSetting = false;
				break;
			}
		}
		return isSetting;
	}
}
