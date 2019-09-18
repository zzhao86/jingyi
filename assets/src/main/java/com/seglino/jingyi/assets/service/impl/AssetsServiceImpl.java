package com.seglino.jingyi.assets.service.impl;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.seglino.jingyi.assets.dao.AssetsDao;
import com.seglino.jingyi.assets.dto.AssetsEntryData;
import com.seglino.jingyi.assets.dto.AssetsExportDto;
import com.seglino.jingyi.assets.dto.AssetsImportDto;
import com.seglino.jingyi.assets.dto.AssetsListDto;
import com.seglino.jingyi.assets.pojo.Assets;
import com.seglino.jingyi.assets.pojo.AssetsCategory;
import com.seglino.jingyi.assets.pojo.AssetsPosition;
import com.seglino.jingyi.assets.service.AssetsCategoryService;
import com.seglino.jingyi.assets.service.AssetsPositionService;
import com.seglino.jingyi.assets.service.AssetsService;
import com.seglino.jingyi.common.core.service.BaseServiceImpl;
import com.seglino.jingyi.common.excel.ExportExcel;
import com.seglino.jingyi.common.excel.ImportExcel;
import com.seglino.jingyi.common.log.annotation.ServiceLog;
import com.seglino.jingyi.common.request.RequestPageParams;
import com.seglino.jingyi.common.utils.DateUtils;

@ServiceLog("资产服务")
@Service
public class AssetsServiceImpl extends BaseServiceImpl<AssetsDao, Assets> implements AssetsService {

	@Autowired
	private AssetsCategoryService assetsCategoryService;
	@Autowired
	private AssetsPositionService assetsPositionService;

	/**
	 * 获取主页列表分页数据
	 * 
	 * @param params
	 * @return
	 */
	@ServiceLog("查看资产分页数据")
	public Page<AssetsListDto> pageByIndex(RequestPageParams params) {
		PageHelper.startPage(params.getIndex(), params.getSize());
		return dao.pageByIndex(params.getCondition());
	}

	@ServiceLog("新建资产")
	@Override
	public int insert(Assets entity) {
		Long maxCode = dao.maxCode();
		entity.setCode(maxCode + 1);
		return super.insert(entity);
	}

	/**
	 * 批量导入资产信息
	 * 
	 * @param file
	 */
	@ServiceLog("导入资产数据")
	public void importExcel(MultipartFile file) throws Exception {
		ImportExcel<AssetsImportDto> ie = new ImportExcel<AssetsImportDto>();
		List<AssetsImportDto> list = ie.importExcel(file, 0, AssetsImportDto.class);
		for (int i = 0; i < list.size(); i++) {
			AssetsImportDto dto = list.get(i);
			String categoryId = "0";
			String positionId = "0";

			// 处理分类
			if (StringUtils.isEmpty(dto.getCategory())) {
				throw new Exception("请填写资产 “" + dto.getName() + "” 的分类");
			}
			String[] categories = dto.getCategory().split("/");
			for (String category : categories) {
				categoryId = getOrInsertCategory(category, categoryId);
			}
			// 处理位置
			if (StringUtils.isEmpty(dto.getPosition())) {
				throw new Exception("请填写资产 “" + dto.getName() + "” 的位置");
			}
			String[] positions = dto.getPosition().split("/");
			for (String position : positions) {
				positionId = getOrInsertPosition(position, positionId);
			}

			Assets assets = new Assets();
			assets.setName(dto.getName());
			assets.setCategoryId(categoryId);
			assets.setPositionId(positionId);
			// assets.setAdmin();
			assets.setBrand(dto.getBrand());
			assets.setModel(dto.getModel());
			assets.setUseStatus(AssetsEntryData.getUseStatusValue(dto.getUseStatus()));
			assets.setStatus(100);
			assets.setPurchasingMethod(AssetsEntryData.getPurchasingMethodValue(dto.getPurchasingMethod()));
			assets.setStartDate(dto.getStartDate());
			assets.setUseTerm(dto.getUseTerm());
			assets.setAmount(dto.getAmount());
			assets.setRemark(dto.getRemark());
			assets.setSupplier(dto.getSupplier());
			assets.setSupplierContact(dto.getSupplierContact());
			assets.setSupplierMobile(dto.getSupplierMobile());
			assets.setMaintDate(dto.getMaintDate());
			assets.setMaintNotes(dto.getMaintNotes());

			insert(assets);
		}
	}

	/**
	 * 查找现有分类或插入未存在的分类
	 * 
	 * @param name
	 * @param parentId
	 * @return
	 */
	private String getOrInsertCategory(String name, String parentId) {
		boolean exists = assetsCategoryService.existsByName(name, parentId);
		if (exists) {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("name", name);
			param.put("parentId", parentId);
			AssetsCategory category = assetsCategoryService.detail(param);
			return category.getId().toString();
		} else {
			AssetsCategory category = new AssetsCategory();
			category.setName(name);
			category.setParentId(parentId);
			assetsCategoryService.insert(category);
			return category.getId().toString();
		}
	}

	/**
	 * 查找现有位置或插入未存在的位置
	 * 
	 * @param name
	 * @param parentId
	 * @return
	 */
	private String getOrInsertPosition(String name, String parentId) {
		boolean exists = assetsPositionService.existsByName(name, parentId);
		if (exists) {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("name", name);
			param.put("parentId", parentId);
			AssetsPosition position = assetsPositionService.detail(param);
			return position.getId().toString();
		} else {
			AssetsPosition position = new AssetsPosition();
			position.setName(name);
			position.setParentId(parentId);
			assetsPositionService.insert(position);
			return position.getId().toString();
		}
	}

	/**
	 * 导出数据到Excel
	 * 
	 * @param param
	 * @param response
	 * @throws IOException
	 */
	@ServiceLog("导出资产数据")
	public void exportExcel(Map<String, Object> param, HttpServletResponse response) throws IOException {
		LinkedHashMap<String, String> headMap = new LinkedHashMap<String, String>();
		headMap.put("codeLabel", "资产编码");
		headMap.put("name", "资产名称");
		headMap.put("categoryName", "分类");
		headMap.put("positionName", "位置");
		headMap.put("brand", "品牌");
		headMap.put("model", "型号");
		headMap.put("statusName", "资产状态");
		headMap.put("useStatusName", "使用状态");
		headMap.put("adminName", "管理员");
		headMap.put("purchasingMethodName", "采购方式");
		headMap.put("startDate", "购置日期");
		headMap.put("amount", "购置金额");
		headMap.put("useTermName", "预计使用期限");
		headMap.put("remark", "备注");
		headMap.put("supplier", "供应商");
		headMap.put("supplierContact", "供应商联系人");
		headMap.put("supplierMobile", "联系方式");
		headMap.put("maintDate", "维保到期日期");
		headMap.put("maintNotes", "维保说明");

		ExportExcel<AssetsExportDto> ee = new ExportExcel<AssetsExportDto>("资产信息数据");
		XSSFWorkbook workbook = ee.exportExcel(dao.exportData(param), headMap);

		String fileName = "资产信息导出_" + DateUtils.getNowString("yyyy-MM-dd") + "." + workbook.getWorkbookType().name().toLowerCase();
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attachment;Filename=" + URLEncoder.encode(fileName, "UTF-8"));
		workbook.write(response.getOutputStream());
	}
}
