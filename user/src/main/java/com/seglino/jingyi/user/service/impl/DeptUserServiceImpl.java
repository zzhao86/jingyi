package com.seglino.jingyi.user.service.impl;

import org.springframework.stereotype.Service;

import com.seglino.jingyi.common.core.service.BaseServiceImpl;
import com.seglino.jingyi.common.log.annotation.ServiceLog;
import com.seglino.jingyi.user.dao.DeptUserDao;
import com.seglino.jingyi.user.pojo.DeptUser;
import com.seglino.jingyi.user.service.DeptUserService;

@ServiceLog("部门用户服务")
@Service
public class DeptUserServiceImpl extends BaseServiceImpl<DeptUserDao, DeptUser> implements DeptUserService {

	/**
	 * 删除所有数据
	 * 
	 * @return
	 */
	@ServiceLog("删除部门所有用户")
	public int deleteAll() {
		return dao.deleteAll();
	}
}
