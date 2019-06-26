package com.seglino.jingyi.webapi.controller.back;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seglino.jingyi.common.response.ApiResult;
import com.seglino.jingyi.common.settings.pojo.Settings;
import com.seglino.jingyi.common.settings.service.SettingsService;

@RestController
@RequestMapping("back/settings")
public class SettingsBackController {

	@Autowired
	private SettingsService settingsService;

	@GetMapping("list")
	public ApiResult list(String type) {
		ApiResult aResult = new ApiResult();
		try {
			Map<String, Object> param = new HashMap<String, Object>();
			if(!StringUtils.isEmpty(type)) {
				param.put("type", type);
			}			
			List<Settings> list = settingsService.list(param);
			aResult.setData(list);
		} catch (Exception e) {
			aResult.AddError(e);
		}
		return aResult;
	}
	
	@PostMapping("save")
	public ApiResult save(@RequestBody List<?> list) {
		ApiResult aResult = new ApiResult();
		
		return aResult;
	}
}
