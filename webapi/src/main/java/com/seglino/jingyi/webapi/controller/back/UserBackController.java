package com.seglino.jingyi.webapi.controller.back;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.Page;
import com.seglino.jingyi.common.request.RequestListParams;
import com.seglino.jingyi.common.response.ApiPageResult;
import com.seglino.jingyi.common.response.ApiResult;
import com.seglino.jingyi.common.utils.AutoMapper;
import com.seglino.jingyi.dingtalk.service.DingtalkUserService;
import com.seglino.jingyi.user.dto.UserDetailDto;
import com.seglino.jingyi.user.pojo.User;
import com.seglino.jingyi.user.service.UserService;
import com.seglino.jingyi.webapi.vo.back.user.UserDetailVo;
import com.seglino.jingyi.webapi.vo.back.user.UserListVo;

@RestController
@RequestMapping("back/user")
public class UserBackController {

	@Autowired
	private UserService userService;
	@Autowired
	private DingtalkUserService dingtalkUserService;

	/**
	 * 获取用户列表
	 * 
	 * @param params
	 * @return
	 */
	@GetMapping("list")
	public ApiPageResult list(RequestListParams params) {
		ApiPageResult aResult = new ApiPageResult();
		try {
			Page<User> list = userService.page(params);
			aResult.setTotal(list.getTotal());
			aResult.setData(AutoMapper.mapperList(list, UserListVo.class));
		} catch (Exception e) {
			aResult.addError(e);
		}
		return aResult;
	}

	/**
	 * 获取用户详情
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("detail")
	public ApiResult detail(String id) {
		ApiResult aResult = new ApiResult();
		try {
			Map<String, Object> param =new HashMap<String, Object>();
			param.put("id", id);
			UserDetailDto user = userService.detailForDept(param);
			aResult.setData(AutoMapper.mapper(user, UserDetailVo.class));
		} catch (Exception e) {
			aResult.addError(e);
		}
		return aResult;
	}

	/**
	 * 从钉钉同步用户数据
	 * 
	 * @param deptId 钉钉部门ID
	 * @return
	 */
	@GetMapping("init_dd")
	public ApiResult initUserData(String deptId) {
		ApiResult aResult = new ApiResult();
		try {
			int count = dingtalkUserService.initUserData(deptId);
			aResult.setData("同步完成，共同步用户" + count + "人");
			dingtalkUserService.initAdmin();
		} catch (Exception e) {
			aResult.addError(e);
		}
		return aResult;
	}
}
