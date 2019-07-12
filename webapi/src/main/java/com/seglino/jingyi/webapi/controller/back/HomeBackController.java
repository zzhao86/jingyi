package com.seglino.jingyi.webapi.controller.back;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("back")
public class HomeBackController {

	private String backBaseUrl = "http://192.168.0.8:5052/#/";

	@GetMapping("home")
	public ModelAndView home(HttpServletRequest request) {
		return new ModelAndView(new RedirectView(backBaseUrl));
	}

	@GetMapping("login")
	public ModelAndView login() {
		return new ModelAndView(new RedirectView(backBaseUrl + "login"));
	}
	
//	@GetMapping("logout")
//	public ModelAndView logout() {
//		return new ModelAndView(new RedirectView(backBaseUrl + "login"));
//	}
}
