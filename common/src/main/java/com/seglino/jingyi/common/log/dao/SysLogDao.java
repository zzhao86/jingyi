package com.seglino.jingyi.common.log.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.github.pagehelper.Page;
import com.seglino.jingyi.common.core.dao.BaseDao;
import com.seglino.jingyi.common.log.dto.SysLogDetailDto;
import com.seglino.jingyi.common.log.dto.SysLogListDto;
import com.seglino.jingyi.common.log.pojo.SysLog;

@Mapper
public interface SysLogDao extends BaseDao<SysLog> {

	/**
	 * 后台列表页数据
	 * 
	 * @param param
	 * @return
	 */
	public Page<SysLogListDto> pageByIndex(Map<String, Object> param);

	/**
	 * 获取日志详情
	 * 
	 * @param id
	 * @return
	 */
	public SysLogDetailDto detailForDto(String id);
	
	/**
	 * 日志模块列表
	 * @return
	 */
	public List<String> moduleList();
}
