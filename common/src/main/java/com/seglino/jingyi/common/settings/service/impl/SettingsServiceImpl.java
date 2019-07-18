package com.seglino.jingyi.common.settings.service.impl;

import org.springframework.stereotype.Service;

import com.seglino.jingyi.common.core.service.BaseServiceImpl;
import com.seglino.jingyi.common.settings.dao.SettingsDao;
import com.seglino.jingyi.common.settings.pojo.Settings;
import com.seglino.jingyi.common.settings.service.SettingsService;

@Service
public class SettingsServiceImpl extends BaseServiceImpl<SettingsDao, Settings> implements SettingsService {

	/**
	 * 根据Code获取设置详值
	 * 
	 * @param code
	 * @return
	 */
	@Override
	public String getValue(String code) {
		Settings settings = dao.detailByCode(code);
		if (settings != null)
			return settings.getValue();
		else {
			return null;
		}
	}
}
