package com.seglino.jingyi.common.log.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.seglino.jingyi.common.core.vo.EntryData;
import com.seglino.jingyi.common.log.LogType;

public class SysLogEntryData {

	/**
	 * 获取日志类型列表
	 * 
	 * @return
	 */
	public static List<EntryData> getTypeList() {
		List<EntryData> list = new ArrayList<EntryData>();
		list.add(new EntryData("普通操作", LogType.OPERATION.getType()));
		list.add(new EntryData("操作异常", LogType.THROWABLE.getType()));
		list.add(new EntryData("登录后台", LogType.BACK_LOGIN.getType()));
		list.add(new EntryData("退出后台", LogType.BACK_LOGOUT.getType()));
		list.add(new EntryData("登录客户端", LogType.CLIENT_LOGIN.getType()));
		list.add(new EntryData("退出客户端", LogType.CLIENT_LOGOUT.getType()));
		return list.stream().filter((EntryData e) -> !e.getDisabled()).collect(Collectors.toList());
	}

	/**
	 * 获取日志类型名称
	 * 
	 * @param type
	 * @return
	 */
	public static String getTypeName(String type) {
		Stream<EntryData> stream = getTypeList().stream().filter((EntryData e) -> e.getValue().equals(type));
		EntryData entry = stream.findAny().get();
		return entry.getLabel();
	}

	/**
	 * 获取日志类型值
	 * 
	 * @param name
	 * @return
	 */
	public static String getTypeValue(String name) {
		Stream<EntryData> stream = getTypeList().stream().filter((EntryData e) -> e.getLabel().equals(name));
		EntryData entry = stream.findAny().get();
		return entry.getValue().toString();
	}
}
