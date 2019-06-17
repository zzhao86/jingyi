package com.seglino.jingyi.common.core.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.seglino.jingyi.common.core.dao.BaseDao;
import com.seglino.jingyi.common.core.po.BaseEntity;

@Service
public class BaseServiceImpl<D extends BaseDao<T>, T extends BaseEntity> implements BaseService<T> {

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
	public int deleteComplete(Object id) {
		return dao.deleteComplete(id);
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
}
