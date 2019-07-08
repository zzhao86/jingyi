package com.seglino.jingyi.user.service;

import java.util.Map;

import com.seglino.jingyi.common.core.service.BaseService;
import com.seglino.jingyi.user.dto.UserDetailDto;
import com.seglino.jingyi.user.pojo.User;

public interface UserService extends BaseService<User> {

	public UserDetailDto detailForDept(Map<String, Object> param);
}
