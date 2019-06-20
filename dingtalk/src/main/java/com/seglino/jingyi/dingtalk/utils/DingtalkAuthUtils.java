package com.seglino.jingyi.dingtalk.utils;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiGetJsapiTicketRequest;
import com.dingtalk.api.request.OapiGettokenRequest;
import com.dingtalk.api.response.OapiGetJsapiTicketResponse;
import com.dingtalk.api.response.OapiGettokenResponse;
import com.dingtalk.oapi.lib.aes.DingTalkEncryptException;
import com.dingtalk.oapi.lib.aes.DingTalkJsApiSingnature;
import com.seglino.jingyi.dingtalk.config.DingtalkConfig;
import com.taobao.api.ApiException;

public class DingtalkAuthUtils {
	private static Logger logger = LoggerFactory.getLogger(DingtalkAuthUtils.class);

	/**
	 * 获取钉钉AccessToken
	 * 
	 * @return
	 */
	public static OapiGettokenResponse getAccessToken() {
		DingTalkClient client = new DefaultDingTalkClient(DingtalkConfig.GetAccessToken);
		OapiGettokenRequest request = new OapiGettokenRequest();
		request.setCorpid(DingtalkConfig.AppKey);
		request.setCorpsecret(DingtalkConfig.AppSecret);
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
	 * 获取钉钉js鉴权票据
	 * 
	 * @return
	 */
	public static OapiGetJsapiTicketResponse getJsapiTicket() {
		DefaultDingTalkClient client = new DefaultDingTalkClient(DingtalkConfig.GetJsapiTicket);
		OapiGetJsapiTicketRequest request = new OapiGetJsapiTicketRequest();
		request.setTopHttpMethod("GET");

		OapiGetJsapiTicketResponse response = null;
		try {
			response = client.execute(request, DingtalkBean.AccessToken);
		} catch (ApiException e) {
			logger.error("{}", e);
		}
		return response;
	}
	
	/**
	 * 获取jsapi鉴权配置
	 * @param url 客户端请求URL
	 * @return
	 */
	public static Map<String, Object> getConfig(String url) {
        // 随机字符串
        String nonceStr = "hEM049VkzWiMZr2pKR7H69JQEErdImuv";
        // 时间戳
        long timeStamp = System.currentTimeMillis() / 1000;
        String signature = null;
        try {
            signature = sign(DingtalkBean.JsapiTicket, nonceStr, timeStamp, url);
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
	 * @param ticket
	 * @param nonceStr
	 * @param timeStamp
	 * @param url
	 * @return
	 */
	private static String sign(String ticket, String nonceStr, long timeStamp, String url) {
		String sign = "";
		try {
			sign = DingTalkJsApiSingnature.getJsApiSingnature(url, nonceStr, timeStamp, ticket);
		} catch (DingTalkEncryptException e) {
			logger.error("{}", e);
		}
		return sign;
	}
}
