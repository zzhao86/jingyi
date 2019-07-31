package com.seglino.jingyi.dingtalk.service;

import com.dingtalk.api.response.OapiMessageCorpconversationAsyncsendV2Response;
import com.seglino.jingyi.dingtalk.dto.DingtalkWorkMessageDto;
public interface DingtalkMessageService {

	/**
	 * 发送工作通知
	 * @param workMsg 消息参数和内容
	 * @return
	 */
	public OapiMessageCorpconversationAsyncsendV2Response sendWorkMessage(DingtalkWorkMessageDto workMsg);
}
