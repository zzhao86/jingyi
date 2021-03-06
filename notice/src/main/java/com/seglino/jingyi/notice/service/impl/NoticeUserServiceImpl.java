package com.seglino.jingyi.notice.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.seglino.jingyi.common.core.service.BaseServiceImpl;
import com.seglino.jingyi.common.log.annotation.ServiceLog;
import com.seglino.jingyi.common.request.RequestPageParams;
import com.seglino.jingyi.notice.dao.NoticeUserDao;
import com.seglino.jingyi.notice.dto.NoticeUserDto;
import com.seglino.jingyi.notice.pojo.NoticeUser;
import com.seglino.jingyi.notice.service.NoticeUserService;

@ServiceLog("公告接收人服务")
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
	@ServiceLog("查看公告接收人列表")
	public Page<NoticeUserDto> listForUser(RequestPageParams params) {
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
	@ServiceLog("删除公告中所有接收人")
	@Override
	public int deleteByNoticeId(String noticeId) {
		return dao.deleteByNoticeId(noticeId);
	}

}
