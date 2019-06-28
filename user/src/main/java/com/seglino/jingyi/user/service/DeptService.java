package com.seglino.jingyi.user.service;

import java.util.List;

import com.seglino.jingyi.common.core.dao.BaseDao;
import com.seglino.jingyi.user.dto.DeptTreeDto;
import com.seglino.jingyi.user.pojo.Dept;

public interface DeptService extends BaseDao<Dept> {

	/**
	 * 根据父部门ID获取部门列表
	 * 
	 * @param pid 父部门ID
	 * @return
	 */
	public List<DeptTreeDto> tree(String pid);
}
