package com.seglino.jingyi.common.settings.dao;

import org.apache.ibatis.annotations.Mapper;

import com.seglino.jingyi.common.core.dao.BaseDao;
import com.seglino.jingyi.common.settings.pojo.Settings;

@Mapper
public interface SettingsDao extends BaseDao<Settings> {

	public Settings detailByCode(String code);
}
