package com.seglino.jingyi.common.core.vo;

/**
 * 数据字典
 * 
 * @author ZZH
 *
 */
public class EntryData {
	private String label;
	private Object value;
	private Boolean disabled = false;

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public Boolean getDisabled() {
		return disabled;
	}

	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}

	/**
	 * 数据字典
	 */
	public EntryData() {

	}

	/**
	 * 数据字典
	 * 
	 * @param label 标签
	 * @param value 值
	 */
	public EntryData(String label, Object value) {
		this.label = label;
		this.value = value;
	}

	/**
	 * 数据字典
	 * 
	 * @param label 标签
	 * @param value 值
	 * @param disabled 是否禁用
	 */
	public EntryData(String label, Object value, Boolean disabled) {
		this.label = label;
		this.value = value;
		this.disabled = disabled;
	}
}
