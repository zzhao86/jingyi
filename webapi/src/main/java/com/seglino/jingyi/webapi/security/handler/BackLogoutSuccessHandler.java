package com.seglino.jingyi.webapi.security.handler;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import com.alibaba.fastjson.JSONObject;
import com.seglino.jingyi.common.response.ApiResult;

public class BackLogoutSuccessHandler implements LogoutSuccessHandler {

	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		PrintWriter out = null;
		try {
			ApiResult aResult = new ApiResult();
			aResult.setData("注销成功");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/json; charset=utf-8");
			out = response.getWriter();
			out.append(JSONObject.toJSONString(aResult));
		} catch (Exception e) {

		} finally {
			if (null != out) {
				out.close();
			}
		}
	}
}
