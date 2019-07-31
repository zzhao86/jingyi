package com.seglino.jingyi.webapi.controller.back;

import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dingtalk.api.response.OapiMessageCorpconversationAsyncsendV2Response;
import com.github.pagehelper.Page;
import com.seglino.jingyi.common.request.RequestListParams;
import com.seglino.jingyi.common.response.ApiPageResult;
import com.seglino.jingyi.common.response.ApiResult;
import com.seglino.jingyi.common.utils.AutoMapper;
import com.seglino.jingyi.common.utils.DateUtils;
import com.seglino.jingyi.dingtalk.dto.DingtalkWorkMessageDto;
import com.seglino.jingyi.dingtalk.dto.MessageType;
import com.seglino.jingyi.dingtalk.service.DingtalkMessageService;
import com.seglino.jingyi.file.dto.FileType;
import com.seglino.jingyi.file.pojo.Files;
import com.seglino.jingyi.file.service.FilesService;
import com.seglino.jingyi.notice.dto.NoticeDetailDto;
import com.seglino.jingyi.notice.dto.NoticeUserDto;
import com.seglino.jingyi.notice.pojo.Notice;
import com.seglino.jingyi.notice.pojo.NoticeAttach;
import com.seglino.jingyi.notice.service.NoticeService;
import com.seglino.jingyi.notice.service.NoticeUserService;
import com.seglino.jingyi.webapi.vo.FilesVo;
import com.seglino.jingyi.webapi.vo.back.notice.NoticeAttachVo;
import com.seglino.jingyi.webapi.vo.back.notice.NoticeDetailVo;
import com.seglino.jingyi.webapi.vo.back.notice.NoticeListVo;
import com.seglino.jingyi.webapi.vo.back.notice.NoticeUserListVo;

@RestController
@RequestMapping("back/notice")
public class NoticeBackController {

	@Autowired
	private NoticeService noticeService;
	@Autowired
	private FilesService filesService;
	@Autowired
	private NoticeUserService noticeUserService;
	@Autowired
	private DingtalkMessageService dingtalkMessageService;

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
			vo.setAttachList(AutoMapper.mapperList(notice.getAttacheList(), NoticeAttachVo.class));
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
	public ApiPageResult Userlist(RequestListParams params) {
		ApiPageResult aResult = new ApiPageResult();
		Page<NoticeUserDto> page = noticeUserService.listForUser(params);
		aResult.setTotal(page.getTotal());
		aResult.setData(AutoMapper.mapperList(page, NoticeUserListVo.class));
		return aResult;
	}

	@PostMapping("save")
	public ApiResult save(@RequestBody NoticeDetailVo vo) {
		ApiResult aResult = new ApiResult();
		try {
			NoticeDetailDto dto = AutoMapper.mapper(vo, NoticeDetailDto.class);
			dto.setAttacheList(AutoMapper.mapperList(vo.getAttachList(), NoticeAttach.class));
			int count = noticeService.save(dto);
			if (count == 0) {
				aResult.addError("保存失败");
			}
		} catch (Exception e) {
			aResult.addError(e);
		}
		return aResult;
	}

	@PostMapping("upload_cover")
	public ApiResult uploadCover(HttpServletRequest request) {
		ApiResult aResult = new ApiResult();
		try {
			List<Files> list = filesService.upload(request, FileType.IMAGE);
			aResult.setData(list);
		} catch (Exception e) {
			aResult.addError(e);
		}
		return aResult;
	}

	@PostMapping("upload_attach")
	public ApiResult uploadAttach(HttpServletRequest request) {
		ApiResult aResult = new ApiResult();
		try {
			List<Files> list = filesService.upload(request, FileType.FILE);
			aResult.setData(AutoMapper.mapperList(list, FilesVo.class));
		} catch (Exception e) {
			aResult.addError(e);
		}
		return aResult;
	}

	@GetMapping("delete")
	public ApiResult delete(String id) {
		ApiResult aResult = new ApiResult();
		try {
			noticeService.delete(id);
		} catch (Exception e) {
			aResult.addError(e);
		}
		return aResult;
	}

	@PostMapping("delete_batch")
	public ApiResult deleteBatch(@RequestBody List<Object> ids) {
		ApiResult aResult = new ApiResult();
		try {
			noticeService.deleteBatch(ids);
		} catch (Exception e) {
			aResult.addError(e);
		}
		return aResult;
	}

	@GetMapping("test")
	public ApiResult sendmessage() {
		ApiResult aResult = new ApiResult();
		try {
			String id = "e63bb95f8d9f11e9a79000ffeb657a63";
			String baseUrl = "http://192.168.0.8:5050";
			Notice notice = noticeService.detailById(id);
			String text = "";
			if (StringUtils.isEmpty(notice.getAuthor())) {
				text = DateUtils.toString(notice.getPublishTime(), "MM月dd日 HH:ss");
			} else {
				text = notice.getAuthor() + "　" + DateUtils.toString(notice.getPublishTime(), "MM月dd日 HH:ss");
			}
			// 获取公告接收人列表，并组合成字符串
			RequestListParams params = new RequestListParams();
			params.setIndex(1);
			params.setSize(9999999);
			params.addCondition("noticeId", id);
			params.addCondition("isRead", false);
			params.addCondition("isDeleted", false);
			List<NoticeUserDto> noticeUserList = noticeUserService.listForUser(params);
			StringBuilder sb = new StringBuilder();
			for (NoticeUserDto noticeUser : noticeUserList) {
				sb.append(noticeUser.getDdUserId());
				sb.append(",");
			}
			if (sb.length() > 0) {
				sb.deleteCharAt(sb.lastIndexOf(","));
			}

			DingtalkWorkMessageDto.Link link = new DingtalkWorkMessageDto.Link();
			link.setMessageUrl("dingtalk://dingtalkclient/page/link?url=" + URLEncoder.encode(baseUrl, "UTF-8") + "&pc_slide=true");
			link.setPicUrl(baseUrl + notice.getCoverUrl());
			link.setTitle(notice.getTitle());
			link.setText(text);

			DingtalkWorkMessageDto dto = new DingtalkWorkMessageDto();
			dto.setUseridList(sb.toString());
			dto.setMsgType(MessageType.LINK);
			dto.setLink(link);

			OapiMessageCorpconversationAsyncsendV2Response response = dingtalkMessageService.sendWorkMessage(dto);
			aResult.setData(response);
		} catch (Exception e) {
			aResult.addError(e);
		}
		return aResult;
	}
}