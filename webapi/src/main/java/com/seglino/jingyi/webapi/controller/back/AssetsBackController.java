package com.seglino.jingyi.webapi.controller.back;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.Page;
import com.seglino.jingyi.assets.dto.AssetsCategoryListDto;
import com.seglino.jingyi.assets.dto.AssetsCategoryTreeDto;
import com.seglino.jingyi.assets.dto.AssetsListDto;
import com.seglino.jingyi.assets.dto.AssetsPositionListDto;
import com.seglino.jingyi.assets.dto.AssetsPositionTreeDto;
import com.seglino.jingyi.assets.pojo.Assets;
import com.seglino.jingyi.assets.pojo.AssetsCategory;
import com.seglino.jingyi.assets.pojo.AssetsPosition;
import com.seglino.jingyi.assets.service.AssetsCategoryService;
import com.seglino.jingyi.assets.service.AssetsPositionService;
import com.seglino.jingyi.assets.service.AssetsService;
import com.seglino.jingyi.common.request.RequestPageParams;
import com.seglino.jingyi.common.response.ApiPageResult;
import com.seglino.jingyi.common.response.ApiResult;
import com.seglino.jingyi.common.utils.AutoMapper;
import com.seglino.jingyi.file.service.FilesService;
import com.seglino.jingyi.webapi.vo.back.assets.AssetsCategoryListVo;
import com.seglino.jingyi.webapi.vo.back.assets.AssetsCategoryTreeVo;
import com.seglino.jingyi.webapi.vo.back.assets.AssetsDataMapping;
import com.seglino.jingyi.webapi.vo.back.assets.AssetsListVo;
import com.seglino.jingyi.webapi.vo.back.assets.AssetsPositionListVo;
import com.seglino.jingyi.webapi.vo.back.assets.AssetsPositionTreeVo;

@RestController
@RequestMapping("back/assets")
public class AssetsBackController {
	@Autowired
	private AssetsService assetsService;
	@Autowired
	private AssetsCategoryService assetsCategoryService;
	@Autowired
	private AssetsPositionService assetsPositionService;
	@Autowired
	private FilesService filesService;

	/**
	 * 获取资产列表分页数据
	 * @param params
	 * @return
	 */
	@GetMapping("list")
	public ApiPageResult list(RequestPageParams params) {
		ApiPageResult aResult = new ApiPageResult();
		Page<AssetsListDto> page = assetsService.pageByIndex(params);
		aResult.setTotal(page.getTotal());
		aResult.setData(AutoMapper.mapperList(page.getResult(), AssetsListVo.class));
		return aResult;
	}

	/**
	 * 资产分类下拉框数据
	 * @return
	 */
	@GetMapping("category/select")
	public ApiResult categorySelect() {
		ApiResult aResult = new ApiResult();
		aResult.setData(AssetsDataMapping.statusList());
		return aResult;
	}

	/**
	 * 获取资产分类列表分页数据
	 * 
	 * @param params
	 * @return
	 */
	@GetMapping("category/list")
	public ApiPageResult categoryList(RequestPageParams params) {
		ApiPageResult aResult = new ApiPageResult();
		try {
			params.addCondition("isSelf", 1);
			Page<AssetsCategoryListDto> page = assetsCategoryService.pageByIndex(params);
			aResult.setTotal(page.getTotal());
			aResult.setData(AutoMapper.mapperList(page.getResult(), AssetsCategoryListVo.class));
		} catch (Exception e) {
			aResult.addError(e);
		}
		return aResult;
	}

	/**
	 * 获取资产分类tree数据
	 * 
	 * @return
	 */
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

