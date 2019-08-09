package com.seglino.jingyi.webapi.vo.client.notice;

import com.seglino.jingyi.common.core.vo.BaseVo;

public class NoticeAttachVo extends BaseVo {
	private String attachName;
	private String attachUrl;
	private Long attachSize;
	private String attachType;
	private String size;

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

	public void setAttachSize(Long attachSize) {
		this.attachSize = attachSize;
	}

	public String getAttachType() {
		return attachType;
	}

	public void setAttachType(String attachType) {
		this.attachType = attachType;
	}

	public String getSize() {
		long KB = 1024;
		long MB = KB * 1024;
		long GB = MB * 1024;
		if (attachSize >= GB) {
			size = String.format("%.1f GB", (float) attachSize / GB);
		} else if (attachSize > MB) {
			float value = (float) attachSize / MB;
			size = String.format(value > 100 ? "%.0f MB" : "%.1f MB", value);
		} else if (attachSize > KB) {
			float value = (float) attachSize / KB;
			size = String.format(value > 100 ? "%.0f KB" : "%.1f KB", value);
		} else {
			size = String.format("%d B", attachSize);
		}
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

}
