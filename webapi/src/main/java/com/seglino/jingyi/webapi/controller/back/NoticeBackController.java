package com.seglino.jingyi.webapi.controller.back;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authc.DisabledAccountException;
import org.apache.tomcat.util.bcel.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dingtalk.api.response.OapiMessageCorpconversationAsyncsendV2Response;
import com.github.pagehelper.Page;
import com.seglino.jingyi.common.request.RequestPageParams;
import com.seglino.jingyi.common.response.ApiPageResult;
import com.seglino.jingyi.common.response.ApiResult;
import com.seglino.jingyi.common.utils.ApplicationUtils;
import com.seglino.jingyi.common.utils.AutoMapper;
import com.seglino.jingyi.common.utils.DateUtils;
import com.seglino.jingyi.dingtalk.dto.DingtalkWorkMessageDto;
import com.seglino.jingyi.dingtalk.dto.MessageType;
import com.seglino.jingyi.dingtalk.service.DingtalkMessageService;
import com.seglino.jingyi.file.dto.FileType;
import com.seglino.jingyi.file.pojo.Files;
import com.seglino.jingyi.file.service.FilesService;
import com.seglino.jingyi.notice.dto.NoticeDetailDto;
import com.seglino.jingyi.notice.dto.NoticeReplyDto;
import com.seglino.jingyi.notice.dto.NoticeUserDto;
import com.seglino.jingyi.notice.pojo.Notice;
import com.seglino.jingyi.notice.pojo.NoticeAttach;
import com.seglino.jingyi.notice.pojo.NoticeReply;
import com.seglino.jingyi.notice.service.NoticeReplyService;
import com.seglino.jingyi.notice.service.NoticeService;
import com.seglino.jingyi.notice.service.NoticeUserService;
import com.seglino.jingyi.webapi.vo.FilesVo;
import com.seglino.jingyi.webapi.vo.back.notice.NoticeAttachVo;
import com.seglino.jingyi.webapi.vo.back.notice.NoticeDetailVo;
import com.seglino.jingyi.webapi.vo.back.notice.NoticeListVo;
import com.seglino.jingyi.webapi.vo.back.notice.NoticeReplyListVo;
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
	private NoticeReplyService noticeReplyService;
	@Autowired
	private DingtalkMessageService dingtalkMessageService;

	/**
	 * 获取分页列表
	 * 
	 * @param params
	 * @return
	 */
	@GetMapping("list")
	public ApiPageResult list(RequestPageParams params) {
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

	/**
	 * 获取详情
	 * 
	 * @param id
	 * @return
	 */
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
		aResult.setTotal(page.getTotal());
		aResult.setData(AutoMapper.mapperList(page, NoticeUserListVo.class));
		return aResult;
	}

	/**
	 * 保存
	 * 
	 * @param vo
	 * @return
	 */
	@PostMapping("save")
	public ApiResult save(@RequestBody NoticeDetailVo vo) {
		ApiResult aResult = new ApiResult();
		try {
			NoticeDetailDto dto = AutoMapper.mapper(vo, NoticeDetailDto.class);
			dto.setAttachList(AutoMapper.mapperList(vo.getAttachList(), NoticeAttach.class));
			int count = noticeService.save(dto);
			if (count == 0) {
				aResult.addError("保存失败");
			} else {
				// 保存成功之后，给所有接收人发送钉钉工作通知
				// sendDingtalkWorkMessage(dto.getId().toString());
			}
		} catch (Exception e) {
			aResult.addError(e);
		}
		return aResult;
	}

	/**
	 * 上传封面
	 * 
	 * @param request
	 * @return
	 */
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

	/**
	 * 上传附件
	 * 
	 * @param request
	 * @return
	 */
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

	/**
	 * 删除
	 * 
	 * @param id
	 * @return
	 */
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

	/**
	 * 批量删除
	 * 
	 * @param ids
	 * @return
	 */
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

	@GetMapping("reply_list")
	public ApiPageResult replyList(RequestPageParams params) {
		ApiPageResult aResult = new ApiPageResult();
		try {
			Page<NoticeReplyDto> pages = noticeReplyService.listForPage(params);
			aResult.setPageCount(pages.getPages());
			aResult.setTotal(pages.getTotal());
			aResult.setData(AutoMapper.mapperList(pages.getResult(), NoticeReplyListVo.class));
		} catch (Exception e) {
			aResult.addError(e);
		}
		return aResult;
	}

	/**
	 * 下载回复的文件
	 * 
	 * @param id
	 * @param response
	 */
	@GetMapping("download")
	public void download(String id, HttpServletResponse response) {
		if (StringUtils.isEmpty(id)) {
			return;
		}
		NoticeReply reply = noticeReplyService.detailById(id);
		if (null != reply) {
			String zipFileName = reply.getFileName().substring(0, reply.getFileName().lastIndexOf(".")) + ".zip";
			Map<String, String> fileMap = new HashMap<String, String>();
			fileMap.put(reply.getFileName(), reply.getFileUrl());
			filesService.downloadZip(fileMap, zipFileName, response);
		}
	}

	/**
	 * 批量下载回复的文件
	 * 
	 * @param id
	 * @param response
	 */
	@GetMapping("downloads")
	public void downloads(String ids, HttpServletResponse response) {
		if (StringUtils.isEmpty(ids)) {
			return;
		}
		String[] array = ids.split(",");
		if (null != array && array.length > 0) {
			Map<String, String> fileMap = new HashMap<String, String>();
			String zipFileName = "公告回复附件." + DateUtils.getNowString("yyy.MM.dd.HH.mm") + ".zip";
			int index = 0;
			for (int i = 0; i < array.length; i++) {
				String id = array[i];
				NoticeReply reply = noticeReplyService.detailById(id);
				if (fileMap.containsKey(reply.getFileName())) {
					String name = reply.getFileName().substring(0, reply.getFileName().lastIndexOf("."));
					String ext = reply.getFileName().substring(reply.getFileName().lastIndexOf("."));
					fileMap.put(name + "(" + (++index) + ")" + ext, reply.getFileUrl());
				} else {
					fileMap.put(reply.getFileName(), reply.getFileUrl());
				}
			}
			if (fileMap.size() > 0) {
				filesService.downloadZip(fileMap, zipFileName, response);
			}
		}
	}

	/**
	 * 在线预览office文档
	 * 
	 * @param path
	 * @return
	 */
	@GetMapping("viewer")
	public ApiResult viewer(String path) {
		ApiResult aResult = new ApiResult();
		try {
			aResult.setData(filesService.toPDF(path));
		} catch (Exception e) {
			aResult.addError(e);
		}
		return aResult;
	}

	/**
	 * 给公告接收人发送钉钉工作消息
	 * 
	 * @param notice
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private boolean sendDingtalkWorkMessage(String id) throws UnsupportedEncodingException {
		String baseUrl = "http://192.168.0.8:5050";
		String text = "";
		Notice notice = noticeService.detailById(id);
		if (StringUtils.isEmpty(notice.getAuthor())) {
			text = DateUtils.toString(notice.getPublishTime(), "MM月dd日 HH:ss");
		} else {
			text = notice.getAuthor() + "　" + DateUtils.toString(notice.getPublishTime(), "MM月dd日 HH:mm");
		}
		// 获取公告接收人列表，并组合成字符串
		RequestPageParams params = new RequestPageParams();
		params.setIndex(1);
		params.setSize(9999999);
		params.addCondition("noticeId", notice.getId());
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
		return response.isSuccess();
	}
}