package com.seglino.jingyi.common.response;

public class ApiPageResult extends ApiResult {
	private static final long serialVersionUID = 1377404371017239688L;

	private int pageCount;
	private long total;

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}
}
