package com.seglino.jingyi.notice.dto;

import java.util.Date;
import java.util.List;

import com.seglino.jingyi.common.core.po.BaseEntity;
import com.seglino.jingyi.notice.pojo.NoticeAttach;

public class NoticeDetailDto extends BaseEntity {
	private static final long serialVersionUID = -1033000765514197178L;
	private String categoryId;
	private String title;
	private String content;
	private String author;
	private String coverUrl;
	private String scopeJson;
	private Integer readCount;
	private Integer totalCount;
	private Date publishTime;
	private List<NoticeAttach> attacheList;

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
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

	public Integer getReadCount() {
		return readCount;
	}

	public void setReadCount(Integer readCount) {
		this.readCount = readCount;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public Date getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}

	public List<NoticeAttach> getAttacheList() {
		return attacheList;
	}

	public void setAttacheList(List<NoticeAttach> attacheList) {
		this.attacheList = attacheList;
	}
}