	/**
	 * 资产分类保存
	 * 
	 * @param category
	 * @return
	 */
	@PostMapping("category/save")
	public ApiResult categorySave(@RequestBody AssetsCategory category) {
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

	/**
	 * 资产分类删除
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("category/delete")
	public ApiResult categoryDelete(String id) {
		ApiResult aResult = new ApiResult();
		try {
			int result = assetsPositionService.deletePhysical(id);
			if (result <= 0) {
				aResult.addError("删除失败");
			}
			if (result > 1) {
				aResult.addError("删除成功，但部分分类下有资产存在无法删除");
			}
		} catch (Exception e) {
			aResult.addError(e);
		}
		return aResult;
	}

	/**
	 * 下载资产分类导入模板
	 */
	@GetMapping("category/template/download")
	public void categoryTemplateDownload(HttpServletResponse response) {
		String url = "/static/ExcelTemplate/资产分类导入模板.xlsx";
		filesService.download(url, "资产分类导入模板.xlsx", response);
	}

	/**
	 * 资产分类批量导入
	 * 
	 * @param file
	 * @return
	 */
	@PostMapping("category/import")
	public ApiResult categoryImportExcel(@RequestParam MultipartFile file) {
		ApiResult aResult = new ApiResult();
		try {
			assetsCategoryService.importExcel(file);
		} catch (Exception e) {
			aResult.addError(e);
		}
		return aResult;
	}

	/**
	 * 获取资产位置列表数据
	 * 
	 * @param params
	 * @return
	 */
	@GetMapping("position/list")
	public ApiPageResult positionList(RequestPageParams params) {
		ApiPageResult aResult = new ApiPageResult();
		try {
			params.addCondition("isSelf", 1);
			Page<AssetsPositionListDto> page = assetsPositionService.pageByIndex(params);
			aResult.setTotal(page.getTotal());
			aResult.setData(AutoMapper.mapperList(page.getResult(), AssetsPositionListVo.class));
		} catch (Exception e) {
			aResult.addError(e);
		}
		return aResult;
	}

	/**
	 * 获取资产位置Tree数据
	 * 
	 * @return
	 */
	@GetMapping("position/tree")
	public ApiResult positionTree() {
		ApiResult aResult = new ApiResult();
		try {
			List<AssetsPositionTreeDto> list = assetsPositionService.treeData("0");
			aResult.setData(AutoMapper.mapperList(list, AssetsPositionTreeVo.class));
		} catch (Exception e) {
			aResult.addError(e);
		}
		return aResult;
	}

	/**
	 * 资产位置保存
	 * 
	 * @param position
	 * @return
	 */
	@PostMapping("position/save")
	public ApiResult positionSave(@RequestBody AssetsPosition position) {
		ApiResult aResult = new ApiResult();
		try {
			if (null == position.getId()) {
				assetsPositionService.insert(position);
			} else {
				assetsPositionService.update(position);
			}
		} catch (Exception e) {
			aResult.addError(e);
		}
		return aResult;
	}

	/**
	 * 资产位置删除
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("position/delete")
	public ApiResult positionDelete(String id) {
		ApiResult aResult = new ApiResult();
		try {
			int result = assetsPositionService.deletePhysical(id);
			if (result <= 0) {
				aResult.addError("删除失败");
			}
			if (result > 1) {
				aResult.addError("删除成功，但部分位置下有资产存在无法删除");
			}
		} catch (Exception e) {
			aResult.addError(e);
		}
		return aResult;
	}

	/**
	 * 下载资产位置导入模板
	 */
	@GetMapping("position/template/download")
	public void positionTemplateDownload(HttpServletResponse response) {
		String url = "/static/ExcelTemplate/资产位置导入模板.xlsx";
		filesService.download(url, "资产位置导入模板.xlsx", response);
	}

	/**
	 * 资产位置批量导入
	 * 
	 * @param file
	 * @return
	 */
	@PostMapping("position/import")
	public ApiResult positionImportExcel(@RequestParam MultipartFile file) {
		ApiResult aResult = new ApiResult();
		try {
			assetsPositionService.importExcel(file);
		} catch (Exception e) {
			aResult.addError(e);
		}
		return aResult;
	}
}
