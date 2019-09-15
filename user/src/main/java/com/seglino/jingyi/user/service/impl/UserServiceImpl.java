package com.seglino.jingyi.user.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.seglino.jingyi.common.core.service.BaseServiceImpl;
import com.seglino.jingyi.common.log.annotation.ServiceLog;
import com.seglino.jingyi.user.dao.UserDao;
import com.seglino.jingyi.user.dto.UserDetailDto;
import com.seglino.jingyi.user.dto.UserListDto;
import com.seglino.jingyi.user.pojo.Dept;
import com.seglino.jingyi.user.pojo.User;
import com.seglino.jingyi.user.service.DeptService;
import com.seglino.jingyi.user.service.UserService;

/**
 * 用户服务
 * 
 * @author ZZH
 *
 */
@Service
@ServiceLog(module = "用户服务")
public class UserServiceImpl extends BaseServiceImpl<UserDao, User> implements UserService {
	@Autowired
	private DeptService deptService;

	/**
	 * 获取用户详情，带所有的部门字段
	 * 
	 * @param param
	 * @return
	 */
	@Override
	public UserDetailDto detailForDept(Map<String, Object> param) {
		return dao.detailForDept(param);
	}

	/**
	 * 获取部门下的所有用户列表
	 * 
	 * @param deptId     部门ID
	 * @param fetchChild 是否递归获取所有子部门下的用户
	 * @return
	 */
	@Override
	public List<User> listByDept(String deptId, boolean fetchChild) {
		List<User> result = new ArrayList<User>();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("deptId", deptId);
		param.put("isDeleted", false);
		result.addAll(this.list(param));
		if (fetchChild) {
			// 获取子部门
			param.clear();
			param.put("parentId", deptId);
			List<Dept> list = deptService.list(param);
			if (null != list && list.size() > 0) {
				for (Dept dept : list) {
					result.addAll(listByDept(dept.getId().toString(), fetchChild));
				}
			}
		}
		return result;
	}

	/**
	 * 获取管理员列表
	 * 
	 * @param keywords
	 * @return
	 */
	@ServiceLog(method = "查询管理员列表")
	public List<UserListDto> adminList(String keywords) {

		throw new IllegalArgumentException("错误测试");

		// return dao.adminList(keywords);
	}
}
