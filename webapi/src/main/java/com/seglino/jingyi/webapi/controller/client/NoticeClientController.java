package com.seglino.jingyi.webapi.controller.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.Page;
import com.seglino.jingyi.common.request.RequestListParams;
import com.seglino.jingyi.common.response.ApiPageResult;
import com.seglino.jingyi.common.response.ApiResult;
import com.seglino.jingyi.common.utils.AutoMapper;
import com.seglino.jingyi.notice.dto.NoticeDetailDto;
import com.seglino.jingyi.notice.pojo.Notice;
import com.seglino.jingyi.notice.service.NoticeService;
import com.seglino.jingyi.webapi.vo.back.notice.NoticeAttachVo;
import com.seglino.jingyi.webapi.vo.back.notice.NoticeDetailVo;
import com.seglino.jingyi.webapi.vo.back.notice.NoticeListVo;

@RestController
@RequestMapping("client/notice")
public class NoticeClientController {

	@Autowired
	private NoticeService noticeService;

	@GetMapping("list")
	public ApiPageResult list(RequestListParams params) {
		ApiPageResult aResult = new ApiPageResult();
		try {
			params.addCondition("isDeleted", false);
			Page<Notice> page = noticeService.page(params);
			aResult.setTotal(page.getTotal());
			aResult.setData(AutoMapper.mapperList(page, NoticeListVo.class));
		} catch (Exception e) {
			aResult.addError(e);
		}
		return aResult;
	}

	@GetMapping("detail")
	public ApiResult detail(String id) {
		ApiResult aResult = new ApiResult();
		try {
			NoticeDetailDto notice = noticeService.detailDto(id);
			NoticeDetailVo vo = AutoMapper.mapper(notice, NoticeDetailVo.class);
			vo.setAttachList(AutoMapper.mapperList(notice.getAttachList(), NoticeAttachVo.class));
			aResult.setData(vo);
		} catch (Exception e) {
			aResult.addError(e);
		}
		return aResult;
	}
}
