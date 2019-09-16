package com.seglino.jingyi.common.log;

public enum LogType {
	/**
	 * 操作日志
	 */
	OPERATION(1),
	/**
	 * 异常日志
	 */
	THROWABLE(2);

	private int type;

	private LogType(int type) {
		this.type = type;
	}

	public int getType() {
		return type;
	}
}
