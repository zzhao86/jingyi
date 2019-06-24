package com.seglino.jingyi.user.dao;

import org.apache.ibatis.annotations.Mapper;

import com.seglino.jingyi.common.core.dao.BaseDao;
import com.seglino.jingyi.user.pojo.User;

@Mapper
public interface UserDao extends BaseDao<User> {

}
