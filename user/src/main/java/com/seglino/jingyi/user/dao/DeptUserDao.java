package com.seglino.jingyi.user.dao;

import org.apache.ibatis.annotations.Mapper;

import com.seglino.jingyi.common.core.dao.BaseDao;
import com.seglino.jingyi.user.pojo.DeptUser;

@Mapper
public interface DeptUserDao extends BaseDao<DeptUser> {

	/**
	 * 删除所有数据
	 * 
	 * @return
	 */
	public int deleteAll();
}
