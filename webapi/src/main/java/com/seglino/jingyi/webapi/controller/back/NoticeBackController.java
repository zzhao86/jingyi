package com.seglino.jingyi.webapi.controller.back;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.Page;
import com.seglino.jingyi.common.request.RequestListParams;
import com.seglino.jingyi.common.response.ApiPageResult;
import com.seglino.jingyi.common.response.ApiResult;
import com.seglino.jingyi.common.utils.AutoMapper;
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

	@GetMapping("list")
	public ApiPageResult list(RequestListParams params) {
		ApiPageResult aResult = new ApiPageResult();
		try {
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
			if(count == 0) {
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
}