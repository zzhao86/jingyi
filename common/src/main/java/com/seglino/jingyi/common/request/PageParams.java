package com.seglino.jingyi.common.request;

import java.io.Serializable;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONObject;

public class PageParams implements Serializable {
	private static final long serialVersionUID = -1369080387086490091L;

	private int pageNum = 1;
	private int pageSize = 15;
	private String condition;
	private Map<String, Object> params = null;

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public Map<String, Object> getParams() {
		if (!StringUtils.isEmpty(condition)) {
			return JSONObject.parseObject(condition, Map.class);
		}
		return params;
	}

}
