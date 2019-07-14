package com.seglino.jingyi.common.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class ApiResult implements Serializable {
	private static final long serialVersionUID = -5069999896975225627L;

	private int code = 200;
	private boolean isSuccess;
	private Object data;
	private String message;
	private List<Exception> errors;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public boolean getIsSuccess() {
		if (errors == null || errors.size() == 0)
			return true;
		else
			return false;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getMessage() {
		StringBuilder sb = new StringBuilder();
		if (errors != null && errors.size() > 0) {
			for (Exception exception : errors) {
				sb.append(exception.getMessage() + "\n");
			}
		}
		return sb.toString();
	}

	public void AddError(String e) {
		if (code == 200) {
			code = 400;
		}
		AddError(new Exception(e));
	}

	public void AddError(Exception e) {
		if (code == 200) {
			code = 500;
		}
		if (errors == null)
			errors = new ArrayList<Exception>();
		errors.add(e);
	}

}