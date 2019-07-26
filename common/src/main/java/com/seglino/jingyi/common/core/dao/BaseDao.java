package com.seglino.jingyi.common.core.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.github.pagehelper.Page;

@Mapper
public interface BaseDao<T> {

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
	 * 根据ID彻底删除数据
	 * 
	 * @param id
	 * @return
	 */
	public int delete(Object id);

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
	 * 获取符合条件的数据总数
	 * 
	 * @param param
	 * @return
	 */
	public int count(Map<String, Object> param);

	/**
	 * 分页获取数据
	 * @param param
	 * @return
	 */
	public Page<T> page(Map<String, Object> param);
}
