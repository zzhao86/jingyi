package com.seglino.jingyi.assets.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.github.pagehelper.Page;
import com.seglino.jingyi.assets.dto.AssetsCategoryListDto;
import com.seglino.jingyi.assets.pojo.AssetsCategory;
import com.seglino.jingyi.common.core.dao.BaseDao;

@Mapper
public interface AssetsCategoryDao extends BaseDao<AssetsCategory> {

	/**
	 * 获取列表页分页数据
	 * 
	 * @param params
	 * @return
	 */
	public Page<AssetsCategoryListDto> pageByIndex(Map<String, Object> param);
}
