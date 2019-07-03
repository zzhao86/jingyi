package com.seglino.jingyi.user.service;

import com.seglino.jingyi.common.core.service.BaseService;
import com.seglino.jingyi.user.pojo.DeptUser;

public interface DeptUserService extends BaseService<DeptUser> {

	/**
	 * 删除所有数据
	 * 
	 * @return
	 */
	public int deleteAll();
}
