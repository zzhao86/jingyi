package com.seglino.jingyi.dingtalk.utils;

import java.util.Timer;
import java.util.TimerTask;

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
			// 初始化并运行维护钉钉凭证的定时任务，获取到定时任务的间隔时间
			DingtalkAccessTokenTimerTask accessTokenTimerTask = new DingtalkAccessTokenTimerTask();
			accessTokenTimerTask.run();
			DingtalkSsoAccessTokenTimerTask ssoAccessTokenTimerTask = new DingtalkSsoAccessTokenTimerTask();
			ssoAccessTokenTimerTask.run();
			DingtalkJsapiTicketTimerTask jsapiTicketTimerTask = new DingtalkJsapiTicketTimerTask();
			jsapiTicketTimerTask.run();

			long accessTokenExpiresIn = (DingtalkGlobal.AccessTokenExpiresIn - 300) * 1000;
			long ssoAccessTokenExpiresIn = (DingtalkGlobal.SsoAccessTokenExpiresIn - 300) * 1000;
			long jsapiTicketExpiresIn = (DingtalkGlobal.JsapiTicketExpiresIn - 300) * 1000;

			// 从第一次定时任务有效期过后，开始循环执行定时任务
			Timer timer = new Timer();
			timer.schedule(accessTokenTimerTask, accessTokenExpiresIn, accessTokenExpiresIn);
			timer.schedule(ssoAccessTokenTimerTask, ssoAccessTokenExpiresIn, ssoAccessTokenExpiresIn);
			timer.schedule(jsapiTicketTimerTask, jsapiTicketExpiresIn, jsapiTicketExpiresIn);
		} catch (Exception e) {
			logger.error("{}", e);
		}
	}

	/**
	 * 定时维护钉钉接口凭证
	 * 
	 * @author ZZH
	 *
	 */
	public class DingtalkAccessTokenTimerTask extends TimerTask {
		private Logger logger = LoggerFactory.getLogger(DingtalkAccessTokenTimerTask.class);

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
	}

	/**
	 * 定时维护钉钉应用后台管理员免登凭证
	 * 
	 * @author ZZH
	 *
	 */
	public class DingtalkSsoAccessTokenTimerTask extends TimerTask {
		private Logger logger = LoggerFactory.getLogger(DingtalkSsoAccessTokenTimerTask.class);

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
	}

	/**
	 * 定时维护钉钉jsapi票据
	 * 
	 * @author ZZH
	 *
	 */
	public class DingtalkJsapiTicketTimerTask extends TimerTask {
		private Logger logger = LoggerFactory.getLogger(DingtalkJsapiTicketTimerTask.class);

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
	}

}
