package com.seglino.jingyi.dingtalk.dto;

public enum MessageType {
	/**
	 * 文本消息
	 */
	TEXT("text"),
	/**
	 * 图片消息
	 */
	IMAGE("image"),
	/**
	 * 语音消息
	 */
	VOICE("voice"),
	/**
	 * 文件消息
	 */
	FILE("file"),
	/**
	 * 链接消息
	 */
	LINK("link"),
	/**
	 * OA消息
	 */
	OA("oa"),
	/**
	 * markdown消息
	 */
	MARKDOWN("markdown"),
	/**
	 * 卡片消息
	 */
	ACTION_CARD("action_card");

	private String type;
	private MessageType(String type) {
		this.type = type;
	}
	
	public String getType() {
		return type;
	}
}
