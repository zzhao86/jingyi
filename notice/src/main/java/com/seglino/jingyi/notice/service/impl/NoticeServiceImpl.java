package com.seglino.jingyi.notice.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.seglino.jingyi.common.core.service.BaseServiceImpl;
import com.seglino.jingyi.common.request.RequestPageParams;
import com.seglino.jingyi.common.utils.AutoMapper;
import com.seglino.jingyi.common.utils.DateUtils;
import com.seglino.jingyi.notice.dao.NoticeDao;
import com.seglino.jingyi.notice.dto.NoticeDetailDto;
import com.seglino.jingyi.notice.pojo.Notice;
import com.seglino.jingyi.notice.pojo.NoticeAttach;
import com.seglino.jingyi.notice.pojo.NoticeUser;
import com.seglino.jingyi.notice.service.NoticeAttachService;
import com.seglino.jingyi.notice.service.NoticeService;
import com.seglino.jingyi.notice.service.NoticeUserService;
import com.seglino.jingyi.user.pojo.User;
import com.seglino.jingyi.user.service.UserService;

@Service
public class NoticeServiceImpl extends BaseServiceImpl<NoticeDao, Notice> implements NoticeService {

	@Autowired
	private NoticeAttachService attachService;
	@Autowired
	private NoticeUserService noticeUserService;
	@Autowired
	private UserService userService;

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
		dto.setAttachList(attachList);
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
		Map<String, Object> param = new HashMap<String, Object>();
		Notice notice = AutoMapper.mapper(dto, Notice.class);
		notice.setPublishTime(DateUtils.getNow());
		// 保存提交的附件列表
		List<NoticeAttach> saveAttachList = AutoMapper.mapperList(dto.getAttachList(), NoticeAttach.class);
		if (StringUtils.isEmpty(notice.getId())) {
			insert(notice);
		} else {
			update(notice);
		}
		dto.setId(notice.getId());
		// 保存接收人
		if (!StringUtils.isEmpty(dto.getScopeJson())) {
			// 删除所有接收人，重新添加
			noticeUserService.deleteByNoticeId(notice.getId().toString());

			JSONArray array = JSONObject.parseArray(dto.getScopeJson());
			for (Object object : array) {
				JSONObject json = (JSONObject) object;
				String id = json.getString("id");
				String type = json.getString("type");
				if ("user".equals(type)) {
					// 类型是user，直接添加
					NoticeUser noticeUser = new NoticeUser();
					noticeUser.setNoticeId(notice.getId().toString());
					noticeUser.setUserId(id);
					noticeUserService.insert(noticeUser);
				} else if ("dept".equals(type)) {
					// 类型是dept，添加部门下所有的用户
					List<User> list = userService.listByDept(id, true);
					for (User user : list) {
						NoticeUser noticeUser = new NoticeUser();
						noticeUser.setNoticeId(notice.getId().toString());
						noticeUser.setUserId(user.getId().toString());
						noticeUserService.insert(noticeUser);
					}
				}
			}
		}
		// 更新公告表已读未读数量
		param.clear();
		param.put("noticeId", notice.getId());
		int totalCount = noticeUserService.count(param);
		notice.setTotalCount(totalCount);
		notice.setReadCount(0);
		update(notice);

		// 保存附件
		param.clear();
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

	/**
	 * 获取客户端首页前5条公告列表
	 * 
	 * @param userId
	 * @return
	 */
	public List<Notice> listByClientHome(String userId) {
		return dao.listByClientHome(userId);
	}

	/**
	 * 获取客户端公告列表
	 * 
	 * @param params
	 * @return
	 */
	public Page<Notice> listByUserClient(RequestPageParams params) {
		PageHelper.startPage(params.getIndex(), params.getSize());
		return dao.listByUserClient(params.getCondition());
	}
}
