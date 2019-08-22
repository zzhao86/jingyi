package com.seglino.jingyi.assets.service;

import java.util.List;

import com.github.pagehelper.Page;
import com.seglino.jingyi.assets.dto.AssetsPositionListDto;
import com.seglino.jingyi.assets.dto.AssetsPositionTreeDto;
import com.seglino.jingyi.assets.pojo.AssetsPosition;
import com.seglino.jingyi.common.core.service.BaseService;
import com.seglino.jingyi.common.request.RequestPageParams;

public interface AssetsPositionService extends BaseService<AssetsPosition> {

	/**
	 * 获取列表页分页数据
	 * 
	 * @param params
	 * @return
	 */
	public Page<AssetsPositionListDto> pageByIndex(RequestPageParams params);

	/**
	 * 获取资产位置tree数据
	 * @return
	 */
	public List<AssetsPositionTreeDto> treeData(String parentId);
}
