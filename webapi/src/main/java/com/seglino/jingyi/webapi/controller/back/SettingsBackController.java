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

import com.seglino.jingyi.common.log.annotation.ControllerLog;
import com.seglino.jingyi.common.response.ApiResult;
import com.seglino.jingyi.common.settings.pojo.Settings;
import com.seglino.jingyi.common.settings.service.SettingsService;
import com.seglino.jingyi.common.utils.AutoMapper;
import com.seglino.jingyi.webapi.vo.back.settings.SettingsVo;

@ControllerLog("设置管理")
@RestController
@RequestMapping("back/settings")
public class SettingsBackController {

	@Autowired
	private SettingsService settingsService;

	@ControllerLog("查看系统设置")
	@GetMapping("list")
	public ApiResult list(String type) {
		ApiResult aResult = new ApiResult();
		try {
			Map<String, Object> param = new HashMap<String, Object>();
			if (!StringUtils.isEmpty(type)) {
				param.put("type", type);
			}
			List<Settings> list = settingsService.list(param);
			aResult.setData(AutoMapper.mapperList(list, SettingsVo.class));
		} catch (Exception e) {
			aResult.addError(e);
		}
		return aResult;
	}

	@ControllerLog("修改系统设置")
	@PostMapping("save")
	public ApiResult save(@RequestBody SettingsVo vo) {
		ApiResult aResult = new ApiResult();
		try {
			Settings settings = AutoMapper.mapper(vo, Settings.class);
			int count = settingsService.update(settings);
			if (count <= 0) {
				aResult.addError("更新失败");
			}
		} catch (Exception e) {
			aResult.addError("更新失败");
		}
		return aResult;
	}
}
