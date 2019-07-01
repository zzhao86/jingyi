package com.seglino.jingyi.dingtalk.utils;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.dingtalk.api.response.OapiGetJsapiTicketResponse;
import com.dingtalk.api.response.OapiGettokenResponse;
import com.dingtalk.api.response.OapiSsoGettokenResponse;
import com.seglino.jingyi.dingtalk.service.AuthService;

@Component
public class DingtalkInit {
	private Logger logger = LoggerFactory.getLogger(DingtalkInit.class);

	@Autowired
	private AuthService authService;

	@PostConstruct
	public void init() {
		try {
			ScheduledThreadPoolExecutor scheduled = new ScheduledThreadPoolExecutor(3);
			// 定时获取钉钉AccessToken
			scheduled.scheduleAtFixedRate(new Runnable() {				
				@Override
				public void run() {
					while (true) {
						try {
							OapiGettokenResponse response = authService.getAccessToken();
							if (response.isSuccess()) {
								DingtalkGlobal.AccessToken = response.getAccessToken();
								DingtalkGlobal.AccessTokenExpiresIn = response.getExpiresIn();

								logger.info("获取AccessToken成功，token：{}", response.getAccessToken());
								break;
							} else {
								Thread.sleep(60 * 1000);
							}
						} catch (Exception e) {
							logger.error("{}", e);
						}
					}
				}
			}, 0, 6900, TimeUnit.SECONDS);
			
			
			// 定时获取钉钉SsoAccessToken
			scheduled.scheduleAtFixedRate(new Runnable() {				
				@Override
				public void run() {
					while (true) {
						try {
							OapiSsoGettokenResponse response = authService.getSsoAccessToken();
							if (response.isSuccess()) {
								DingtalkGlobal.SsoAccessToken = response.getAccessToken();
								DingtalkGlobal.SsoAccessTokenExpiresIn = 7200;

								logger.info("获取SsoAccessToken成功，token：{}", response.getAccessToken());
								break;
							} else {
								Thread.sleep(60 * 1000);
							}
						} catch (Exception e) {
							logger.error("{}", e);
						}
					}
				}
			}, 0, 6900, TimeUnit.SECONDS);
			
			
			// 定时获取钉钉JsapiTicket
			scheduled.scheduleAtFixedRate(new Runnable() {				
				@Override
				public void run() {
					while (true) {
						try {
							if (StringUtils.isEmpty(DingtalkGlobal.AccessToken))
								continue;

							OapiGetJsapiTicketResponse response = authService.getJsapiTicket();
							if (response.isSuccess()) {
								DingtalkGlobal.JsapiTicket = response.getTicket();
								DingtalkGlobal.JsapiTicketExpiresIn = response.getExpiresIn();

								logger.info("获取JsapiTicket成功，ticket：{}", response.getTicket());
								break;
							} else {
								Thread.sleep(60 * 1000);
							}
						} catch (Exception e) {
							logger.error("{}", e);
						}
					}
				}
			}, 0, 6900, TimeUnit.SECONDS);
		} catch (Exception e) {
			logger.error("{}", e);
		}
	}
}
