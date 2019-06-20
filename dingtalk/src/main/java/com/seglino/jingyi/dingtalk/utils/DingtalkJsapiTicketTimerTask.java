package com.seglino.jingyi.dingtalk.utils;

import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.dingtalk.api.response.OapiGetJsapiTicketResponse;

@Component
public class DingtalkJsapiTicketTimerTask extends TimerTask {
	private Logger logger = LoggerFactory.getLogger(DingtalkJsapiTicketTimerTask.class);

	@Override
	public void run() {
		while (true) {
			try {
				if (StringUtils.isEmpty(DingtalkBean.AccessToken))
					continue;

				OapiGetJsapiTicketResponse response = DingtalkAuthUtils.getJsapiTicket();
				if (response.isSuccess()) {
					DingtalkBean.JsapiTicket = response.getTicket();
					DingtalkBean.JsapiTicketExpiresIn = response.getExpiresIn() - 300;

					logger.info("获取JsapiTicket成功，ticket：{}", response.getTicket());
					break;
				}
			} catch (Exception e) {
				logger.error("{}", e);
			}
		}
	}

}
