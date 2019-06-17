package com.seglino.jingyi.webapi.controller.client;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seglino.jingyi.common.response.ApiResult;
import com.seglino.jingyi.notice.pojo.Notice;
import com.seglino.jingyi.notice.service.NoticeService;

@RestController
@RequestMapping("client/notice")
public class NoticeController {
	
	@Autowired
	private NoticeService noticeService;
	
	@GetMapping("list")
	public ApiResult list() {
		ApiResult aResult = new ApiResult();
		try {
			List<Notice> list = noticeService.list(null);
			aResult.setData(list);
		} catch (Exception e) {
			aResult.AddError(e);
		}
		return aResult;
	}
}
