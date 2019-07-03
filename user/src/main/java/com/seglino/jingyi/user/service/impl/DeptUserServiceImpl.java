package com.seglino.jingyi.user.service.impl;

import org.springframework.stereotype.Service;

import com.seglino.jingyi.common.core.service.BaseServiceImpl;
import com.seglino.jingyi.user.dao.DeptUserDao;
import com.seglino.jingyi.user.pojo.DeptUser;
import com.seglino.jingyi.user.service.DeptUserService;

@Service
public class DeptUserServiceImpl extends BaseServiceImpl<DeptUserDao, DeptUser> implements DeptUserService {

	/**
	 * 删除所有数据
	 * 
	 * @return
	 */
	public int deleteAll() {
		return dao.deleteAll();
	}
}
