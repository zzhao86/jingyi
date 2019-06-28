package com.seglino.jingyi.dingtalk.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiDepartmentListRequest;
import com.dingtalk.api.response.OapiDepartmentListResponse;
import com.dingtalk.api.response.OapiDepartmentListResponse.Department;
import com.seglino.jingyi.dingtalk.config.DingtalkConfig;
import com.seglino.jingyi.dingtalk.service.DingtalkDeptService;
import com.seglino.jingyi.dingtalk.utils.DingtalkGlobal;
import com.seglino.jingyi.user.pojo.Dept;
import com.taobao.api.ApiException;

public class DingtalkDeptServiceImpl implements DingtalkDeptService {
	private Logger logger = LoggerFactory.getLogger(DingtalkDeptServiceImpl.class);
	
	/**
	 * 获取部门列表
	 * 
	 * @param pid 钉钉父部门ID
	 * @return
	 */
	@Override
	public OapiDepartmentListResponse getDeptList(String pid) {
		DingTalkClient client = new DefaultDingTalkClient(DingtalkConfig.GetDeptList);
		OapiDepartmentListRequest request = new OapiDepartmentListRequest();
		request.setId(pid);
		request.setHttpMethod("GET");
		OapiDepartmentListResponse response = null;
		try {
			response = client.execute(request, DingtalkGlobal.AccessToken);
		} catch (ApiException e) {
			logger.error("{}", e);
			e.printStackTrace();
		}
		return response;
	}

	/**
	 * 从钉钉组织架构中初始化部门数据到系统中
	 */
	public void initDeptData() {
		OapiDepartmentListResponse response = getDeptList("1");
		if(response.isSuccess()) {
			List<Department> list = response.getDepartment();
			for (Department department : list) {
				Dept dept = new Dept();
				dept.setName(department.getName());
				dept.setDdDeptId(department.getId());
				dept.setDdDeptPid(department.getParentid());
			}
		}
	}
}
