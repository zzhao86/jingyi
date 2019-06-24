package com.seglino.jingyi.notice.service;

import java.util.List;
import java.util.Map;

import com.seglino.jingyi.notice.pojo.Notice;

public interface NoticeService /*extends BaseService<Notice>*/ {
	public List<Notice> list(Map<String, Object> param);
}
