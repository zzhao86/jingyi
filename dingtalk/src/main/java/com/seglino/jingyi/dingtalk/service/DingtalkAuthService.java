package com.seglino.jingyi.dingtalk.service;

import java.util.Map;

import com.dingtalk.api.response.OapiGetJsapiTicketResponse;
import com.dingtalk.api.response.OapiGettokenResponse;
import com.dingtalk.api.response.OapiSnsGetuserinfoBycodeResponse;
import com.dingtalk.api.response.OapiSsoGettokenResponse;
import com.dingtalk.api.response.OapiSsoGetuserinfoResponse;
import com.dingtalk.api.response.OapiUserGetuserinfoResponse;

public interface DingtalkAuthService {

	/**
	 * 获取钉钉AccessToken
	 * 
	 * @return
	 */
	public OapiGettokenResponse getAccessToken();

	/**
	 * 企业内部应用免登码获取用户信息
	 * 
	 * @param code
	 * @return
	 */
	public OapiUserGetuserinfoResponse getUserDetailByCorp(String code);

	/**
	 * 获取应用管理后台免登的管理员信息
	 * 
	 * @param code
	 * @return
	 */
	public OapiSsoGetuserinfoResponse getUserDetailBySso(String code);

	/**
	 * 获取管理后台免登接口凭证
	 * 
	 * @return
	 */
	public OapiSsoGettokenResponse getSsoAccessToken();

	/**
	 * 用扫码后的临时授权码获取用户信息
	 * 
	 * @param code
	 * @return
	 */
	public OapiSnsGetuserinfoBycodeResponse getUserDetailByQrCode(String code);

	/**
	 * 获取钉钉js鉴权票据
	 * 
	 * @return
	 */
	public OapiGetJsapiTicketResponse getJsapiTicket();

	/**
	 * 获取jsapi鉴权配置
	 * 
	 * @param url 客户端请求URL
	 * @return
	 */
	public Map<String, Object> getJsapiConfig(String url);
}
