package com.seglino.jingyi.notice.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.github.pagehelper.Page;
import com.seglino.jingyi.common.core.dao.BaseDao;
import com.seglino.jingyi.notice.pojo.Notice;

@Mapper
public interface NoticeDao extends BaseDao<Notice> {
	/**
	 * 获取客户端首页前5条公告列表
	 * 
	 * @param userId
	 * @return
	 */
	public List<Notice> listByClientHome(String userId);

	/**
	 * 获取客户端公告列表
	 * @param param
	 * @return
	 */
	public Page<Notice> listByUserClient(Map<String, Object> param);
}
