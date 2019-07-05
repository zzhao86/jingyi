package com.seglino.jingyi.common.request;

import java.io.Serializable;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONObject;

public class RequestListParams implements Serializable {
	private static final long serialVersionUID = -1369080387086490091L;

	private int index = 1;
	private int size = 15;
	private String query;
	private Map<String, Object> condition = null;

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> getCondition() {
		if (!StringUtils.isEmpty(query)) {
			return JSONObject.parseObject(query, Map.class);
		}
		return condition;
	}

}
