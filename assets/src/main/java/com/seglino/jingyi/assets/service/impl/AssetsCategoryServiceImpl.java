package com.seglino.jingyi.assets.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.seglino.jingyi.assets.dao.AssetsCategoryDao;
import com.seglino.jingyi.assets.dto.AssetsCategoryListDto;
import com.seglino.jingyi.assets.dto.AssetsCategoryTreeDto;
import com.seglino.jingyi.assets.pojo.AssetsCategory;
import com.seglino.jingyi.assets.service.AssetsCategoryService;
import com.seglino.jingyi.common.core.service.BaseServiceImpl;
import com.seglino.jingyi.common.request.RequestPageParams;

@Service
public class AssetsCategoryServiceImpl extends BaseServiceImpl<AssetsCategoryDao, AssetsCategory> implements AssetsCategoryService {

	/**
	 * 获取列表页分页数据
	 * 
	 * @param params
	 * @return
	 */
	public Page<AssetsCategoryListDto> pageByIndex(RequestPageParams params) {
		PageHelper.startPage(params.getIndex(), params.getSize());
		return dao.pageByIndex(params.getCondition());
	}

	/**
	 * 获取资产分类tree数据
	 * 
	 * @return
	 */
	public List<AssetsCategoryTreeDto> treeData(String parentId) {
		List<AssetsCategoryTreeDto> list = dao.tree(parentId);
		if (null != list && list.size() > 0) {
			for (AssetsCategoryTreeDto dto : list) {
				if (dto.getChildCount() > 0) {
					dto.setChildren(treeData(dto.getId().toString()));
				}
			}
		}
		return list;
	}
	
}
