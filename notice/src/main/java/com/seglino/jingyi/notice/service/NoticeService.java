package com.seglino.jingyi.notice.service;

import java.util.List;

import com.github.pagehelper.Page;
import com.seglino.jingyi.common.core.service.BaseService;
import com.seglino.jingyi.common.request.RequestPageParams;
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

	/**
	 * 获取客户端公告列表
	 * 
	 * @param params
	 * @return
	 */
	public Page<Notice> listByUserClient(RequestPageParams params);

	/**
	 * 读取公告，将公告的状态设为已读
	 * 
	 * @param id 公告ID
	 * @param userid 接收人ID
	 * @return
	 */
	public int read(String id, String userid);
}
