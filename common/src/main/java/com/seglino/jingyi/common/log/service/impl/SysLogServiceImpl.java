package com.seglino.jingyi.common.log.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.seglino.jingyi.common.core.service.BaseServiceImpl;
import com.seglino.jingyi.common.log.dao.SysLogDao;
import com.seglino.jingyi.common.log.dto.SysLogDetailDto;
import com.seglino.jingyi.common.log.dto.SysLogListDto;
import com.seglino.jingyi.common.log.pojo.SysLog;
import com.seglino.jingyi.common.log.service.SysLogService;
import com.seglino.jingyi.common.request.RequestPageParams;

@Service
public class SysLogServiceImpl extends BaseServiceImpl<SysLogDao, SysLog> implements SysLogService {

	/**
	 * 后台列表页数据
	 * 
	 * @param param
	 * @return
	 */
	@Override
	public Page<SysLogListDto> pageByIndex(RequestPageParams params) {
		PageHelper.startPage(params.getIndex(), params.getSize());
		return dao.pageByIndex(params.getCondition());
	}

	/**
	 * 获取日志详情
	 * 
	 * @param id
	 * @return
	 */
	@Override
	public SysLogDetailDto detail(String id) {
		return dao.detailForDto(id);
	}
	
	/**
	 * 日志模块列表
	 * @return
	 */
	public List<String> moduleList(){
		return dao.moduleList();
	}
}
