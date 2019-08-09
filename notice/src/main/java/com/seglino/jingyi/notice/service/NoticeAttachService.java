package com.seglino.jingyi.notice.service;

import java.util.List;

import com.seglino.jingyi.common.core.service.BaseService;
import com.seglino.jingyi.notice.dto.NoticeAttachDto;
import com.seglino.jingyi.notice.pojo.NoticeAttach;

public interface NoticeAttachService extends BaseService<NoticeAttach> {

	/**
	 * 获取附件详情列表
	 * 
	 * @param noticeId
	 * @return
	 */
	public List<NoticeAttachDto> listForFileDetail(String noticeId);
}
