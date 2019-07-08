package com.seglino.jingyi.user.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.seglino.jingyi.common.core.service.BaseServiceImpl;
import com.seglino.jingyi.user.dao.UserDao;
import com.seglino.jingyi.user.dto.UserDetailDto;
import com.seglino.jingyi.user.pojo.User;
import com.seglino.jingyi.user.service.UserService;

@Service
public class UserServiceImpl extends BaseServiceImpl<UserDao, User> implements UserService {

	public UserDetailDto detailForDept(Map<String, Object> param) {
		return dao.detailForDept(param);
	}
}
