package com.seglino.jingyi.file.dto;

/**
 * 上传文件的类型
 * @author ZZH
 *
 */
public enum FileType {
	/**
	 * 图片文件
	 */
	IMAGE("image"),
	/**
	 * 普通文件
	 */
	FILE("file"),
	/**
	 * 音频文件
	 */
	AUDIO("audio"),
	/**
	 * 视频文件
	 */
	VIDEO("video");

	private String type;

	private FileType(String type) {
		this.type = type;
	}

	/**
	 * 获取枚举字符串
	 * @return
	 */
	public String getType() {
		return type;
	}
}
