package com.seglino.jingyi.webapi.vo.client.notice;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.seglino.jingyi.common.core.vo.BaseVo;

public class NoticeDetailVo extends BaseVo {
	private String title;
	private String author;
	private String content;
	private String coverId;
	private String coverUrl;
	private String scopeJson;
	private int readCount;
	private int totalCount;
	private Date publishTime;
	private List<NoticeAttachVo> attachList = new ArrayList<NoticeAttachVo>();
	private Boolean needReply;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCoverId() {
		return coverId;
	}

	public void setCoverId(String coverId) {
		this.coverId = coverId;
	}

	public String getCoverUrl() {
		return coverUrl;
	}

	public void setCoverUrl(String coverUrl) {
		this.coverUrl = coverUrl;
	}

	public String getScopeJson() {
		return scopeJson;
	}

	public void setScopeJson(String scopeJson) {
		this.scopeJson = scopeJson;
	}

	public int getReadCount() {
		return readCount;
	}

	public void setReadCount(int readCount) {
		this.readCount = readCount;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public Date getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}

	public List<NoticeAttachVo> getAttachList() {
		return attachList;
	}

	public void setAttachList(List<NoticeAttachVo> attachList) {
		this.attachList = attachList;
	}

	public Boolean getNeedReply() {
		return needReply;
	}

	public void setNeedReply(Boolean needReply) {
		this.needReply = needReply;
	}
}
