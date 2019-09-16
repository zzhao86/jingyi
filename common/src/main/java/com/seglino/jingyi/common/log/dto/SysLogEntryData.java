package com.seglino.jingyi.common.log.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import com.seglino.jingyi.common.core.vo.EntryData;

public class SysLogEntryData {

	/**
	 * 获取日志模块列表
	 * 
	 * @return
	 */
	public static List<EntryData> getTypeList() {
		List<EntryData> list = new ArrayList<EntryData>();
		list.add(new EntryData("操作日志", 1));
		list.add(new EntryData("错误日志", 2));
		return list;
	}

	/**
	 * 获取日志模块名称
	 * 
	 * @param type
	 * @return
	 */
	public static String getTypeName(int type) {
		Stream<EntryData> stream = getTypeList().stream().filter((EntryData e) -> e.getValue().equals(type));
		EntryData entry = stream.findAny().get();
		return entry.getLabel();
	}

	/**
	 * 获取日志模块值
	 * 
	 * @param name
	 * @return
	 */
	public static int getTypeValue(String name) {
		Stream<EntryData> stream = getTypeList().stream().filter((EntryData e) -> e.getLabel().equals(name));
		EntryData entry = stream.findAny().get();
		return (int)entry.getValue();
	}
}
