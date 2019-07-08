package com.seglino.jingyi.dingtalk.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiUserGetAdminRequest;
import com.dingtalk.api.request.OapiUserGetDeptMemberRequest;
import com.dingtalk.api.request.OapiUserGetRequest;
import com.dingtalk.api.response.OapiDepartmentListResponse;
import com.dingtalk.api.response.OapiDepartmentListResponse.Department;
import com.dingtalk.api.response.OapiUserGetAdminResponse;
import com.dingtalk.api.response.OapiUserGetAdminResponse.AdminList;
import com.dingtalk.api.response.OapiUserGetDeptMemberResponse;
import com.dingtalk.api.response.OapiUserGetResponse;
import com.seglino.jingyi.dingtalk.config.DingtalkConfig;
import com.seglino.jingyi.dingtalk.service.DingtalkDeptService;
import com.seglino.jingyi.dingtalk.service.DingtalkUserService;
import com.seglino.jingyi.dingtalk.utils.DingtalkGlobal;
import com.seglino.jingyi.user.pojo.Dept;
import com.seglino.jingyi.user.pojo.DeptUser;
import com.seglino.jingyi.user.pojo.User;
import com.seglino.jingyi.user.service.DeptService;
import com.seglino.jingyi.user.service.DeptUserService;
import com.seglino.jingyi.user.service.UserService;
import com.taobao.api.ApiException;

@Service
public class DingtalkUserServiceImpl implements DingtalkUserService {
	private Logger logger = LoggerFactory.getLogger(DingtalkUserServiceImpl.class);

	@Autowired
	private UserService userService;

	@Autowired
	private DeptService deptService;

	@Autowired
	private DeptUserService deptUserService;

	@Autowired
	private DingtalkDeptService dingtalkDeptService;

	/**
	 * 获取部门用户userid列表
	 * 
	 * @param deptId 钉钉部门ID
	 * @return
	 */
	@Override
	public OapiUserGetDeptMemberResponse getUserIds(String deptId) {
		DingTalkClient client = new DefaultDingTalkClient(DingtalkConfig.GetUserIds);
		OapiUserGetDeptMemberRequest request = new OapiUserGetDeptMemberRequest();
		request.setDeptId(deptId);
		request.setHttpMethod("GET");
		OapiUserGetDeptMemberResponse response = null;
		try {
			response = client.execute(request, DingtalkGlobal.AccessToken);
		} catch (ApiException e) {
			logger.error("获取部门用户ID列表失败，{}", e);
			e.printStackTrace();
		}
		return response;
	}

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
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("ddUserId", userDetail.getUserid());
				User user = userService.detail(param);
				user.setType(1);
				return userService.update(user);
			}
		} catch (Exception e) {
			logger.error("初始化管理员失败：{}", e);
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 从钉钉通讯录中初始化用户数据
	 * 
	 * @param deptId 钉钉部门ID
	 * @return
	 */
	@Override
	public int initUserData(String deptId) {
		int count = 0;

		// 清空部门用户表数据
		deptUserService.deleteAll();

		// 插入根部门所有用户数据
		count += insertDeptUserData("1");

		// 获取所有子部门
		OapiDepartmentListResponse response = dingtalkDeptService.getDeptList("1", true);
		if (response.isSuccess()) {
			for (Department department : response.getDepartment()) {
				// 插入子部门所有用户数据
				count += insertDeptUserData(department.getId().toString());
			}
		} else {
			logger.error("成员添加失败，{}", response.getErrmsg());
		}
		return count;
	}

	/**
	 * 获取部门下的所有人员，并添加到数据库中
	 * 
	 * @param deptId
	 * @return
	 */
	private int insertDeptUserData(String deptId) {
		int count = 0;
		// 获取部门中所有用户ID
		OapiUserGetDeptMemberResponse userids = getUserIds(deptId);
		for (String userid : userids.getUserIds()) {
			// 获取钉钉用户详情
			OapiUserGetResponse userDetail = getUserDetail(userid);
			if (userDetail.isSuccess()) {
				// 判断用户是否已存在数据库中，若不存在则添加，若已存在则修改
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("ddUserId", userDetail.getUserid());
				User user = userService.detail(param);
				if (user == null) {
					// 添加用户数据到用户表
					user = new User();
					user.setName(userDetail.getName());
					user.setMobile(userDetail.getMobile());
					user.setTel(userDetail.getTel());
					user.setAvatar(userDetail.getAvatar());
					user.setPosition(userDetail.getPosition());
					user.setOpenid(userDetail.getOpenId());
					user.setDdUserId(userDetail.getUserid());
					if (userDetail.getIsAdmin()) {
						user.setType(2);
					} else {
						user.setType(99);
					}
					if (userService.insert(user) == 1) {
						count++;
						insertDeptUserData(deptId, user.getId().toString(), userDetail);
					} else {
						logger.error("成员数据添加失败");
					}
				} else {
					// 更新用户数据到用户表
					user.setName(userDetail.getName());
					user.setMobile(userDetail.getMobile());
					user.setTel(userDetail.getTel());
					user.setAvatar(userDetail.getAvatar());
					user.setPosition(userDetail.getPosition());
					user.setOpenid(userDetail.getOpenId());
					if (userDetail.getIsAdmin()) {
						user.setType(2);
					} else {
						user.setType(99);
					}
					if (userService.update(user) == 1) {
						count++;
						insertDeptUserData(deptId, user.getId().toString(), userDetail);
					} else {
						logger.error("成员数据更新失败");
					}
				}
			} else {
				logger.error("成员详情获取失败，{}", userDetail.getErrmsg());
			}
		}
		return count;
	}

	/**
	 * 插入用户部门数据
	 * 
	 * @param deptId
	 * @param userId
	 * @param userDetail
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private int insertDeptUserData(String deptId, String userId, OapiUserGetResponse userDetail) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("ddDeptId", deptId);
		Dept dept = deptService.detail(param);
		if (dept != null) {
			DeptUser deptUser = new DeptUser();
			deptUser.setDeptId(dept.getId().toString());
			deptUser.setUserId(userId);
			Map<Integer, Long> deptOrderMap = JSONObject.parseObject(userDetail.getOrderInDepts(), Map.class);
			deptUser.setSortNo(deptOrderMap.get(Integer.parseInt(deptId)));
			return deptUserService.insert(deptUser);
		} else {
			return 0;
		}
	}
}
