package com.seglino.jingyi.user.service;

import java.util.List;

import com.seglino.jingyi.common.core.service.BaseService;
import com.seglino.jingyi.user.dto.DeptTreeDto;
import com.seglino.jingyi.user.pojo.Dept;

public interface DeptService extends BaseService<Dept> {

	/**
	 * 获取部门Tree数据
	 * 
	 * @return
	 */
	public List<DeptTreeDto> tree();
}
