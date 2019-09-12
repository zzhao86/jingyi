package com.seglino.jingyi.common.core.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.seglino.jingyi.common.core.dao.BaseDao;
import com.seglino.jingyi.common.core.po.BaseEntity;
import com.seglino.jingyi.common.request.RequestPageParams;
import com.seglino.jingyi.common.utils.ApplicationUtils;
import com.seglino.jingyi.common.utils.DateUtils;

@Service
public abstract class BaseServiceImpl<D extends BaseDao<T>, T extends BaseEntity> implements BaseService<T> {

	@Autowired
	protected D dao;

	/**
	 * 插入一条数据
	 * 
	 * @param entity
	 * @return
	 */
	@Override
	public int insert(T entity) {
		if (null != entity) {
			entity.setId(UUID.randomUUID().toString().replaceAll("-", ""));
			entity.setCreateTime(DateUtils.getNow());
			entity.setCreateUid(ApplicationUtils.getUserId());
		}
		return dao.insert(entity);
	}

	/**
	 * 批量插入数据
	 * 
	 * @param list
	 * @return
	 */
	@Override
	public int insertBatch(List<T> list) {
		return dao.insertBatch(list);
	}

	/**
	 * 修改一条数据
	 * 
	 * @param entity
	 * @return
	 */
	@Override
	public int update(T entity) {
		if (null != entity) {
			entity.setModifyTime(DateUtils.getNow());
			entity.setModifyUid(ApplicationUtils.getUserId());
		}
		return dao.update(entity);
	}

	/**
	 * 根据ID删除数据
	 * 
	 * @param id
	 * @return
	 */
	@Override
	public int delete(Object id) {
		T entity = detailById(id);		
		if (null != entity) {
			entity.setDeleteTime(DateUtils.getNow());
			entity.setDeleteUid(ApplicationUtils.getUserId());
			entity.setIsDeleted(true);
		}
		return dao.update(entity);
	}
	
	/**
	 * 根据ID批量删除
	 * @param ids
	 * @return
	 */
	public int deleteBatch(List<Object> ids) {
		int count = 0;
		for (Object id : ids) {
			count += delete(id);
		}
		return count;
	}

	/**
	 * 根据ID恢复删除（未彻底删除）的数据
	 * 
	 * @param id
	 * @return
	 */
	@Override
	public int restore(Object id) {
		T entity = detailById(id);		
		if (null != entity) {
			entity.setDeleteUid("");
			entity.setIsDeleted(false);
		}
		return dao.update(entity);
	}

	/**
	 * 根据ID彻底删除数据
	 * 
	 * @param id
	 * @return
	 */
	@Override
	public int deletePhysical(Object id) {
		return dao.delete(id);
	}

	/**
	 * 获取符合条件的数据列表
	 * 
	 * @param param
	 * @return
	 */
	@Override
	public List<T> list(Map<String, Object> param) {
		return dao.list(param);
	}

	/**
	 * 获取符合条件的详细数据
	 * 
	 * @param param
	 * @return
	 */
	@Override
	public T detail(Map<String, Object> param) {
		return dao.detail(param);
	}

	/**
	 * 根据ID获取详细数据
	 * 
	 * @param param
	 * @return
	 */
	@Override
	public T detailById(Object id) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("id", id);
		return dao.detail(param);
	}

	/**
	 * 获取符合条件的数据总数
	 * 
	 * @param param
	 * @return
	 */
	@Override
	public int count(Map<String, Object> param) {
		return dao.count(param);
	}

	/**
	 * 分页获取数据
	 * 
	 * @param params 列表请求参数
	 * @return
	 */
	@Override
	public Page<T> page(RequestPageParams params) {
		PageHelper.startPage(params.getIndex(), params.getSize());
		return dao.page(params.getCondition());
	}
}
