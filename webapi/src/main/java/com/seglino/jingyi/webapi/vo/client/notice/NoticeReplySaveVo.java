package com.seglino.jingyi.webapi.vo.client.notice;

import com.seglino.jingyi.common.core.vo.BaseVo;

public class NoticeReplySaveVo extends BaseVo {
	private String noticeId;
	private String userId;
	private String content;

	public String getNoticeId() {
		return noticeId;
	}

	public void setNoticeId(String noticeId) {
		this.noticeId = noticeId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
