package com.seglino.jingyi.dingtalk.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiGetJsapiTicketRequest;
import com.dingtalk.api.request.OapiGettokenRequest;
import com.dingtalk.api.request.OapiSnsGetuserinfoBycodeRequest;
import com.dingtalk.api.request.OapiSsoGettokenRequest;
import com.dingtalk.api.request.OapiSsoGetuserinfoRequest;
import com.dingtalk.api.request.OapiUserGetuserinfoRequest;
import com.dingtalk.api.response.OapiGetJsapiTicketResponse;
import com.dingtalk.api.response.OapiGettokenResponse;
import com.dingtalk.api.response.OapiSnsGetuserinfoBycodeResponse;
import com.dingtalk.api.response.OapiSsoGettokenResponse;
import com.dingtalk.api.response.OapiSsoGetuserinfoResponse;
import com.dingtalk.api.response.OapiUserGetuserinfoResponse;
import com.dingtalk.oapi.lib.aes.DingTalkEncryptException;
import com.dingtalk.oapi.lib.aes.DingTalkJsApiSingnature;
import com.seglino.jingyi.dingtalk.config.DingtalkConfig;
import com.seglino.jingyi.dingtalk.service.AuthService;
import com.seglino.jingyi.dingtalk.utils.DingtalkGlobal;
import com.taobao.api.ApiException;

@Service
public class AuthServiceImpl implements AuthService {
	private Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

	/**
	 * 获取钉钉AccessToken
	 * 
	 * @return
	 */
	public OapiGettokenResponse getAccessToken() {
		DingTalkClient client = new DefaultDingTalkClient(DingtalkConfig.GetAccessToken);
		OapiGettokenRequest request = new OapiGettokenRequest();
		// request.setCorpid(DingtalkConfig.AppKey);
		// request.setCorpsecret(DingtalkConfig.AppSecret);
		request.setAppkey(DingtalkConfig.AppKey);
		request.setAppsecret(DingtalkConfig.AppSecret);
		request.setTopHttpMethod("GET");

		OapiGettokenResponse response = null;
		try {
			response = client.execute(request);
		} catch (ApiException e) {
			logger.error("{}", e);
		}
		return response;
	}

	/**
	 * 用免登码获取用户信息
	 * 
	 * @param code
	 * @return
	 */
	@Override
	public OapiUserGetuserinfoResponse getUserDetailByCorp(String code) {
		DingTalkClient client = new DefaultDingTalkClient(DingtalkConfig.GetUserDetailByCode);
		OapiUserGetuserinfoRequest request = new OapiUserGetuserinfoRequest();
		request.setCode(code);
		request.setHttpMethod("GET");
		OapiUserGetuserinfoResponse response = null;
		try {
			response = client.execute(request, DingtalkGlobal.AccessToken);
		} catch (ApiException e) {
			logger.error("{}", e);
			e.printStackTrace();
		}
		return response;
	}

	/**
	 * 获取管理后台免登接口凭证
	 * 
	 * @return
	 */
	public OapiSsoGettokenResponse getSsoAccessToken() {
		DingTalkClient client = new DefaultDingTalkClient(DingtalkConfig.GetSsoAccessToken);
		OapiSsoGettokenRequest request = new OapiSsoGettokenRequest();
		request.setCorpid(DingtalkConfig.CorpId);
		request.setCorpsecret(DingtalkConfig.SSOSecret);
		request.setHttpMethod("GET");
		OapiSsoGettokenResponse response = null;
		try {
			response = client.execute(request);
		} catch (Exception e) {
			logger.error("{}", e);
			e.printStackTrace();
		}
		return response;
	}

	/**
	 * 获取应用管理后台免登的管理员信息
	 * 
	 * @param code
	 * @return
	 */
	public OapiSsoGetuserinfoResponse getUserDetailBySso(String code) {
		DingTalkClient client = new DefaultDingTalkClient(DingtalkConfig.GetUserDetailBySSO);
		OapiSsoGetuserinfoRequest request = new OapiSsoGetuserinfoRequest();
		request.setCode(code);
		request.setHttpMethod("GET");
		OapiSsoGetuserinfoResponse response = null;
		try {
			response = client.execute(request, DingtalkGlobal.SsoAccessToken);
			if (!response.isSuccess()) {
				logger.error(response.getErrmsg());
			}
		} catch (ApiException e) {
			logger.error("{}", e);
			e.printStackTrace();
		}
		return response;
	}

	/**
	 * 用扫码后的临时授权码获取用户信息
	 * 
	 * @param code
	 * @return
	 */
	public OapiSnsGetuserinfoBycodeResponse getUserDetailByQrCode(String code) {
		DefaultDingTalkClient client = new DefaultDingTalkClient(DingtalkConfig.GetUserDetailByQrCode);
		OapiSnsGetuserinfoBycodeRequest request = new OapiSnsGetuserinfoBycodeRequest();
		request.setTmpAuthCode(code);
		request.setHttpMethod("POST");
		OapiSnsGetuserinfoBycodeResponse response = null;
		try {
			response = client.execute(request, DingtalkConfig.QrCodeId, DingtalkConfig.QrCodeSecret);
		} catch (ApiException e) {
			logger.error("{}", e);
			e.printStackTrace();
		}
		return response;
	}

	/**
	 * 获取钉钉js鉴权票据
	 * 
	 * @return
	 */
	public OapiGetJsapiTicketResponse getJsapiTicket() {
		DefaultDingTalkClient client = new DefaultDingTalkClient(DingtalkConfig.GetJsapiTicket);
		OapiGetJsapiTicketRequest request = new OapiGetJsapiTicketRequest();
		request.setTopHttpMethod("GET");

		OapiGetJsapiTicketResponse response = null;
		try {
			response = client.execute(request, DingtalkGlobal.AccessToken);
		} catch (ApiException e) {
			logger.error("{}", e);
		}
		return response;
	}

	/**
	 * 获取jsapi鉴权配置
	 * 
	 * @param url 客户端请求URL
	 * @return
	 */
	public Map<String, Object> getJsapiConfig(String url) {
		// 随机字符串
		String nonceStr = "hEM049VkzWiMZr2pKR7H69JQEErdImuv";
		// 时间戳
		long timeStamp = System.currentTimeMillis() / 1000;
		String signature = null;
		try {
			signature = sign(DingtalkGlobal.JsapiTicket, nonceStr, timeStamp, url);
		} catch (Exception e) {
			logger.error("{}", e);
		}

		Map<String, Object> config = new HashMap<>();
		config.put("signature", signature);
		config.put("nonceStr", nonceStr);
		config.put("timeStamp", timeStamp);
		config.put("corpId", DingtalkConfig.CorpId);
		config.put("agentId", DingtalkConfig.AgentId);
		return config;
	}

	/**
	 * 获取签名
	 * 
	 * @param ticket
	 * @param nonceStr
	 * @param timeStamp
	 * @param url
	 * @return
	 */
	private String sign(String ticket, String nonceStr, long timeStamp, String url) {
		String sign = "";
		try {
			sign = DingTalkJsApiSingnature.getJsApiSingnature(url, nonceStr, timeStamp, ticket);
		} catch (DingTalkEncryptException e) {
			logger.error("{}", e);
		}
		return sign;
	}
}
