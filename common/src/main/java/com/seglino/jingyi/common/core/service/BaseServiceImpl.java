package com.seglino.jingyi.common.core.service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.seglino.jingyi.common.core.dao.BaseDao;
import com.seglino.jingyi.common.core.po.BaseEntity;
import com.seglino.jingyi.common.core.utils.DateUtils;
import com.seglino.jingyi.common.request.PageParams;

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
			entity.setCreateUid(null);
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
			entity.setModifyUid(null);
		}
		return dao.update(entity);
	}

	/**
	 * 根据ID删除数据
	 * 
	 * @param entity
	 * @return
	 */
	@Override
	public int delete(T entity) {
		if (null != entity) {
			entity.setDeleteTime(DateUtils.getNow());
			entity.setDeleteUid(null);
		}
		return dao.delete(entity);
	}

	/**
	 * 根据ID恢复删除（未彻底删除）的数据
	 * 
	 * @param entity
	 * @return
	 */
	@Override
	public int restore(T entity) {
		return dao.restore(entity);
	}

	/**
	 * 根据ID彻底删除数据
	 * 
	 * @param id
	 * @return
	 */
	@Override
	public int deletePhysical(Object id) {
		return dao.deletePhysical(id);
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
		return dao.detailById(id);
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
	 * @param params 分页参数
	 * @return
	 */
	@Override
	public Page<T> page(PageParams params) {
		PageHelper.startPage(params.getPageNum(), params.getPageSize());
		return dao.page(params.getParams());
	}
}
