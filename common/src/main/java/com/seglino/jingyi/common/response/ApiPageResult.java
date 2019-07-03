package com.seglino.jingyi.common.response;

public class ApiPageResult extends ApiResult {
	private static final long serialVersionUID = 1377404371017239688L;

	private long total;

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}
}
