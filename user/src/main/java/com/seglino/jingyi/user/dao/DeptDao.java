package com.seglino.jingyi.user.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.seglino.jingyi.common.core.dao.BaseDao;
import com.seglino.jingyi.user.dto.DeptTreeDto;
import com.seglino.jingyi.user.pojo.Dept;

@Mapper
public interface DeptDao extends BaseDao<Dept> {

	/**
	 * 根据父部门ID获取部门列表
	 * 
	 * @param pid 父部门ID
	 * @return
	 */
	public List<DeptTreeDto> tree(Map<String, Object> param);
}
