package com.seglino.jingyi.notice.service;

import java.util.List;

import com.seglino.jingyi.common.core.service.BaseService;
import com.seglino.jingyi.notice.dto.NoticeDetailDto;
import com.seglino.jingyi.notice.pojo.Notice;

public interface NoticeService extends BaseService<Notice> {
	/**
	 * 获取公告详情，带附件列表
	 */
	public NoticeDetailDto detailDto(String id);

	/**
	 * 保存
	 * 
	 * @param dto
	 * @return
	 */
	public int save(NoticeDetailDto dto);

	/**
	 * 获取客户端首页前5条公告列表
	 * 
	 * @param userId
	 * @return
	 */
	public List<Notice> listByClientHome(String userId);
}
