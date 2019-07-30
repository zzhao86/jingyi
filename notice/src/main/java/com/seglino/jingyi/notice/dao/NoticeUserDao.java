package com.seglino.jingyi.notice.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.github.pagehelper.Page;
import com.seglino.jingyi.common.core.dao.BaseDao;
import com.seglino.jingyi.notice.dto.NoticeUserDto;
import com.seglino.jingyi.notice.pojo.NoticeUser;

@Mapper
public interface NoticeUserDao extends BaseDao<NoticeUser> {

	/**
	 * 获取公告接收用户列表
	 * 
	 * @param param
	 * @return
	 */
	public Page<NoticeUserDto> listForUser(Map<String, Object> param);
	
	/**
	 * 删除公告中所有的接收人
	 * 
	 * @param noticeId 公告ID
	 * @return
	 */
	public int deleteByNoticeId(String noticeId);
}
