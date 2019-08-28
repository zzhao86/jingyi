package com.seglino.jingyi.user.dto;

import com.seglino.jingyi.common.core.dto.BaseDto;

public class UserListDto extends BaseDto {
	private String name;
	private String mobile;
	private String avatar;
	private String position;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}
}
