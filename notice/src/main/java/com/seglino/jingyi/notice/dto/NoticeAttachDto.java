package com.seglino.jingyi.notice.dto;

public class NoticeAttachDto {
	private String id;
	private String attachId;
	private String attachName;
	private String attachUrl;
	private Long attachSize;
	private String attachType;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public Long getAttachSize() {
		return attachSize;
	}

	public void setAttachSize(Long attachSize) {
		this.attachSize = attachSize;
	}

	public String getAttachType() {
		return attachType;
	}

	public void setAttachType(String attachType) {
		this.attachType = attachType;
	}
}
