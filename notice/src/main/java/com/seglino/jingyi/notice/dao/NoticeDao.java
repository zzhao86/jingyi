package com.seglino.jingyi.notice.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

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
}
