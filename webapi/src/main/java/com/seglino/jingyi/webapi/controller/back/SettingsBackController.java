package com.seglino.jingyi.webapi.controller.back;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
	public ApiResult list() {
		ApiResult aResult = new ApiResult();
		try {
			List<Settings> list = settingsService.list(null);
			aResult.setData(list);
		} catch (Exception e) {
			aResult.AddError(e);
		}
		return aResult;
	}
}
