package com.seglino.jingyi.common.log.service;

import java.util.List;

import com.github.pagehelper.Page;
import com.seglino.jingyi.common.core.service.BaseService;
import com.seglino.jingyi.common.log.dto.SysLogDetailDto;
import com.seglino.jingyi.common.log.dto.SysLogListDto;
import com.seglino.jingyi.common.log.pojo.SysLog;
import com.seglino.jingyi.common.request.RequestPageParams;

public interface SysLogService extends BaseService<SysLog> {

	/**
	 * 后台列表页数据
	 * 
	 * @param param
	 * @return
	 */
	public Page<SysLogListDto> pageByIndex(RequestPageParams params);
	
	/**
	 * 获取日志详情
	 * @param id
	 * @return
	 */
	public SysLogDetailDto detail(String id);
	
	/**
	 * 日志模块列表
	 * @return
	 */
	public List<String> moduleList();
}
