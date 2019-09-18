package com.seglino.jingyi.dingtalk.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiDepartmentGetRequest;
import com.dingtalk.api.request.OapiDepartmentListRequest;
import com.dingtalk.api.response.OapiDepartmentGetResponse;
import com.dingtalk.api.response.OapiDepartmentListResponse;
import com.dingtalk.api.response.OapiDepartmentListResponse.Department;
import com.seglino.jingyi.common.log.annotation.ServiceLog;
import com.seglino.jingyi.dingtalk.config.DingtalkConfig;
import com.seglino.jingyi.dingtalk.service.DingtalkDeptService;
import com.seglino.jingyi.dingtalk.utils.DingtalkGlobal;
import com.seglino.jingyi.user.pojo.Dept;
import com.seglino.jingyi.user.service.DeptService;
import com.taobao.api.ApiException;

@ServiceLog("钉钉部门服务")
@Service
public class DingtalkDeptServiceImpl implements DingtalkDeptService {
	private Logger logger = LoggerFactory.getLogger(DingtalkDeptServiceImpl.class);

	@Autowired
	private DeptService deptServcie;

	/**
	 * 获取部门列表
	 * 
	 * @param pid 钉钉父部门ID
	 * @param fetchChild 是否递归部门的全部子部门
	 * @return
	 */
	@ServiceLog("获取部门列表")
	@Override
	public OapiDepartmentListResponse getDeptList(String pid, boolean fetchChild) {
		DingTalkClient client = new DefaultDingTalkClient(DingtalkConfig.GetDeptList);
		OapiDepartmentListRequest request = new OapiDepartmentListRequest();
		request.setId(pid);
		request.setFetchChild(fetchChild);
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
	 * 获取部门列表
	 * 
	 * @param pid 钉钉父部门ID
	 * @return
	 */
	@Override
	public OapiDepartmentListResponse getDeptList(String pid) {
		return getDeptList(pid, false);
	}

	/**
	 * 获取部门详情
	 * 
	 * @param id 钉钉部门ID
	 * @return
	 */
	@ServiceLog("获取部门详情")
	@Override
	public OapiDepartmentGetResponse getDeptDetail(String id) {
		DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/department/get");
		OapiDepartmentGetRequest request = new OapiDepartmentGetRequest();
		request.setId(id);
		request.setHttpMethod("GET");
		OapiDepartmentGetResponse response = null;
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
	 * 
	 * @param pid 系统父部门ID，默认一级部门为空
	 * @param ddpid 钉钉父部门ID，默认一级部门为1
	 */
	@ServiceLog("同步钉钉部门数据")
	@Override
	public void initDeptData(String pid, String ddpid) {
		if (StringUtils.isEmpty(pid) && (StringUtils.isEmpty(ddpid) || "1".equals(ddpid))) {
			OapiDepartmentGetResponse response = getDeptDetail("1");
			if (response.isSuccess()) {
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("ddDeptId", response.getId());
				Dept dept = deptServcie.detail(param);
				if (dept == null) {
					dept = new Dept();
					dept.setParentId("0");
					dept.setName(response.getName());
					dept.setSortNo(response.getOrder());
					dept.setDdDeptId(response.getId());
					dept.setDdDeptPid(response.getParentid());

					int count = deptServcie.insert(dept);
					if (count == 0) {
						logger.error("一级部门添加失败，{}", response.getBody());
					} else {
						pid = dept.getId().toString();
					}
				} else {
					pid = dept.getId().toString();
				}
			}
		}

		OapiDepartmentListResponse response = getDeptList(ddpid, false);
		if (response.isSuccess() && response.getDepartment().size() > 0) {
			List<Department> list = response.getDepartment();
			for (Department department : list) {
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("ddDeptId", department.getId());
				Dept dept = deptServcie.detail(param);
				OapiDepartmentGetResponse deptDetail = getDeptDetail(department.getId().toString());
				if (deptDetail.isSuccess()) {
					if (dept == null) {
						// 添加部门数据到数据库部门表中
						dept = new Dept();
						dept.setParentId(pid);
						dept.setName(deptDetail.getName());
						dept.setSortNo(deptDetail.getOrder());
						dept.setDdDeptId(deptDetail.getId());
						dept.setDdDeptPid(deptDetail.getParentid());

						int count = deptServcie.insert(dept);
						if (count == 0) {
							logger.error("部门添加失败，{}", response.getErrmsg());
						}
					} else {
						// 更新部门数据到数据库部门表中
						param.replace("ddDeptId", deptDetail.getParentid());
						Dept parentDept = deptServcie.detail(param);
						if (parentDept != null) {
							dept.setParentId(parentDept.getId().toString());
							dept.setName(deptDetail.getName());
							dept.setSortNo(deptDetail.getOrder());
							dept.setDdDeptId(deptDetail.getId());
							dept.setDdDeptPid(deptDetail.getParentid());

							int count = deptServcie.update(dept);
							if (count == 0) {
								logger.error("部门修改失败，{}", response.getErrmsg());
							}
						} else {
							logger.error("未找到父部门，{}", dept.getId());
						}
					}
				} else {
					logger.error("部门详情获取失败，{}", response.getErrmsg());
				}
				initDeptData(dept.getId().toString(), department.getId().toString());
			}
		}
	}
}
