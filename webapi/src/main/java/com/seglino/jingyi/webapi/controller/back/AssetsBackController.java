package com.seglino.jingyi.webapi.controller.back;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.Page;
import com.seglino.jingyi.assets.dto.AssetsCategoryListDto;
import com.seglino.jingyi.assets.dto.AssetsCategoryTreeDto;
import com.seglino.jingyi.assets.pojo.AssetsCategory;
import com.seglino.jingyi.assets.service.AssetsCategoryService;
import com.seglino.jingyi.common.request.RequestPageParams;
import com.seglino.jingyi.common.response.ApiPageResult;
import com.seglino.jingyi.common.response.ApiResult;
import com.seglino.jingyi.common.utils.AutoMapper;
import com.seglino.jingyi.webapi.vo.back.assets.AssetsCategoryListVo;
import com.seglino.jingyi.webapi.vo.back.assets.AssetsCategoryTreeVo;

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

	@GetMapping("category/tree")
	public ApiResult categoryTree() {
		ApiResult aResult = new ApiResult();
		try {
			List<AssetsCategoryTreeDto> list = assetsCategoryService.treeData("0");
			aResult.setData(AutoMapper.mapperList(list, AssetsCategoryTreeVo.class));
		} catch (Exception e) {
			aResult.addError(e);
		}
		return aResult;
	}

	@PostMapping("category/save")
	public ApiResult categoryAdd(@RequestBody AssetsCategory category) {
		ApiResult aResult = new ApiResult();
		try {
			if (null == category.getId()) {
				assetsCategoryService.insert(category);
			} else {
				assetsCategoryService.update(category);
			}
		} catch (Exception e) {
			aResult.addError(e);
		}
		return aResult;
	}
	
	@GetMapping("category/delete")
	public ApiResult categoryDelete(String id) {
		ApiResult aResult = new ApiResult();
		try {
			assetsCategoryService.deletePhysical(id);
		} catch (Exception e) {
			aResult.addError(e);
		}
		return aResult;
	}
}
