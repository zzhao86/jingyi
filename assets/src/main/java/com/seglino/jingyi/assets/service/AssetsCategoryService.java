package com.seglino.jingyi.assets.service;

import java.util.List;

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
}
