package com.seglino.jingyi.assets.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.seglino.jingyi.common.core.vo.EntryData;

public class AssetsEntryData {

	/**
	 * 获取资产状态列表
	 * 
	 * @return
	 */
	public static List<EntryData> getStatusList() {
		List<EntryData> list = new ArrayList<EntryData>();
		list.add(new EntryData("空闲", 100, false));
		list.add(new EntryData("领用待签字", 200, true));
		list.add(new EntryData("已领用", 300, false));
		list.add(new EntryData("退库待签字", 400, true));
		list.add(new EntryData("借用待签字", 500, true));
		list.add(new EntryData("借用中", 600, false));
		list.add(new EntryData("归还待签字", 700, true));
		list.add(new EntryData("调拨中", 800, false));
		list.add(new EntryData("待验收", 900, true));
		list.add(new EntryData("处置待确认", 1000, true));
		list.add(new EntryData("处置待审核", 1100, true));
		list.add(new EntryData("已处置", 1300, true));
		list.add(new EntryData("信息变更待审核", 1300, true));
		return list.stream().filter((EntryData e) -> !e.getDisabled()).collect(Collectors.toList());
	}

	/**
	 * 获取资产状态名称
	 * 
	 * @param status
	 * @return
	 */
	public static String getStatusName(int status) {
		Stream<EntryData> stream = getStatusList().stream().filter((EntryData e) -> e.getValue().equals(status));
		EntryData entry = stream.findAny().get();
		return entry.getLabel();
	}

	/**
	 * 获取资产状态值
	 * 
	 * @param name
	 * @return
	 */
	public static int getStatusValue(String name) {
		Stream<EntryData> stream = getStatusList().stream().filter((EntryData e) -> e.getLabel().equals(name));
		EntryData entry = stream.findAny().get();
		return (int) entry.getValue();
	}

	/**
	 * 获取资产使用状态列表
	 * 
	 * @return
	 */
	public static List<EntryData> getUseStatusList() {
		List<EntryData> list = new ArrayList<EntryData>();
		list.add(new EntryData("正常", 100));
		list.add(new EntryData("故障", 200));
		list.add(new EntryData("维修中", 300));
		return list;
	}

	/**
	 * 获取资产使用状态名称
	 * 
	 * @param status
	 * @return
	 */
	public static String getUseStatusName(int useStatus) {
		Stream<EntryData> stream = getUseStatusList().stream().filter((EntryData e) -> e.getValue().equals(useStatus));
		EntryData entry = stream.findAny().get();
		return entry.getLabel();
	}

	/**
	 * 获取资产使用状态值
	 * 
	 * @param name
	 * @return
	 */
	public static int getUseStatusValue(String name) {
		Stream<EntryData> stream = getUseStatusList().stream().filter((EntryData e) -> e.getLabel().equals(name));
		EntryData entry = stream.findAny().get();
		return (int) entry.getValue();
	}

	/**
	 * 获取购置方式列表
	 * 
	 * @return
	 */
	public static List<EntryData> getPurchasingMethodList() {
		List<EntryData> list = new ArrayList<EntryData>();
		list.add(new EntryData("采购", 1));
		list.add(new EntryData("租赁", 2));
		return list;
	}

	/**
	 * 获取购置方式名称
	 * 
	 * @param purchasingMethod
	 * @return
	 */
	public static String getPurchasingMethodName(int purchasingMethod) {
		Stream<EntryData> stream = getPurchasingMethodList().stream().filter((EntryData e) -> e.getValue().equals(purchasingMethod));
		EntryData entry = stream.findAny().get();
		return entry.getLabel();
	}

	/**
	 * 获取资产使用状态值
	 * 
	 * @param name
	 * @return
	 */
	public static int getPurchasingMethodValue(String name) {
		Stream<EntryData> stream = getPurchasingMethodList().stream().filter((EntryData e) -> e.getLabel().equals(name));
		EntryData entry = stream.findAny().get();
		return (int) entry.getValue();
	}
}
