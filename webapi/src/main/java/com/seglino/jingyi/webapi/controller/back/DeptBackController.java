package com.seglino.jingyi.webapi.controller.back;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.seglino.jingyi.common.log.annotation.ControllerLog;
import com.seglino.jingyi.common.response.ApiResult;
import com.seglino.jingyi.dingtalk.service.DingtalkDeptService;
import com.seglino.jingyi.user.dto.DeptTreeDto;
import com.seglino.jingyi.user.pojo.Dept;
import com.seglino.jingyi.user.service.DeptService;

@ControllerLog("部门管理")
@RestController
@RequestMapping("back/dept")
public class DeptBackController {

	@Autowired
	private DeptService deptService;

	@Autowired
	private DingtalkDeptService dingtalkDeptService;

	@ControllerLog("查看部门列表")
	@GetMapping("list")
	public ApiResult list(@RequestParam Map<String, Object> param) {
		ApiResult aResult = new ApiResult();
		try {
			List<Dept> list = deptService.list(param);
			aResult.setData(list);
		} catch (Exception e) {
			aResult.addError(e);
		}
		return aResult;
	}

	@GetMapping("root")
	public ApiResult rootDept() {
		ApiResult aResult = new ApiResult();
		try {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("parentId", "0");
			Dept dept = deptService.detail(param);
			aResult.setData(dept);
		} catch (Exception e) {
			aResult.addError(e);
		}
		return aResult;
	}

	@GetMapping("tree")
	public ApiResult tree() {
		ApiResult aResult = new ApiResult();
		try {
			List<DeptTreeDto> list = deptService.tree();
			aResult.setData(list);
		} catch (Exception e) {
			aResult.addError(e);
		}
		return aResult;
	}

	@GetMapping("parent_list")
	public ApiResult parentsDept(String id) {
		ApiResult aResult = new ApiResult();
		try {
			List<Dept> list = new ArrayList<Dept>();
			Dept dept = deptService.detailById(id);
			list.add(dept);
			while (!"0".equals(dept.getParentId())) {
				dept = deptService.detailById(dept.getParentId());
				list.add(dept);
			}
			Collections.reverse(list);
			aResult.setData(list);
		} catch (Exception e) {
			aResult.addError(e);
		}
		return aResult;
	}

	@ControllerLog("同步钉钉部门数据")
	@GetMapping("init_dd")
	public ApiResult initFromDingtalk(String id) {
		ApiResult aResult = new ApiResult();
		try {
			dingtalkDeptService.initDeptData(null, id);
		} catch (Exception e) {
			aResult.addError(e);
		}
		return aResult;
	}
}
