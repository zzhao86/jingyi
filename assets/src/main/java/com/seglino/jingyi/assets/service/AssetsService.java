package com.seglino.jingyi.assets.service;

import com.github.pagehelper.Page;
import com.seglino.jingyi.assets.dto.AssetsListDto;
import com.seglino.jingyi.assets.pojo.Assets;
import com.seglino.jingyi.common.core.service.BaseService;
import com.seglino.jingyi.common.request.RequestPageParams;

public interface AssetsService extends BaseService<Assets> {

	/**
	 * 获取主页列表分页数据
	 * @param params
	 * @return
	 */
	public Page<AssetsListDto> pageByIndex(RequestPageParams params);
}
