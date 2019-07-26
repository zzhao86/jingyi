package com.seglino.jingyi.notice.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.seglino.jingyi.common.core.service.BaseServiceImpl;
import com.seglino.jingyi.common.utils.AutoMapper;
import com.seglino.jingyi.notice.dao.NoticeDao;
import com.seglino.jingyi.notice.dto.NoticeDetailDto;
import com.seglino.jingyi.notice.pojo.Notice;
import com.seglino.jingyi.notice.pojo.NoticeAttach;
import com.seglino.jingyi.notice.service.NoticeAttachService;
import com.seglino.jingyi.notice.service.NoticeService;

@Service
public class NoticeServiceImpl extends BaseServiceImpl<NoticeDao, Notice> implements NoticeService {

	@Autowired
	private NoticeAttachService attachService;

	/**
	 * 获取公告详情，带附件列表
	 */
	@Override
	public NoticeDetailDto detailDto(String id) {
		Notice notice = this.detailById(id);
		NoticeDetailDto dto = AutoMapper.mapper(notice, NoticeDetailDto.class);
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("noticeId", id);
		List<NoticeAttach> attachList = attachService.list(param);
		dto.setAttacheList(attachList);
		return dto;
	}

	/**
	 * 保存
	 * 
	 * @param dto
	 * @return
	 */
	public int save(NoticeDetailDto dto) {
		if (null == dto) {
			return 0;
		}
		Notice notice = AutoMapper.mapper(dto, Notice.class);
		// 保存提交的附件列表
		List<NoticeAttach> saveAttachList = AutoMapper.mapperList(dto.getAttacheList(), NoticeAttach.class);
		if (StringUtils.isEmpty(notice.getId())) {
			insert(notice);
		} else {
			update(notice);
		}
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("noticeId", notice.getId());
		List<NoticeAttach> oldAttacheList = attachService.list(param);
		// 删除不在保存列表中的附件
		for (NoticeAttach noticeAttach : oldAttacheList) {
			boolean exists = false;
			for (NoticeAttach saveAttach : saveAttachList) {
				if (noticeAttach.getId().equals(saveAttach.getId())) {
					exists = true;
					break;
				}
			}
			if (!exists) {
				attachService.deletePhysical(noticeAttach.getId());
			}
		}
		// 把Id是空的附件添加到数据库
		for (NoticeAttach saveAttach : saveAttachList) {
			if (null == saveAttach.getId()) {
				saveAttach.setNoticeId(notice.getId().toString());
				attachService.insert(saveAttach);
			}
		}
		return 1;
	}
}
