package com.seglino.jingyi.webapi.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.seglino.jingyi.assets.service.AssetsPositionService;
import com.seglino.jingyi.common.response.ApiResult;

@RestController
@RequestMapping("test")
public class TestController {
	@Autowired
	private AssetsPositionService assetsPositionService;
	
	@PostMapping("import")
	public ApiResult importExcel(@RequestParam MultipartFile file) {
		ApiResult aResult = new ApiResult();
		try {
			assetsPositionService.importExcel(file);
		} catch (Exception e) {
			aResult.addError(e);
		}
		return aResult;
	}

	public static class Position {
		private String name;
		private String parent;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getParent() {
			return parent;
		}

		public void setParent(String parent) {
			this.parent = parent;
		}
	}
}
