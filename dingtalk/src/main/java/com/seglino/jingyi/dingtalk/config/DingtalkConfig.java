package com.seglino.jingyi.dingtalk.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.seglino.jingyi.common.settings.service.SettingsService;

@Component
public class DingtalkConfig {
	private static Properties prop;

	@Autowired
	private SettingsService settingsService;

	@PostConstruct
	public void init() {
		CorpId = settingsService.getValue("DINGTALK_CORPID");
		AppKey = settingsService.getValue("DINGTALK_APPKEY");
		AppSecret = settingsService.getValue("DINGTALK_APPSECRET");
		AgentId = settingsService.getValue("DINGTALK_AGENTID");
		SSOSecret = settingsService.getValue("DINGTALK_SSOSECRET");
	}

	static {
		InputStream inputStream = null;
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
	public static String CorpId = "";

	/**
	 * 钉钉AppKey
	 */
	public static String AppKey = "";
	/**
	 * 钉钉AppSecret
	 */
	public static String AppSecret = "";
	/**
	 * 应用AgentId
	 */
	public static String AgentId = "";
	/**
	 * 钉钉SSOSecret
	 */
	public static String SSOSecret = "";

	/**
	 * 钉钉接口基础URL
	 */
	public static String ApiBaseUrl = prop.getProperty("ApiBaseUrl");

	/**
	 * 获取access_token接口
	 */
	public final static String GetAccessToken = ApiBaseUrl + prop.getProperty("GetAccessToken");
	/**
	 * 获取jsapi_ticket接口
	 */
	public final static String GetJsapiTicket = ApiBaseUrl + prop.getProperty("GetJsapiTicket");

	/**
	 * 获取应用管理后台免登的access_token接口
	 */
	public final static String GetSsoAccessToken = ApiBaseUrl + prop.getProperty("GetSsoAccessToken");
	/**
	 * 获取应用管理后台免登的管理员信息
	 */
	public final static String GetUserDetailBySSO = ApiBaseUrl + prop.getProperty("GetUserDetailBySSO");
	/**
	 * 通过免登授权码获取用户的userid接口
	 */
	public final static String GetUserDetailByCode = ApiBaseUrl + prop.getProperty("GetUserDetailByCode");

	/**
	 * 获取部门用户userid列表接口
	 */
	public final static String GetUserIds = ApiBaseUrl + prop.getProperty("GetUserIds");
	/**
	 * 获取用户详情接口
	 */
	public final static String GetUserDetail = ApiBaseUrl + prop.getProperty("GetUserDetail");
	/**
	 * 获取钉钉管理员列表接口
	 */
	public final static String GetAdminList = ApiBaseUrl + prop.getProperty("GetAdminList");

	/**
	 * 获取部门列表
	 */
	public final static String GetDeptList = ApiBaseUrl + prop.getProperty("GetDeptList");
	/**
	 * 获取部门详情
	 */
	public final static String GetDeptDetail = ApiBaseUrl + prop.getProperty("GetDeptDetail");
}
