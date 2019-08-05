package com.seglino.jingyi.common.encrypt;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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

	/**
	 * 字符串SHA1加密
	 * 
	 * @param text
	 * @return
	 */
	public static String getSHA1(String text) {
		MessageDigest sha1;
		String result = null;
		char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			sha1 = MessageDigest.getInstance("SHA1");
			sha1.update(text.getBytes("UTF-8"));
			byte[] bytes = sha1.digest();
			int length = bytes.length;
			char[] results = new char[length * 2];
			int j = 0;
			for (int i = 0; i < length; i++) {
				byte b = bytes[i];
				results[j++] = hexDigits[b >>> 4 & 0xf];
				results[j++] = hexDigits[b & 0xf];
			}
			result = new String(results);
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}
}
