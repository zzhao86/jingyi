package com.seglino.jingyi.notice.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.seglino.jingyi.common.core.service.BaseServiceImpl;
import com.seglino.jingyi.common.log.annotation.ServiceLog;
import com.seglino.jingyi.notice.dao.NoticeAttachDao;
import com.seglino.jingyi.notice.dto.NoticeAttachDto;
import com.seglino.jingyi.notice.pojo.NoticeAttach;
import com.seglino.jingyi.notice.service.NoticeAttachService;

@ServiceLog("公告附件服务")
@Service
public class NoticeAttachServiceImpl extends BaseServiceImpl<NoticeAttachDao, NoticeAttach> implements NoticeAttachService {

	/**
	 * 获取附件详情列表
	 * 
	 * @param noticeId
	 * @return
	 */
	@ServiceLog("公告附件列表")
	public List<NoticeAttachDto> listForFileDetail(String noticeId) {
		return dao.listForFileDetail(noticeId);
	}
}
