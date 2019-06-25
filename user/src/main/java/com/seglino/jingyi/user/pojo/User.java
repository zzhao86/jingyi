package com.seglino.jingyi.user.pojo;

import com.seglino.jingyi.common.core.po.BaseEntity;

public class User extends BaseEntity {
	
	private String name;
	private String mobile;
	private String tel;
	private String avatar;
	private String position;
	private String ddUserId;
	private String openid;
	
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
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
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
	public String getDdUserId() {
		return ddUserId;
	}
	public void setDdUserId(String ddUserId) {
		this.ddUserId = ddUserId;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
}
