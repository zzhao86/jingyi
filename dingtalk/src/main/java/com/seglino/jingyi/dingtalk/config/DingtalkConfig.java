package com.seglino.jingyi.dingtalk.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DingtalkConfig {
	private static Properties prop;
	private static InputStream inputStream;
	static {
		try {
			inputStream = ClassLoader.getSystemResourceAsStream("config.properties");
			prop = new Properties();
			prop.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 钉钉CorpId
	 */
	public final static String CorpId = prop.getProperty("CorpId");

	/**
	 * 钉钉AppKey
	 */
	public final static String AppKey = prop.getProperty("AppKey");
	/**
	 * 钉钉AppSecret
	 */
	public final static String AppSecret = prop.getProperty("AppSecret");
	/**
	 * 应用AgentId
	 */
	public final static String AgentId = prop.getProperty("AgentId");

	/**
	 * 钉钉接口基础URL
	 */
	public final static String ApiBaseUrl = prop.getProperty("ApiBaseUrl");

	/**
	 * 获取access_token接口
	 */
	public final static String GetAccessToken = ApiBaseUrl + prop.getProperty("GetAccessToken");
	/**
	 * 获取jsapi_ticket接口
	 */
	public final static String GetJsapiTicket = ApiBaseUrl + prop.getProperty("GetJsapiTicket");
	/**
	 * 通过免登授权码获取用户的userid接口
	 */
	public final static String GetUserinfoByCode = ApiBaseUrl + prop.getProperty("GetUserinfoByCode");
}
