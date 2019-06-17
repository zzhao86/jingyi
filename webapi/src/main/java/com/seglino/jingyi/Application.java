package com.seglino.jingyi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.Banner.Mode;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
	
	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(Application.class);
		application.setBannerMode(Mode.CONSOLE);
		application.run(args);
		System.out.printf("客户端已启动...");
	}
}
