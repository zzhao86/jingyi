package com.seglino.jingyi.notice.pojo;

import com.seglino.jingyi.common.core.po.BaseEntity;

public class NoticeAttach extends BaseEntity {
	private static final long serialVersionUID = 3399528394849630464L;

	private String noticeId;
	private String attachId;
	private String attachName;
	private String attachUrl;

	public String getNoticeId() {
		return noticeId;
	}

	public void setNoticeId(String noticeId) {
		this.noticeId = noticeId;
	}

	public String getAttachId() {
		return attachId;
	}

	public void setAttachId(String attachId) {
		this.attachId = attachId;
	}

	public String getAttachName() {
		return attachName;
	}

	public void setAttachName(String attachName) {
		this.attachName = attachName;
	}

	public String getAttachUrl() {
		return attachUrl;
	}

	public void setAttachUrl(String attachUrl) {
		this.attachUrl = attachUrl;
	}
}
