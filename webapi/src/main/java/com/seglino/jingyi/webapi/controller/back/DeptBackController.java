package com.seglino.jingyi.webapi.controller.back;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.seglino.jingyi.common.response.ApiResult;
import com.seglino.jingyi.dingtalk.service.DingtalkDeptService;
import com.seglino.jingyi.user.dto.DeptTreeDto;
import com.seglino.jingyi.user.pojo.Dept;
import com.seglino.jingyi.user.service.DeptService;

@RestController
@RequestMapping("back/dept")
public class DeptBackController {

	@Autowired
	private DeptService deptService;
	
	@Autowired
	private DingtalkDeptService dingtalkDeptService;

	@GetMapping("list")
	public ApiResult list(@RequestParam Map<String, Object> param) {
		ApiResult aResult = new ApiResult();
		try {
			List<Dept> list = deptService.list(param);
			aResult.setData(list);
		} catch (Exception e) {
			aResult.AddError(e);
		}
		return aResult;
	}

	@GetMapping("tree")
	public ApiResult tree(String pid) {
		ApiResult aResult = new ApiResult();
		try {
			Map<String, Object> param =new HashMap<String, Object>();
			param.put("parentId", pid);
			List<DeptTreeDto> list = deptService.tree(param);
			aResult.setData(list);
		} catch (Exception e) {
			aResult.AddError(e);
		}
		return aResult;
	}
	
	@GetMapping("init_dd")
	public ApiResult initFromDingtalk(String id) {
		ApiResult aResult = new ApiResult();
		try {
			dingtalkDeptService.initDeptData(null, id);
		} catch (Exception e) {
			aResult.AddError(e);
		}
		return aResult;
	}
}