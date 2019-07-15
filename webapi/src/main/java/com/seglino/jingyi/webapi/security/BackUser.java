//package com.seglino.jingyi.webapi.security;
//
//import java.util.Collection;
//
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.User;
//
//public class BackUser extends User {
//	private static final long serialVersionUID = -5662577170014981491L;
//
//	public BackUser(String id, String name, String mobile, String tel, String email, String position, String avatar, Collection<? extends GrantedAuthority> authorities) {
//		super(id, "123456", authorities);
//		this.id = id;
//		this.name = name;
//		this.mobile = mobile;
//		this.tel = tel;
//		this.email = email;
//		this.position = position;
//		this.avatar = avatar;
//	}
//
//	private String id;
//	private String name;
//	private String mobile;
//	private String tel;
//	private String email;
//	private String position;
//	private String avatar;
//
//	public String getId() {
//		return id;
//	}
//
//	public void setId(String id) {
//		this.id = id;
//	}
//
//	public String getName() {
//		return name;
//	}
//
//	public void setName(String name) {
//		this.name = name;
//	}
//
//	public String getMobile() {
//		return mobile;
//	}
//
//	public void setMobile(String mobile) {
//		this.mobile = mobile;
//	}
//
//	public String getTel() {
//		return tel;
//	}
//
//	public void setTel(String tel) {
//		this.tel = tel;
//	}
//
//	public String getEmail() {
//		return email;
//	}
//
//	public void setEmail(String email) {
//		this.email = email;
//	}
//
//	public String getPosition() {
//		return position;
//	}
//
//	public void setPosition(String position) {
//		this.position = position;
//	}
//
//	public String getAvatar() {
//		return avatar;
//	}
//
//	public void setAvatar(String avatar) {
//		this.avatar = avatar;
//	}
//}
