package com.seglino.jingyi.notice.pojo;

import java.io.Serializable;

import com.seglino.jingyi.common.core.po.BaseEntity;

public class Notice extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 3647940942707340222L;

	private String categoryId;
	private String title;
	private String content;
	private String author;
	private Integer readCount;
	private Integer unreadCount;
	
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
	public Integer getReadCount() {
		return readCount;
	}
	public void setReadCount(Integer readCount) {
		this.readCount = readCount;
	}
	public Integer getUnreadCount() {
		return unreadCount;
	}
	public void setUnreadCount(Integer unreadCount) {
		this.unreadCount = unreadCount;
	}
}
