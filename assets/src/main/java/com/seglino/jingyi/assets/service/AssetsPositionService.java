package com.seglino.jingyi.assets.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

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
	
	/**
	 * Excel导入资产位置
	 * @param file
	 */
	public void importExcel(MultipartFile file) throws IOException;
}
