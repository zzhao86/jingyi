package com.seglino.jingyi.assets.service;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.Page;
import com.seglino.jingyi.assets.dto.AssetsListDto;
import com.seglino.jingyi.assets.pojo.Assets;
import com.seglino.jingyi.common.core.service.BaseService;
import com.seglino.jingyi.common.request.RequestPageParams;

public interface AssetsService extends BaseService<Assets> {

	/**
	 * 获取主页列表分页数据
	 * 
	 * @param params
	 * @return
	 */
	public Page<AssetsListDto> pageByIndex(RequestPageParams params);

	/**
	 * 批量导入资产信息
	 * 
	 * @param file
	 */
	public void importExcel(MultipartFile file) throws Exception;

	/**
	 * 导出数据到Excel
	 * 
	 * @param param
	 * @param response
	 */
	public void exportExcel(Map<String, Object> param, HttpServletResponse response) throws IOException;
}
