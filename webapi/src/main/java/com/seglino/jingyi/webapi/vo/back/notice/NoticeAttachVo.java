package com.seglino.jingyi.webapi.vo.back.notice;

import com.seglino.jingyi.common.core.vo.BaseVo;

public class NoticeAttachVo extends BaseVo {
	private String attachName;
	private String attachUrl;

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
