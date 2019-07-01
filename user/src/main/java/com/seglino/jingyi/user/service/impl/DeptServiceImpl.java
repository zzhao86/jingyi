package com.seglino.jingyi.user.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.seglino.jingyi.common.core.service.BaseServiceImpl;
import com.seglino.jingyi.user.dao.DeptDao;
import com.seglino.jingyi.user.dto.DeptTreeDto;
import com.seglino.jingyi.user.pojo.Dept;
import com.seglino.jingyi.user.service.DeptService;

@Service
public class DeptServiceImpl extends BaseServiceImpl<DeptDao, Dept> implements DeptService {

	/**
	 * 根据父部门ID获取部门列表
	 * 
	 * @param pid 父部门ID
	 * @return
	 */
	public List<DeptTreeDto> tree(Map<String, Object> param){
		return dao.tree(param);
	}
}
