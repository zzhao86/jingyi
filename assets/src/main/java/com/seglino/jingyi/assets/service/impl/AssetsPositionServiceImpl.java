package com.seglino.jingyi.assets.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.seglino.jingyi.assets.dao.AssetsPositionDao;
import com.seglino.jingyi.assets.dto.AssetsPositionImportDto;
import com.seglino.jingyi.assets.dto.AssetsPositionListDto;
import com.seglino.jingyi.assets.dto.AssetsPositionTreeDto;
import com.seglino.jingyi.assets.pojo.AssetsPosition;
import com.seglino.jingyi.assets.service.AssetsPositionService;
import com.seglino.jingyi.assets.service.AssetsService;
import com.seglino.jingyi.common.core.service.BaseServiceImpl;
import com.seglino.jingyi.common.excel.ImportExcel;
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

	/**
	 * Excel导入资产位置
	 * 
	 * @param file 上传的Excel文件
	 * @throws IOException
	 */
	public void importExcel(MultipartFile file) throws IOException {
		ImportExcel<AssetsPositionImportDto> ie = new ImportExcel<>();
		List<AssetsPositionImportDto> list = ie.importExcel(file, 0, AssetsPositionImportDto.class);
		List<AssetsPositionImportDto> data = getImportChildrenData(list, "");
		saveImportData(list, data, "0");
	}

	/**
	 * 保存数据
	 * 
	 * @param list Excel的所有数据
	 * @param data 需要保存的数据
	 * @param parentId 上级位置ID
	 */
	private void saveImportData(List<AssetsPositionImportDto> list, List<AssetsPositionImportDto> data, String parentId) {
		for (int i = 0; i < data.size(); i++) {
			AssetsPositionImportDto dto = data.get(i);
			AssetsPosition entity = new AssetsPosition();
			entity.setName(dto.getName());
			entity.setParentId(parentId);
			int count = dao.existsByName(entity.getName(), entity.getParentId());
			if (count > 0) {
				continue;
			}
			int result = insert(entity);
			if (result == 1) {
				List<AssetsPositionImportDto> children = getImportChildrenData(list, dto.getName());
				if (null != children && children.size() > 0) {
					saveImportData(list, children, entity.getId().toString());
				}
			}
		}
	}

	/**
	 * 获取导入的子级数据
	 * 
	 * @param list Excel的所有数据
	 * @param parent Excel中的上级位置名称
	 * @return
	 */
	private List<AssetsPositionImportDto> getImportChildrenData(List<AssetsPositionImportDto> list, String parent) {
		return list.stream().filter((AssetsPositionImportDto c) -> parent.equals(c.getParent())).collect(Collectors.toList());
	}
}
