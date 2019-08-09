package com.seglino.jingyi.webapi.controller.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.Page;
import com.seglino.jingyi.common.request.RequestPageParams;
import com.seglino.jingyi.common.response.ApiPageResult;
import com.seglino.jingyi.common.response.ApiResult;
import com.seglino.jingyi.common.utils.AutoMapper;
import com.seglino.jingyi.notice.dto.NoticeDetailDto;
import com.seglino.jingyi.notice.dto.NoticeUserDto;
import com.seglino.jingyi.notice.pojo.Notice;
import com.seglino.jingyi.notice.service.NoticeService;
import com.seglino.jingyi.notice.service.NoticeUserService;
import com.seglino.jingyi.webapi.vo.client.notice.NoticeAttachVo;
import com.seglino.jingyi.webapi.vo.client.notice.NoticeDetailVo;
import com.seglino.jingyi.webapi.vo.client.notice.NoticeListVo;
import com.seglino.jingyi.webapi.vo.client.notice.NoticeUserListVo;

@RestController
@RequestMapping("client/notice")
public class NoticeClientController {

	@Autowired
	private NoticeService noticeService;
	@Autowired
	private NoticeUserService noticeUserService;

	@GetMapping("list")
	public ApiPageResult list(RequestPageParams params) {
		ApiPageResult aResult = new ApiPageResult();
		try {
			params.addCondition("isDeleted", false);
			Page<Notice> page = noticeService.listByUserClient(params);
			aResult.setPageCount(page.getPages());
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

	/**
	 * 告公接收人列表
	 * 
	 * @return
	 */
	@GetMapping("user_list")
	public ApiPageResult Userlist(RequestPageParams params) {
		ApiPageResult aResult = new ApiPageResult();
		Page<NoticeUserDto> page = noticeUserService.listForUser(params);
		aResult.setPageCount(page.getPages());
		aResult.setTotal(page.getTotal());
		aResult.setData(AutoMapper.mapperList(page, NoticeUserListVo.class));
		return aResult;
	}
}
