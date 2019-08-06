package com.seglino.jingyi.webapi.controller.back;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.Page;
import com.seglino.jingyi.common.request.RequestPageParams;
import com.seglino.jingyi.common.response.ApiPageResult;
import com.seglino.jingyi.common.response.ApiResult;
import com.seglino.jingyi.common.utils.AutoMapper;
import com.seglino.jingyi.dingtalk.service.DingtalkUserService;
import com.seglino.jingyi.user.dto.UserDetailDto;
import com.seglino.jingyi.user.pojo.Dept;
import com.seglino.jingyi.user.pojo.User;
import com.seglino.jingyi.user.service.DeptService;
import com.seglino.jingyi.user.service.UserService;
import com.seglino.jingyi.webapi.vo.back.user.UserDetailVo;
import com.seglino.jingyi.webapi.vo.back.user.UserListVo;
import com.seglino.jingyi.webapi.vo.back.user.UserChooseVo;

@RestController
@RequestMapping("back/user")
public class UserBackController {
	@Autowired
	private UserService userService;
	@Autowired
	private DeptService deptservice;
	@Autowired
	private DingtalkUserService dingtalkUserService;

	/**
	 * 获取用户列表
	 * 
	 * @param params
	 * @return
	 */
	@GetMapping("list")
	public ApiPageResult list(RequestPageParams params) {
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
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("id", id);
			UserDetailDto user = userService.detailForDept(param);
			aResult.setData(AutoMapper.mapper(user, UserDetailVo.class));
		} catch (Exception e) {
			aResult.addError(e);
		}
		return aResult;
	}

	/**
	 * 获取部门下的子部门和成员
	 * 
	 * @param deptId
	 * @return
	 */
	@GetMapping("choose")
	public ApiResult choose(String deptId, String keywords) {
		ApiResult aResult = new ApiResult();
		try {
			List<UserChooseVo> list = new ArrayList<UserChooseVo>();
			Map<String, Object> param = new HashMap<String, Object>();
			if (StringUtils.isEmpty(keywords)) {
				param.put("name", keywords);
				param.put("parentId", deptId);
				param.put("isDeleted", false);
				List<Dept> deptList = deptservice.list(param);
				for (Dept dept : deptList) {
					UserChooseVo vo = new UserChooseVo();
					vo.setId(dept.getId().toString());
					vo.setName(dept.getName());
					vo.setType("dept");
					list.add(vo);
				}
			}
			param.clear();
			param.put("name", keywords);
			if (StringUtils.isEmpty(keywords)) {
				param.put("deptId", deptId);
			}
			param.put("isDeleted", false);
			List<User> userList = userService.list(param);

			for (User user : userList) {
				UserChooseVo vo = new UserChooseVo();
				vo.setId(user.getId().toString());
				vo.setName(user.getName());
				vo.setAvatar(user.getAvatar());
				vo.setType("user");
				list.add(vo);
			}
			aResult.setData(list);
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
