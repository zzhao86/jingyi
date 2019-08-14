package com.seglino.jingyi.notice.dao;

import org.apache.ibatis.annotations.Mapper;

import com.github.pagehelper.Page;
import com.seglino.jingyi.common.core.dao.BaseDao;
import com.seglino.jingyi.notice.dto.NoticeReplyDto;
import com.seglino.jingyi.notice.pojo.NoticeReply;

@Mapper
public interface NoticeReplyDao extends BaseDao<NoticeReply> {
	
	/**
	 * 获取公告回复分页列表
	 * @param params
	 * @return
	 */
	public Page<NoticeReplyDto> listForPage(String noticeId);
}
