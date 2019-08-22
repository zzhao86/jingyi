package com.seglino.jingyi.assets.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.seglino.jingyi.assets.dao.AssetsCategoryDao;
import com.seglino.jingyi.assets.dto.AssetsCategoryListDto;
import com.seglino.jingyi.assets.dto.AssetsCategoryTreeDto;
import com.seglino.jingyi.assets.pojo.AssetsCategory;
import com.seglino.jingyi.assets.service.AssetsCategoryService;
import com.seglino.jingyi.assets.service.AssetsService;
import com.seglino.jingyi.common.core.service.BaseServiceImpl;
import com.seglino.jingyi.common.request.RequestPageParams;

@Service
public class AssetsCategoryServiceImpl extends BaseServiceImpl<AssetsCategoryDao, AssetsCategory> implements AssetsCategoryService {

	@Autowired
	private AssetsService assetsService;

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

	@Override
	public int deletePhysical(Object id) {
		int result = 1;
		// 递归获取所有的子类
		List<AssetsCategory> children = allChildren(id);
		for (AssetsCategory category : children) {
			// 判断是否有资产关联
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("categoryId", category.getId());
			int count = assetsService.count(param);
			if (count == 0) {
				super.deletePhysical(category.getId());
			} else {
				result++;
			}
		}
		if (result == 1) {
			result = super.deletePhysical(id);
		}
		return result;
	}

	/**
	 * 递归获取所有子级
	 * 
	 * @param parentId
	 * @return
	 */
	private List<AssetsCategory> allChildren(Object parentId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("parentId", parentId);
		param.put("isSelf", false);
		List<AssetsCategory> list = list(param);
		for (int i = 0; i < list.size(); i++) {
			AssetsCategory category = list.get(i);
			List<AssetsCategory> children = allChildren(category.getId());
			if (null != children && children.size() > 0) {
				list.addAll(children);
			}
		}
		return list;
	}
}
