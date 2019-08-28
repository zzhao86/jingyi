package com.seglino.jingyi.user.service;

import java.util.List;
import java.util.Map;

import com.seglino.jingyi.common.core.service.BaseService;
import com.seglino.jingyi.user.dto.UserDetailDto;
import com.seglino.jingyi.user.dto.UserListDto;
import com.seglino.jingyi.user.pojo.User;

public interface UserService extends BaseService<User> {

	/**
	 * 获取用户详情，带所有的部门字段
	 * @param param
	 * @return
	 */
	public UserDetailDto detailForDept(Map<String, Object> param);
	
	/**
	 * 获取部门下的所有用户列表
	 * @param deptId 部门ID
	 * @param fetchChild 是否递归获取所有子部门下的用户
	 * @return
	 */
	public List<User> listByDept(String deptId, boolean fetchChild);

	/**
	 * 获取管理员列表
	 * @param keywords
	 * @return
	 */
	public List<UserListDto> adminList(String keywords);
}
