package com.seglino.jingyi.webapi.controller.client;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seglino.jingyi.common.response.ApiResult;
import com.seglino.jingyi.notice.pojo.Notice;
import com.seglino.jingyi.notice.service.NoticeService;

@RestController
@RequestMapping("client/notice")
public class NoticeClientController {
	
	@Autowired
	private NoticeService noticeService;
	
	@GetMapping("list")
	public ApiResult list() {
		ApiResult aResult = new ApiResult();
		try {
			Map<String, Object> param=new HashMap<String, Object>();
			param.put("isDeleted", false);
			List<Notice> list = noticeService.list(param);
			aResult.setData(list);
		} catch (Exception e) {
			aResult.AddError(e);
		}
		return aResult;
	}
}