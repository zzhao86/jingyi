package com.seglino.jingyi.user.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.seglino.jingyi.common.core.dao.BaseDao;
import com.seglino.jingyi.user.dto.UserDetailDto;
import com.seglino.jingyi.user.pojo.User;

@Mapper
public interface UserDao extends BaseDao<User> {

	public UserDetailDto detailForDept(Map<String, Object> param);
}
