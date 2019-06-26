package com.seglino.jingyi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.Banner.Mode;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
	private static Logger logger = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(Application.class);
		application.setBannerMode(Mode.CONSOLE);
		application.run(args);
		logger.info("服务已启动...");
	}

}
