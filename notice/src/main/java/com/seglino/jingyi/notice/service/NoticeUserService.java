package com.seglino.jingyi.notice.service;

import com.github.pagehelper.Page;
import com.seglino.jingyi.common.core.service.BaseService;
import com.seglino.jingyi.common.request.RequestListParams;
import com.seglino.jingyi.notice.dto.NoticeUserDto;
import com.seglino.jingyi.notice.pojo.NoticeUser;

public interface NoticeUserService extends BaseService<NoticeUser> {

	/**
	 * 获取公告接收用户列表
	 * 
	 * @param params
	 * @return
	 */
	public Page<NoticeUserDto> listForUser(RequestListParams params);
	
	/**
	 * 删除公告中所有的接收人
	 * 
	 * @param noticeId 公告ID
	 * @return
	 */
	public int deleteByNoticeId(String noticeId);
}
