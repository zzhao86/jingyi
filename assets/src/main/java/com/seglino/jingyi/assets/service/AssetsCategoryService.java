package com.seglino.jingyi.assets.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.Page;
import com.seglino.jingyi.assets.dto.AssetsCategoryListDto;
import com.seglino.jingyi.assets.dto.AssetsCategoryTreeDto;
import com.seglino.jingyi.assets.pojo.AssetsCategory;
import com.seglino.jingyi.common.core.service.BaseService;
import com.seglino.jingyi.common.request.RequestPageParams;

public interface AssetsCategoryService extends BaseService<AssetsCategory> {

	/**
	 * 获取列表页分页数据
	 * 
	 * @param params
	 * @return
	 */
	public Page<AssetsCategoryListDto> pageByIndex(RequestPageParams params);

	/**
	 * 获取资产分类tree数据
	 * @return
	 */
	public List<AssetsCategoryTreeDto> treeData(String parentId);
	
	/**
	 * Excel导入资产分类
	 * @param file
	 */
	public void importExcel(MultipartFile file) throws IOException;

	/**
	 * 判断同一上级位置中是否存在重复名称的位置
	 * @param name 
	 * @param parentId
	 * @return
	 */
	public boolean existsByName(String name, String parentId);
}
