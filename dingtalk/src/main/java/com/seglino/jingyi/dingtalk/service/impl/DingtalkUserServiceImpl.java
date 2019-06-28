package com.seglino.jingyi.dingtalk.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiUserGetAdminRequest;
import com.dingtalk.api.request.OapiUserGetRequest;
import com.dingtalk.api.response.OapiUserGetAdminResponse;
import com.dingtalk.api.response.OapiUserGetAdminResponse.AdminList;
import com.dingtalk.api.response.OapiUserGetResponse;
import com.seglino.jingyi.dingtalk.config.DingtalkConfig;
import com.seglino.jingyi.dingtalk.service.DingtalkUserService;
import com.seglino.jingyi.dingtalk.utils.DingtalkGlobal;
import com.seglino.jingyi.user.pojo.User;
import com.seglino.jingyi.user.service.UserService;
import com.taobao.api.ApiException;

@Service
public class DingtalkUserServiceImpl implements DingtalkUserService {
	private Logger logger = LoggerFactory.getLogger(DingtalkUserServiceImpl.class);

	@Autowired
	private UserService userService;

	/**
	 * 获取用户详情
	 * 
	 * @param userid 钉钉用户ID
	 * @return
	 */
	@Override
	public OapiUserGetResponse getUserDetail(String userid) {
		DingTalkClient client = new DefaultDingTalkClient(DingtalkConfig.GetUserDetail);
		OapiUserGetRequest request = new OapiUserGetRequest();
		request.setUserid(userid);
		request.setHttpMethod("GET");
		OapiUserGetResponse response = null;
		try {
			response = client.execute(request, DingtalkGlobal.AccessToken);
		} catch (ApiException e) {
			logger.error("获取用户详情失败：{}", e);
			e.printStackTrace();
		}
		return response;
	}

	/**
	 * 获取管理员列表
	 * 
	 * @return
	 */
	@Override
	public OapiUserGetAdminResponse getAdminList() {
		DingTalkClient client = new DefaultDingTalkClient(DingtalkConfig.GetAdminList);
		OapiUserGetAdminRequest request = new OapiUserGetAdminRequest();
		request.setHttpMethod("GET");

		OapiUserGetAdminResponse response = null;
		try {
			response = client.execute(request, DingtalkGlobal.AccessToken);
		} catch (ApiException e) {
			logger.error("获取管理员列表失败：{}", e);
			e.printStackTrace();
		}
		return response;
	}

	/**
	 * 将钉钉管理员初始化为系统管理员
	 * 
	 * @param userid 钉钉用户ID
	 * @return
	 */
	@Override
	public int initAdmin() {
		try {
			OapiUserGetAdminResponse adminResponse = getAdminList();
			if (adminResponse.isSuccess()) {
				List<AdminList> adminList = adminResponse.getAdminList();
				String adminUserid = null;
				for (AdminList admin : adminList) {
					// 找到主管理员
					if (admin.getSysLevel().intValue() == 1) {
						adminUserid = admin.getUserid();
						break;
					}
				}
				OapiUserGetResponse userDetail = getUserDetail(adminUserid);
				User user = new User();
				user.setName(userDetail.getName());
				user.setMobile(userDetail.getMobile());
				user.setTel(userDetail.getTel());
				user.setAvatar(userDetail.getAvatar());
				user.setPosition(userDetail.getPosition());
				user.setDdUserId(userDetail.getUserid());
				user.setOpenid(userDetail.getOpenId());
				user.setType(1);

				return userService.insert(user);
			}
		} catch (Exception e) {
			logger.error("初始化管理员失败：{}", e);
			e.printStackTrace();
		}
		return 0;
	}

}
