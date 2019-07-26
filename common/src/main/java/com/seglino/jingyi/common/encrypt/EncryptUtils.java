package com.seglino.jingyi.common.encrypt;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;

import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

public class EncryptUtils {

	/**
	 * 获取字符串的MD5加密值
	 * 
	 * @param text 加密字符
	 * @return
	 */
	public static String getMD5(String text) {
		byte[] bytes = null;
		try {
			MessageDigest digest = MessageDigest.getInstance("MD5");
			digest.update(text.getBytes());
			bytes = digest.digest();
			String md5 = "";
			for (int i = 0; i < bytes.length; i++) {
				md5 += Integer.toHexString(0x000000FF & bytes[i] | 0xFFFFFF00).substring(6);
			}
			return md5;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 获取文件的MD5值
	 * 
	 * @param path 文件物理路径
	 * @return
	 */
	public static String getFileMD5(String path) {
		String md5 = "";
		if (StringUtils.isEmpty(path))
			return md5;
		try {
			md5 = getFileMD5(new FileInputStream(path));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return md5;
	}

	/**
	 * 获取文件的MD5值
	 * 
	 * @param stream 文件流
	 * @return
	 */
	public static String getFileMD5(InputStream stream) {
		String md5 = "";
		if (null == stream)
			return md5;
		try {
			md5 = DigestUtils.md5DigestAsHex(stream);
			stream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return md5;
	}
}
