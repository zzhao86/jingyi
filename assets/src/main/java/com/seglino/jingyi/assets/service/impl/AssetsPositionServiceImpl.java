package com.seglino.jingyi.assets.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.seglino.jingyi.assets.dao.AssetsPositionDao;
import com.seglino.jingyi.assets.dto.AssetsPositionListDto;
import com.seglino.jingyi.assets.dto.AssetsPositionTreeDto;
import com.seglino.jingyi.assets.pojo.AssetsPosition;
import com.seglino.jingyi.assets.service.AssetsPositionService;
import com.seglino.jingyi.assets.service.AssetsService;
import com.seglino.jingyi.common.core.service.BaseServiceImpl;
import com.seglino.jingyi.common.request.RequestPageParams;

@Service
public class AssetsPositionServiceImpl extends BaseServiceImpl<AssetsPositionDao, AssetsPosition> implements AssetsPositionService {

	@Autowired
	private AssetsService assetsService;

	/**
	 * 获取列表页分页数据
	 * 
	 * @param params
	 * @return
	 */
	public Page<AssetsPositionListDto> pageByIndex(RequestPageParams params) {
		PageHelper.startPage(params.getIndex(), params.getSize());
		return dao.pageByIndex(params.getCondition());
	}

	/**
	 * 获取资产分类tree数据
	 * 
	 * @return
	 */
	public List<AssetsPositionTreeDto> treeData(String parentId) {
		List<AssetsPositionTreeDto> list = dao.tree(parentId);
		if (null != list && list.size() > 0) {
			for (AssetsPositionTreeDto dto : list) {
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
		List<AssetsPosition> children = allChildren(id);
		for (AssetsPosition position : children) {
			// 判断是否有资产关联
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("positionId", position.getId());
			int count = assetsService.count(param);
			if (count == 0) {
				super.deletePhysical(position.getId());
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
	private List<AssetsPosition> allChildren(Object parentId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("parentId", parentId);
		param.put("isSelf", false);
		List<AssetsPosition> list = list(param);
		for (int i = 0; i < list.size(); i++) {
			AssetsPosition position = list.get(i);
			List<AssetsPosition> children = allChildren(position.getId());
			if (null != children && children.size() > 0) {
				list.addAll(children);
			}
		}
		return list;
	}
}
