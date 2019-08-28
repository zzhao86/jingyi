package com.seglino.jingyi.common.core.vo;

public class EntryData {
	private String label;
	private Object value;

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

	public EntryData() {

	}

	public EntryData(String label, Object value) {
		this.label = label;
		this.value = value;
	}
}
