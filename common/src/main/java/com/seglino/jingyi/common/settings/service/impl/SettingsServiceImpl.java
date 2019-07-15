package com.seglino.jingyi.common.settings.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.seglino.jingyi.common.core.service.BaseServiceImpl;
import com.seglino.jingyi.common.settings.dao.SettingsDao;
import com.seglino.jingyi.common.settings.pojo.Settings;
import com.seglino.jingyi.common.settings.service.SettingsService;

@Service
public class SettingsServiceImpl extends BaseServiceImpl<SettingsDao, Settings> implements SettingsService {
	private Logger logger = LoggerFactory.getLogger(getClass());

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

	/**
	 * 批量更新
	 */
//	@Override
//	public int update(List<Settings> list) {
//		int count = 0;
//		try {
//			if (list == null || list.size() == 0)
//				return count;
//			for (Settings settings : list) {
//				count += dao.update(settings);
//			}
//		} catch (Exception e) {
//			count = 0;
//			logger.error("{}", e);
//		}
//		return count;
//	}

}
