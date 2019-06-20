package com.seglino.jingyi;

import java.util.Timer;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.Banner.Mode;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.seglino.jingyi.dingtalk.utils.DingtalkAccessTokenTimerTask;
import com.seglino.jingyi.dingtalk.utils.DingtalkBean;
import com.seglino.jingyi.dingtalk.utils.DingtalkJsapiTicketTimerTask;

@SpringBootApplication
public class Application {
	private static Logger logger = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(Application.class);
		application.setBannerMode(Mode.CONSOLE);
		application.run(args);
		logger.info("客户端已启动...");
	}

	@PostConstruct
	public void init() {
		try {
			// 初始化并运行获取钉钉接口凭证和js接口票据的定时任务
			DingtalkAccessTokenTimerTask accessTokenTimerTask = new DingtalkAccessTokenTimerTask();
			accessTokenTimerTask.run();
			DingtalkJsapiTicketTimerTask jsapiTicketTimerTask = new DingtalkJsapiTicketTimerTask();
			jsapiTicketTimerTask.run();
			
			long accessTokenExpiresIn = DingtalkBean.AccessTokenExpiresIn;
			long jsapiTicketExpiresIn = DingtalkBean.JsapiTicketExpiresIn;

			// 从第一次定时任务有效期过后，开始循环执行定时任务
			Timer timer = new Timer();
			timer.scheduleAtFixedRate(new DingtalkAccessTokenTimerTask(), accessTokenExpiresIn * 1000, accessTokenExpiresIn * 1000);
			timer.scheduleAtFixedRate(new DingtalkJsapiTicketTimerTask(), jsapiTicketExpiresIn * 1000, jsapiTicketExpiresIn * 1000);
		} catch (Exception e) {
			logger.error("{}", e);
		}
	}
}
