package com.seglino.jingyi.dingtalk.utils;

import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dingtalk.api.response.OapiGettokenResponse;

public class DingtalkAccessTokenTimerTask extends TimerTask {
	private Logger logger = LoggerFactory.getLogger(DingtalkAccessTokenTimerTask.class);

	@Override
	public void run() {
		while (true) {
			try {
				OapiGettokenResponse response = DingtalkAuthUtils.getAccessToken();
				if (response.isSuccess()) {
					DingtalkBean.AccessToken = response.getAccessToken();
					DingtalkBean.AccessTokenExpiresIn = response.getExpiresIn() - 300;

					logger.info("获取AccessToken成功，token：{}", response.getAccessToken());
					break;
				}
			} catch (Exception e) {
				logger.error("{}", e);
			}
		}
	}
}
