package com.seglino.jingyi.user.service.impl;

import java.util.HashMap;
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
	 * 获取部门Tree数据
	 * 
	 * @return
	 */
	@Override
	public List<DeptTreeDto> tree() {
		return this.tree("0");
	}

	private List<DeptTreeDto> tree(String pid) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("parentId", pid);
		List<DeptTreeDto> list = dao.tree(param);
		for (DeptTreeDto dept : list) {
			if (dept.getChildCount() > 0) {
				dept.setChildren(tree(dept.getId()));;
			}
		}
		return list;
	}
}
