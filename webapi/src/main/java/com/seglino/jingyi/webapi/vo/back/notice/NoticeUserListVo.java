package com.seglino.jingyi.webapi.vo.back.notice;

import com.seglino.jingyi.common.core.vo.BaseVo;

public class NoticeUserListVo extends BaseVo {
	private String userId;
	private String name;
	private String avatar;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
}
