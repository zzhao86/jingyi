package com.seglino.jingyi.common.settings.service;

import java.util.List;

import com.seglino.jingyi.common.core.service.BaseService;
import com.seglino.jingyi.common.settings.pojo.Settings;

public interface SettingsService extends BaseService<Settings> {

	/**
	 * 根据Code获取设置值
	 * 
	 * @param code
	 * @return
	 */
	public String getValue(String code);

	/**
	 * 批量更新
	 * 
	 * @param list
	 * @return
	 */
//	public int update(List<Settings> list);
}
