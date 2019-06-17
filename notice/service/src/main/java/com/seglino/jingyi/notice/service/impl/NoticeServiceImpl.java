package com.seglino.jingyi.notice.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.seglino.jingyi.notice.dao.NoticeDao;
import com.seglino.jingyi.notice.pojo.Notice;
import com.seglino.jingyi.notice.service.NoticeService;

@Service
public class NoticeServiceImpl /*extends BaseServiceImpl<NoticeDao, Notice>*/ implements NoticeService {

	@Autowired
	private NoticeDao dao;
	
	@Override
	public List<Notice> list(Map<String, Object> param) {
		return dao.list(param);
	}
	
}
