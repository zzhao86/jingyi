package com.seglino.jingyi.notice.service.impl;

import org.springframework.stereotype.Service;

import com.seglino.jingyi.common.core.service.BaseServiceImpl;
import com.seglino.jingyi.notice.dao.NoticeDao;
import com.seglino.jingyi.notice.pojo.Notice;
import com.seglino.jingyi.notice.service.NoticeService;

@Service
public class NoticeServiceImpl extends BaseServiceImpl<NoticeDao, Notice> implements NoticeService {

}
