package com.seglino.jingyi.user.service.impl;

import org.springframework.stereotype.Service;

import com.seglino.jingyi.common.core.service.BaseServiceImpl;
import com.seglino.jingyi.user.dao.UserDao;
import com.seglino.jingyi.user.pojo.User;
import com.seglino.jingyi.user.service.UserService;

@Service
public class UserServiceImpl extends BaseServiceImpl<UserDao, User> implements UserService {

}
