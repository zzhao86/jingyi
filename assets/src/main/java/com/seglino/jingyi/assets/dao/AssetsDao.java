package com.seglino.jingyi.assets.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.github.pagehelper.Page;
import com.seglino.jingyi.assets.dto.AssetsExportDto;
import com.seglino.jingyi.assets.dto.AssetsListDto;
import com.seglino.jingyi.assets.pojo.Assets;
import com.seglino.jingyi.common.core.dao.BaseDao;

@Mapper
public interface AssetsDao extends BaseDao<Assets> {

	/**
	 * 获取主页列表分页数据
	 * 
	 * @param param
	 * @return
	 */
	public Page<AssetsListDto> pageByIndex(Map<String, Object> param);

	/**
	 * 获取资产最大编号
	 * 
	 * @return
	 */
	public Long maxCode();

	/**
	 * 获取导出数据
	 * 
	 * @param param
	 * @return
	 */
	public List<AssetsExportDto> exportData(Map<String, Object> param);
}
