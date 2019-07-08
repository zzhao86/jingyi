package com.seglino.jingyi.common.utils;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类
 * 
 * @author zzhao
 *
 */
public class DateUtils {
	/**
	 * 获取当前日期字符串
	 * 
	 * @return 返回字符串格式，yyyy-MM-dd HH:mm:ss
	 */
	public static String getDate() {
		return getNowString("yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 获取当前日期字符串
	 * 
	 * @return 返回字符串格式，yyyy-MM-dd
	 */
	public static String getShortDate() {
		return getNowString("yyyy-MM-dd");
	}

	/**
	 * 获取当前日期字符串
	 * 
	 * @return 返回字符串格式，HH:mm:ss
	 */
	public static String getShortTime() {
		return getNowString("HH:mm:ss");
	}

	/**
	 * 获取当前日期字符串
	 * 
	 * @param format 格式化字符串，例如：yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String getNowString(String format) {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}

	/**
	 * 获取当前日期
	 * 
	 * @param format 格式化字符串，例如：yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static Date getDate(String date, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			return sdf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取当前日期
	 * 
	 * @return
	 */
	public static Date getNow() {
		return new Date();
	}

	/**
	 * 将日期类型转换为字符串
	 * 
	 * @param date   日期字段
	 * @param format 格式化字符串，例如：yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String toString(Date date, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}

	/**
	 * 将日期类型转换为字符串
	 * 
	 * @param date 日期字段
	 * @return
	 */
	public static String toString(Date date) {
		return toString(date, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 将日期字符串转换为日期类型
	 * 
	 * @param date 日期字符串
	 * @return
	 */
	public static Date toDate(String date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ParsePosition position = new ParsePosition(0);
		return sdf.parse(date, position);
	}

	/**
	 * 两个日期对象进行比较
	 * 
	 * @param date1 日期对象1
	 * @param date2 日期对象2
	 * @return 1：日期1大于日期2；-1：日期1小于日期2；0：两个日期相等
	 */
	public static int compare(Date date1, Date date2) {
		long dt1 = date1.getTime();
		long dt2 = date2.getTime();
		if (dt1 > dt2) {
			return 1;
		} else if (dt1 < dt2) {
			return -1;
		} else {
			return 0;
		}
	}

	/**
	 * 两个日期字符串进行比较
	 * 
	 * @param date1 日期字符串1
	 * @param date2 日期字符串2
	 * @return 1：日期1大于日期2；-1：日期1小于日期2；0：两个日期相等
	 */
	public static int compare(String date1, String date2) {
		return compare(toDate(date1), toDate(date2));
	}

	/**
	 * 与当前日期比较大小
	 * 
	 * @param date 比较的日期对象
	 * @return
	 */
	public static int compareNow(Date date) {
		return compare(getNow(), date);
	}

	/**
	 * 与当前日期比较大小
	 * 
	 * @param date 比较的日期字符串
	 * @return
	 */
	public static int compareNow(String date) {
		return compare(getNow(), toDate(date));
	}

	/**
	 * 日期年份加减
	 * 
	 * @param date   日期
	 * @param amount 加减数值，正数加负数减
	 * @return
	 */
	public static Date addYear(Date date, int amount) {
		return operationDate(date, amount, Calendar.YEAR);
	}

	/**
	 * 日期年份加减
	 * 
	 * @param amount 加减数值，正数加负数减
	 * @return
	 */
	public static Date addYear(int amount) {
		return addYear(null, amount);
	}

	/**
	 * 日期月份加减
	 * 
	 * @param date   日期
	 * @param amount 加减数值，正数加负数减
	 * @return
	 */
	public static Date addMonth(Date date, int amount) {
		return operationDate(date, amount, Calendar.MONTH);
	}

	/**
	 * 日期月份加减
	 * 
	 * @param amount 加减数值，正数加负数减
	 * @return
	 */
	public static Date addMonth(int amount) {
		return addMonth(null, amount);
	}

	/**
	 * 日期天数加减
	 * 
	 * @param date   日期
	 * @param amount 加减数值，正数加负数减
	 * @return
	 */
	public static Date addDay(Date date, int amount) {
		return operationDate(date, amount, Calendar.DAY_OF_MONTH);
	}

	/**
	 * 日期天数加减
	 * 
	 * @param amount 加减数值，正数加负数减
	 * @return
	 */
	public static Date addDay(int amount) {
		return addDay(null, amount);
	}

	/**
	 * 日期小时加减
	 * 
	 * @param date   日期
	 * @param amount 加减数值，正数加负数减
	 * @return
	 */
	public static Date addHour(Date date, int amount) {
		return operationDate(date, amount, Calendar.HOUR_OF_DAY);
	}

	/**
	 * 日期小时加减
	 * 
	 * @param amount 加减数值，正数加负数减
	 * @return
	 */
	public static Date addHour(int amount) {
		return addHour(null, amount);
	}

	/**
	 * 日期分钟加减
	 * 
	 * @param date   日期
	 * @param amount 加减数值，正数加负数减
	 * @return
	 */
	public static Date addMinute(Date date, int amount) {
		return operationDate(date, amount, Calendar.MINUTE);
	}

	/**
	 * 日期分钟加减
	 * 
	 * @param amount 加减数值，正数加负数减
	 * @return
	 */
	public static Date addMinute(int amount) {
		return addMinute(null, amount);
	}

	/**
	 * 日期秒数加减
	 * 
	 * @param date   日期
	 * @param amount 加减数值，正数加负数减
	 * @return
	 */
	public static Date addSecond(Date date, int amount) {
		return operationDate(date, amount, Calendar.SECOND);
	}

	/**
	 * 日期秒数加减
	 * 
	 * @param amount 加减数值，正数加负数减
	 * @return
	 */
	public static Date addSecond(int amount) {
		return addSecond(null, amount);
	}

	/**
	 * 日期加减运算
	 * 
	 * @param date   日期
	 * @param amount 加减数值
	 * @param type   日期字段（the calendar field）
	 * @return
	 */
	private static Date operationDate(Date date, int amount, int field) {
		Calendar calendar = Calendar.getInstance();
		if (date != null) {
			calendar.setTime(date);
		}
		calendar.add(field, amount);
		return calendar.getTime();
	}
}