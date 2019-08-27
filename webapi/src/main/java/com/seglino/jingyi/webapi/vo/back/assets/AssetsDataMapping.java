package com.seglino.jingyi.webapi.vo.back.assets;

import java.util.HashMap;
import java.util.Map;

public class AssetsDataMapping {
	
	/**
	 * 资产状态列表
	 * @return
	 */
	public static Map<Integer, String> statusList(){
		Map<Integer, String> map = new HashMap<Integer, String>();
		map.put(100, "空闲");
		map.put(200, "领用待签字");
		map.put(300, "已领用");
		map.put(400, "退库待签字");
		map.put(500, "借用待签字");
		map.put(600, "借用中");
		map.put(700, "归还待签字");
		map.put(800, "调拨中");
		map.put(900, "待验收");
		map.put(1000, "处置待确认");
		map.put(1100, "处置待审核");
		map.put(1200, "信息变更待审核");
		return map;
	}

	/**
	 * 获取资产状态名称
	 * @param status
	 * @return
	 */
	public static String getStatusName(int status) {
		return statusList().get(status);
	}
	
	/**
	 * 资产使用状态列表
	 * @return
	 */
	public static Map<Integer, String> useStatusList(){																																																																																																																																		
		Map<Integer, String> map = new HashMap<Integer, String>();
		map.put(100, "正常");
		map.put(200, "故障");
		map.put(300, "维修中");																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																									map.put(300, "维修中");
		return map;
	}
	
	/**
	 * 获取资产使用状态名称
	 * @param status
	 * @return
	 */
	public static String getUseStatusName(int status) {
		return useStatusList().get(status);
	}
	
	/**
	 * 购置方式列表
	 * @return
	 */
	public static Map<Integer, String> purchasingMethodList(){
		Map<Integer, String> map = new HashMap<Integer, String>();
		map.put(1, "采购");
		map.put(2, "租赁");																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																								map.put(300, "维修中");
		return map;
	}
	
	/**
	 * 获取购置方式名称
	 * @param purchasingMethod
	 * @return
	 */
	public static String getPurchasingMethodName(int purchasingMethod) {
		return purchasingMethodList().get(purchasingMethod);
	}
}