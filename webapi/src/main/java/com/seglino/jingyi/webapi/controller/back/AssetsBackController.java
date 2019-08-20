package com.seglino.jingyi.webapi.controller.back;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.Page;
import com.seglino.jingyi.assets.dto.AssetsCategoryListDto;
import com.seglino.jingyi.assets.pojo.AssetsCategory;
import com.seglino.jingyi.assets.service.AssetsCategoryService;
import com.seglino.jingyi.common.request.RequestPageParams;
import com.seglino.jingyi.common.response.ApiPageResult;
import com.seglino.jingyi.common.response.ApiResult;
import com.seglino.jingyi.common.utils.AutoMapper;
import com.seglino.jingyi.webapi.vo.back.assets.AssetsCategoryListVo;

@RestController
@RequestMapping("back/assets")
public class AssetsBackController {
	@Autowired
	private AssetsCategoryService assetsCategoryService;

	@GetMapping("category/list")
	public ApiPageResult categoryList(RequestPageParams params) {
		ApiPageResult aResult = new ApiPageResult();
		try {
			Page<AssetsCategoryListDto> page = assetsCategoryService.pageByIndex(params);
			aResult.setTotal(page.getTotal());
			aResult.setData(AutoMapper.mapperList(page.getResult(), AssetsCategoryListVo.class));
		} catch (Exception e) {
			aResult.addError(e);
		}
		return aResult;
	}
	
	@PostMapping("category/add")
	public ApiResult categoryAdd(AssetsCategory category) {
		ApiResult aResult = new ApiResult();
		try {
			assetsCategoryService.insert(category);
		} catch (Exception e) {
			aResult.addError(e);
		}
		return aResult;
	}
}
