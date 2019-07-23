package com.seglino.jingyi.webapi.controller.back;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONException;
import com.baidu.ueditor.ActionEnter;

@RestController
@RequestMapping("static/ueditor")
public class UEditorController {

	@RequestMapping("controller")
	public void getConfigInfo(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("application/json");
		String rootPath = System.getProperty("user.dir").replace("\\", "/");
		try {
			String exec = new ActionEnter(request, rootPath).exec();
			PrintWriter writer = response.getWriter();
			writer.write(exec);
			writer.flush();
			writer.close();
		} catch (IOException | JSONException e) {
			e.printStackTrace();
		}
	}
}
