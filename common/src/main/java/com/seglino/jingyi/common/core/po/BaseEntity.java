package com.seglino.jingyi.common.core.po;

import java.util.Date;

public abstract class BaseEntity {
	/**
	 * 主键
	 */
	private Object id;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 创建人ID
	 */
	private String createUid;
	/**
	 * 修改时间
	 */
	private Date modifyTime;
	/**
	 * 修改人ID
	 */
	private String modifyUid;
	/**
	 * 删除时间
	 */
	private Date deleteTime;
	/**
	 * 删除人ID
	 */
	private String deleteUid;
	/**
	 * 是否删除
	 */
	private Boolean isDeleted;

	/**
	 * 获取主键ID
	 * 
	 * @return
	 */
	public Object getId() {
		return id;
	}

	/**
	 * 设置主键ID
	 * 
	 * @param id
	 */
	public void setId(Object id) {
		this.id = id;
	}

	/**
	 * 获取创建时间
	 * 
	 * @return
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * 设置创建时间
	 * 
	 * @param createTime
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 获取创建操作人ID
	 * 
	 * @return
	 */
	public String getCreateUid() {
		return createUid;
	}

	/**
	 * 设置创建操作人ID
	 * 
	 * @param createUid
	 */
	public void setCreateUid(String createUid) {
		this.createUid = createUid;
	}

	/**
	 * 获取修改时间
	 * 
	 * @return
	 */
	public Date getModifyTime() {
		return modifyTime;
	}

	/**
	 * 设置修改时间
	 * 
	 * @param updateTime
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	/**
	 * 获取修改操作人ID
	 * 
	 * @return
	 */
	public String getModifyUid() {
		return modifyUid;
	}

	/**
	 * 设置修改操作人ID
	 * 
	 * @param updateTime
	 */
	public void setModifyUid(String modifyUid) {
		this.modifyUid = modifyUid;
	}

	/**
	 * 获取删除时间
	 * 
	 * @return
	 */
	public Date getDeleteTime() {
		return deleteTime;
	}

	/**
	 * 设置删除时间
	 * 
	 * @param deleteTime
	 */
	public void setDeleteTime(Date deleteTime) {
		this.deleteTime = deleteTime;
	}

	/**
	 * 获取删除操作人ID
	 * 
	 * @return
	 */
	public String getDeleteUid() {
		return deleteUid;
	}

	/**
	 * 设置删除操作人ID
	 * 
	 * @param deleteUid
	 */
	public void setDeleteUid(String deleteUid) {
		this.deleteUid = deleteUid;
	}

	/**
	 * 获取是否删除
	 * 
	 * @return
	 */ 
	public Boolean getIsDeleted() {
		return isDeleted;
	}

	/**
	 * 设置是否删除(0:启用 1:禁用)
	 * 
	 * @param isDeleted
	 */
	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
}
