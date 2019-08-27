package com.seglino.jingyi.assets.service.impl;

import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.seglino.jingyi.assets.dao.AssetsDao;
import com.seglino.jingyi.assets.dto.AssetsListDto;
import com.seglino.jingyi.assets.pojo.Assets;
import com.seglino.jingyi.assets.service.AssetsService;
import com.seglino.jingyi.common.core.service.BaseServiceImpl;
import com.seglino.jingyi.common.request.RequestPageParams;

@Service
public class AssetsServiceImpl extends BaseServiceImpl<AssetsDao, Assets> implements AssetsService {

	/**
	 * 获取主页列表分页数据
	 * @param params
	 * @return
	 */
	public Page<AssetsListDto> pageByIndex(RequestPageParams params){
		PageHelper.startPage(params.getIndex(), params.getSize());
		return dao.pageByIndex(params.getCondition());
	}
}
