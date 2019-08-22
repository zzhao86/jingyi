package com.seglino.jingyi.assets.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.github.pagehelper.Page;
import com.seglino.jingyi.assets.dto.AssetsPositionListDto;
import com.seglino.jingyi.assets.dto.AssetsPositionTreeDto;
import com.seglino.jingyi.assets.pojo.AssetsPosition;
import com.seglino.jingyi.common.core.dao.BaseDao;

@Mapper
public interface AssetsPositionDao extends BaseDao<AssetsPosition> {

	/**
	 * 获取列表页分页数据
	 * 
	 * @param params
	 * @return
	 */
	public Page<AssetsPositionListDto> pageByIndex(Map<String, Object> param);

	/**
	 * 获取位置Tree数据
	 * 
	 * @param parentId
	 * @return
	 */
	public List<AssetsPositionTreeDto> tree(@Param("parentId") String parentId);
}
