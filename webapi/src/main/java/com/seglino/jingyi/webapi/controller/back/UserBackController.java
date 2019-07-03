package com.seglino.jingyi.webapi.controller.back;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.Page;
import com.seglino.jingyi.common.request.PageParams;
import com.seglino.jingyi.common.response.ApiPageResult;
import com.seglino.jingyi.common.response.ApiResult;
import com.seglino.jingyi.dingtalk.service.DingtalkUserService;
import com.seglino.jingyi.user.pojo.User;
import com.seglino.jingyi.user.service.UserService;

@RestController
@RequestMapping("back/user")
public class UserBackController {

	@Autowired
	private UserService userService;
	@Autowired
	private DingtalkUserService dingtalkuserService;

	@GetMapping("list")
	public ApiPageResult list(PageParams params) {
		ApiPageResult aResult = new ApiPageResult();
		try {
			Page<User> list = userService.page(params);
			aResult.setTotal(list.getTotal());
			aResult.setData(list);
		} catch (Exception e) {
			aResult.AddError(e);
		}
		return aResult;
	}

	@GetMapping("init_dd")
	public ApiResult initUserData(String deptId) {
		ApiResult aResult = new ApiResult();
		try {
			int count = dingtalkuserService.initUserData(deptId);
			aResult.setData("同步完成，共同步用户" + count + "人");
		} catch (Exception e) {
			aResult.AddError(e);
		}
		return aResult;
	}
}
