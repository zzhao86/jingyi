package com.seglino.jingyi.notice.service;

import com.github.pagehelper.Page;
import com.seglino.jingyi.common.core.service.BaseService;
import com.seglino.jingyi.common.request.RequestPageParams;
import com.seglino.jingyi.notice.dto.NoticeReplyDto;
import com.seglino.jingyi.notice.pojo.NoticeReply;

public interface NoticeReplyService extends BaseService<NoticeReply> {

	/**
	 * 获取公告回复分页列表
	 * 
	 * @param params
	 * @return
	 */
	public Page<NoticeReplyDto> listForPage(RequestPageParams params) throws Exception;
}
