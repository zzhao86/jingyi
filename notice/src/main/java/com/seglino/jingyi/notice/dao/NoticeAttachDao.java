package com.seglino.jingyi.notice.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.seglino.jingyi.common.core.dao.BaseDao;
import com.seglino.jingyi.notice.dto.NoticeAttachDto;
import com.seglino.jingyi.notice.pojo.NoticeAttach;

@Mapper
public interface NoticeAttachDao extends BaseDao<NoticeAttach> {

	/**
	 * 获取附件详情列表
	 * @param noticeId
	 * @return
	 */
	public List<NoticeAttachDto> listForFileDetail(String noticeId);
}
