package com.seglino.jingyi.assets.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.seglino.jingyi.assets.dao.AssetsDao;
import com.seglino.jingyi.assets.dto.AssetsEntryData;
import com.seglino.jingyi.assets.dto.AssetsImportDto;
import com.seglino.jingyi.assets.dto.AssetsListDto;
import com.seglino.jingyi.assets.pojo.Assets;
import com.seglino.jingyi.assets.pojo.AssetsCategory;
import com.seglino.jingyi.assets.pojo.AssetsPosition;
import com.seglino.jingyi.assets.service.AssetsCategoryService;
import com.seglino.jingyi.assets.service.AssetsPositionService;
import com.seglino.jingyi.assets.service.AssetsService;
import com.seglino.jingyi.common.core.service.BaseServiceImpl;
import com.seglino.jingyi.common.excel.ImportExcel;
import com.seglino.jingyi.common.request.RequestPageParams;

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
	public Page<AssetsListDto> pageByIndex(RequestPageParams params) {
		PageHelper.startPage(params.getIndex(), params.getSize());
		return dao.pageByIndex(params.getCondition());
	}

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
//			assets.setAdmin();
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
}
