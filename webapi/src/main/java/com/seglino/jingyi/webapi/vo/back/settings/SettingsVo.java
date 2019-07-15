package com.seglino.jingyi.webapi.vo.back.settings;

import com.seglino.jingyi.common.core.vo.BaseVo;

public class SettingsVo extends BaseVo {
	private String code;
	private String name;
	private String value;
	private String optionValues;
	private String type;
	private String displayMode;
	private String description;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getOptionValues() {
		return optionValues;
	}

	public void setOptionValues(String optionValues) {
		this.optionValues = optionValues;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDisplayMode() {
		return displayMode;
	}

	public void setDisplayMode(String displayMode) {
		this.displayMode = displayMode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
