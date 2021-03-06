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
import com.seglino.jingyi.common.encrypt.EncryptUtils;
import com.seglino.jingyi.common.log.annotation.ServiceLog;
import com.seglino.jingyi.dingtalk.config.DingtalkConfig;
import com.seglino.jingyi.dingtalk.service.DingtalkAuthService;
import com.seglino.jingyi.dingtalk.utils.DingtalkGlobal;
import com.taobao.api.ApiException;

@ServiceLog("钉钉认证授权服务")
@Service
public class DingtalkAuthServiceImpl implements DingtalkAuthService {
	private Logger logger = LoggerFactory.getLogger(DingtalkAuthServiceImpl.class);

	/**
	 * 获取钉钉AccessToken
	 * 
	 * @return
	 */
	@ServiceLog("获取钉钉业务接口凭证")
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
	@ServiceLog("用免登码获取用户信息")
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
	@ServiceLog("获取管理后台免登接口凭证")
	@Override
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
	@ServiceLog("应用管理后台免登的管理员信息")
	@Override
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
	@ServiceLog("扫码登录获取用户信息")
	@Override
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
	@ServiceLog("获取钉钉js鉴权票据")
	@Override
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
	@ServiceLog("获取jsapi鉴权配置")
	@Override
	public Map<String, Object> getJsapiConfig(String url) {
		// 随机字符串
		String nonceStr = "hEM049VkzWiMZr2pKR7H69JQEErdImuv";
		// 时间戳
		long timeStamp = System.currentTimeMillis() / 1000;
		String signature = null;
		try {
			signature = sign(url, nonceStr, timeStamp);
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
	private String sign(String url, String nonceStr, long timeStamp) {
		String plain = "jsapi_ticket=" + DingtalkGlobal.JsapiTicket + "&noncestr=" + nonceStr + "&timestamp=" + String.valueOf(timeStamp) + "&url=" + url;
		// System.out.println(plain);
		return EncryptUtils.getSHA1(plain);
	}
}
