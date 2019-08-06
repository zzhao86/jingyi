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
import com.seglino.jingyi.dingtalk.service.DingtalkAuthService;

@Component
public class DingtalkInit {
	private Logger logger = LoggerFactory.getLogger(DingtalkInit.class);

	@Autowired
	private DingtalkAuthService authService;

	@PostConstruct
	public void init() {
		try {
			DingtalkAccessTokenThread accessTokenThread = new DingtalkAccessTokenThread();
			accessTokenThread.run();
			DingtalkSsoAccessTokenThread ssoAccessTokenThread = new DingtalkSsoAccessTokenThread();
			ssoAccessTokenThread.run();
			DingtalkJsapiTicketThread jsapiTicketThread = new DingtalkJsapiTicketThread();
			jsapiTicketThread.run();

			long accessTokenDelay = DingtalkGlobal.AccessTokenExpiresIn - 300;
			long ssoTokenDelay = DingtalkGlobal.SsoAccessTokenExpiresIn - 300;
			long jsapiTicketDelay = DingtalkGlobal.JsapiTicketExpiresIn - 300;

			ScheduledThreadPoolExecutor scheduled = new ScheduledThreadPoolExecutor(1);
			// 定时获取钉钉AccessToken
			scheduled.scheduleAtFixedRate(accessTokenThread, accessTokenDelay, accessTokenDelay, TimeUnit.SECONDS);
			// 定时获取钉钉SsoAccessToken
			scheduled.scheduleAtFixedRate(ssoAccessTokenThread, ssoTokenDelay, ssoTokenDelay, TimeUnit.SECONDS);
			// 定时获取钉钉JsapiTicket
			scheduled.scheduleAtFixedRate(jsapiTicketThread, jsapiTicketDelay, jsapiTicketDelay, TimeUnit.SECONDS);
		} catch (Exception e) {
			logger.error("{}", e);
		}
	}

	public class DingtalkAccessTokenThread implements Runnable {
		@Override
		public void run() {
			synchronized (this) {
				while (true) {
					try {
						OapiGettokenResponse response = authService.getAccessToken();
						if (response.isSuccess()) {
							DingtalkGlobal.AccessToken = response.getAccessToken();
							DingtalkGlobal.AccessTokenExpiresIn = response.getExpiresIn();

							logger.info("获取AccessToken成功，token：{}", response.getAccessToken());
							break;
						} else {
							this.wait(60 * 1000);
						}
					} catch (Exception e) {
						logger.error("{}", e);
					}
				}
			}
		}
	}

	public class DingtalkSsoAccessTokenThread implements Runnable {
		@Override
		public void run() {
			synchronized (this) {
				while (true) {
					try {
						OapiSsoGettokenResponse response = authService.getSsoAccessToken();
						if (response.isSuccess()) {
							DingtalkGlobal.SsoAccessToken = response.getAccessToken();
							DingtalkGlobal.SsoAccessTokenExpiresIn = 7200;

							logger.info("获取SsoAccessToken成功，token：{}", response.getAccessToken());
							break;
						} else {
							this.wait(60 * 1000);
						}
					} catch (Exception e) {
						logger.error("{}", e);
					}
				}
			}
		}
	}

	public class DingtalkJsapiTicketThread implements Runnable {
		@Override
		public void run() {
			synchronized (this) {
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
							this.wait(60 * 1000);
						}
					} catch (Exception e) {
						logger.error("{}", e);
					}
				}
			}
		}
	}
}
