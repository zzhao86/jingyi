package com.seglino.jingyi.user.pojo;

import com.seglino.jingyi.common.core.po.BaseEntity;

public class User extends BaseEntity {
	private static final long serialVersionUID = -5722258210735686372L;

	private String name;
	private String mobile;
	private String tel;
	private String email;
	private String avatar;
	private String position;
	private String ddUserId;
	private String openid;
	private String unionid;
	private Integer type;
	private String json;

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getUnionid() {
		return unionid;
	}

	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}
}
