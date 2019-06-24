package com.seglino.jingyi.notice.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.seglino.jingyi.notice.pojo.Notice;

@Mapper
public interface NoticeDao /*extends BaseDao<Notice>*/ {
	public List<Notice> list(Map<String, Object> param);
}
