package com.seglino.jingyi.notice.pojo;

import com.seglino.jingyi.common.core.po.BaseEntity;

public class NoticeFile extends BaseEntity {
	private static final long serialVersionUID = -413137793333530247L;
	private String noticeId;
	private String fileName;
	private String fileUrl;

	public String getNoticeId() {
		return noticeId;
	}

	public void setNoticeId(String noticeId) {
		this.noticeId = noticeId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}
}
