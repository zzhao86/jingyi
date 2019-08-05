package com.seglino.jingyi.webapi.controller.client;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seglino.jingyi.common.response.ApiResult;
import com.seglino.jingyi.common.utils.AutoMapper;
import com.seglino.jingyi.notice.pojo.Notice;
import com.seglino.jingyi.notice.service.NoticeService;
import com.seglino.jingyi.webapi.vo.client.notice.NoticeListVo;

@RestController
@RequestMapping("client/home")
public class HomeClientController {

	@Autowired
	private NoticeService noticeService;

	@GetMapping("notice/list")
	public ApiResult noticeList(String userid) {
		ApiResult aResult = new ApiResult();
		try {
			List<Notice> list = noticeService.listByClientHome(userid);
			aResult.setData(AutoMapper.mapperList(list, NoticeListVo.class));
		} catch (Exception e) {
			aResult.addError(e);
		}
		return aResult;
	}
}
