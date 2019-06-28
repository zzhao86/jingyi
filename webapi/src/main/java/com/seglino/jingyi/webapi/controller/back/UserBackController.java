package com.seglino.jingyi.webapi.controller.back;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.seglino.jingyi.common.response.ApiResult;
import com.seglino.jingyi.user.pojo.User;
import com.seglino.jingyi.user.service.UserService;

@RestController
@RequestMapping("back/user")
public class UserBackController {

	@Autowired
	private UserService userService;

	@GetMapping("list")
	public ApiResult list(@RequestParam Map<String, Object> param) {
		ApiResult aResult = new ApiResult();
		try {
			List<User> list = userService.list(param);
			aResult.setData(list);
		} catch (Exception e) {
			aResult.AddError(e);
		}
		return aResult;
	}
}
