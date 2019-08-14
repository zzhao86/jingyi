package com.seglino.jingyi.notice.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.seglino.jingyi.common.core.service.BaseServiceImpl;
import com.seglino.jingyi.common.request.RequestPageParams;
import com.seglino.jingyi.notice.dao.NoticeReplyDao;
import com.seglino.jingyi.notice.dto.NoticeReplyDto;
import com.seglino.jingyi.notice.pojo.NoticeReply;
import com.seglino.jingyi.notice.service.NoticeReplyService;

@Service
public class NoticeReplyServiceImpl extends BaseServiceImpl<NoticeReplyDao, NoticeReply> implements NoticeReplyService {

	/**
	 * 获取公告回复分页列表
	 * 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@Override
	public Page<NoticeReplyDto> listForPage(RequestPageParams params) throws Exception {
		PageHelper.startPage(params.getIndex(), params.getSize());
		Map<String, Object> condition = params.getCondition();
		if (condition.containsKey("noticeId")) {
			String noticeId = params.getCondition().get("noticeId").toString();
			return dao.listForPage(noticeId);
		} else {
			throw new Exception("缺少公告ID参数");
		}
	}
}
