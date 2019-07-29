package com.seglino.jingyi.notice.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.seglino.jingyi.common.core.service.BaseServiceImpl;
import com.seglino.jingyi.common.request.RequestListParams;
import com.seglino.jingyi.notice.dao.NoticeUserDao;
import com.seglino.jingyi.notice.dto.NoticeUserDto;
import com.seglino.jingyi.notice.pojo.NoticeUser;
import com.seglino.jingyi.notice.service.NoticeUserService;

@Service
public class NoticeUserServiceImpl extends BaseServiceImpl<NoticeUserDao, NoticeUser> implements NoticeUserService {

	@Override
	public int insert(NoticeUser entity) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("noticeId", entity.getNoticeId());
		param.put("userId", entity.getUserId());
		NoticeUser user = this.detail(param);
		if (null != user) {
			return 0;
		} else {
			return super.insert(entity);
		}
	}

	/**
	 * 获取公告接收用户列表
	 * 
	 * @param params
	 * @return
	 */
	public Page<NoticeUserDto> listForUser(RequestListParams params) {
		PageHelper.startPage(params.getIndex(), params.getSize());
		Map<String, Object> param = params.getCondition();
		return dao.listForUser(param);
	}

	/**
	 * 删除公告中所有的接收人
	 * 
	 * @param noticeId 公告ID
	 * @return
	 */
	@Override
	public int deleteAll(String noticeId) {
		return dao.deleteAll(noticeId);
	}

}
