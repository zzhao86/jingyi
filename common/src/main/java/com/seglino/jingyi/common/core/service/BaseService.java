package com.seglino.jingyi.common.core.service;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.Page;
import com.seglino.jingyi.common.request.RequestListParams;

public interface BaseService<T> {

	/**
	 * 插入一条数据
	 * 
	 * @param entity
	 * @return
	 */
	public int insert(T entity);

	/**
	 * 批量插入数据
	 * 
	 * @param list
	 * @return
	 */
	public int insertBatch(List<T> list);

	/**
	 * 修改一条数据
	 * 
	 * @param entity
	 * @return
	 */
	public int update(T entity);

	/**
	 * 根据ID删除数据
	 * 
	 * @param id
	 * @return
	 */
	public int delete(Object id);
	
	/**
	 * 根据ID批量删除
	 * @param ids
	 * @return
	 */
	public int deleteBatch(List<Object> ids);
	
	/**
	 * 根据ID恢复删除（未彻底删除）的数据
	 * @param id
	 * @return
	 */
	public int restore(Object id);

	/**
	 * 根据ID彻底删除数据
	 * @param id
	 * @return
	 */
	public int deletePhysical(Object id);
	
	/**
	 * 获取符合条件的数据列表
	 * 
	 * @param param
	 * @return
	 */
	public List<T> list(Map<String, Object> param);

	/**
	 * 获取符合条件的详细数据
	 * 
	 * @param param
	 * @return
	 */
	public T detail(Map<String, Object> param);

	/**
	 * 根据ID获取详细数据
	 * 
	 * @param param
	 * @return
	 */
	public T detailById(Object id);

	/**
	 * 获取符合条件的数据总数
	 * 
	 * @param param
	 * @return
	 */
	public int count(Map<String, Object> param);
	
	/**
	 * 分页获取数据
	 * @param params 列表请求参数
	 * @return
	 */
	public Page<T> page(RequestListParams params);
}

