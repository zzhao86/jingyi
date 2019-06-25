package com.seglino.jingyi.dingtalk.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiUserGetuserinfoRequest;
import com.dingtalk.api.response.OapiUserGetuserinfoResponse;
import com.seglino.jingyi.dingtalk.config.DingtalkConfig;
import com.seglino.jingyi.dingtalk.service.AuthService;
import com.seglino.jingyi.dingtalk.utils.DingtalkBean;
import com.taobao.api.ApiException;

@Service
public class AuthServiceImpl implements AuthService {
	private Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

	/**
	 * 用免登码获取用户信息
	 * 
	 * @param code
	 * @return
	 */
	@Override
	public OapiUserGetuserinfoResponse getUserDetail(String code) {
		DingTalkClient client = new DefaultDingTalkClient(DingtalkConfig.GetUserDetailByCode);
		OapiUserGetuserinfoRequest request = new OapiUserGetuserinfoRequest();
		request.setCode(code);
		request.setHttpMethod("GET");
		OapiUserGetuserinfoResponse response = null;
		try {
			response = client.execute(request, DingtalkBean.AccessToken);
		} catch (ApiException e) {
			logger.error("{}", e);
			e.printStackTrace();
		}
		return response;
	}

}
