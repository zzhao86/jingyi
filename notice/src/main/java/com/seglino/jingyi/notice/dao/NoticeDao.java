package com.seglino.jingyi.notice.dao;

import org.apache.ibatis.annotations.Mapper;

import com.seglino.jingyi.common.core.dao.BaseDao;
import com.seglino.jingyi.notice.pojo.Notice;

@Mapper
public interface NoticeDao extends BaseDao<Notice> {

}
