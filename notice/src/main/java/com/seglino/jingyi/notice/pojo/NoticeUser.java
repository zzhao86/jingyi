package com.seglino.jingyi.notice.pojo;

import com.seglino.jingyi.common.core.po.BaseEntity;

public class NoticeUser extends BaseEntity {
	private static final long serialVersionUID = -7050000999754874386L;
	
	private String noticeId;
	private String userId;
	private String ddUserId;
	private String isRead;
	private String readTime;

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

	public String getDdUserId() {
		return ddUserId;
	}

	public void setDdUserId(String ddUserId) {
		this.ddUserId = ddUserId;
	}

	public String getIsRead() {
		return isRead;
	}

	public void setIsRead(String isRead) {
		this.isRead = isRead;
	}

	public String getReadTime() {
		return readTime;
	}

	public void setReadTime(String readTime) {
		this.readTime = readTime;
	}
}
