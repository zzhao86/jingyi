package com.seglino.jingyi.common.utils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class ExcelUtils {

	private final static String excel2003L = ".xls"; // 2003- 版本的excel
	private final static String excel2007U = ".xlsx"; // 2007+ 版本的excel

	/**
	 * 根据文件选择excel版本
	 * 
	 * @return
	 * @throws Exception
	 */
	private static Workbook chooseWorkbook(MultipartFile file) throws Exception {

		Workbook workbook = null;

		// 把MultipartFile转化为File
		CommonsMultipartFile cmf = (CommonsMultipartFile) file;
		DiskFileItem dfi = (DiskFileItem) cmf.getFileItem();
		File fo = dfi.getStoreLocation();

		String filename = file.getOriginalFilename();
		String fileType = (filename.substring(filename.lastIndexOf("."), filename.length())).toLowerCase();

		if (excel2003L.equals(fileType)) {
			workbook = new HSSFWorkbook(FileUtils.openInputStream(fo)); // 2003-
		} else if (excel2007U.equals(fileType)) {
			workbook = new XSSFWorkbook(FileUtils.openInputStream(fo)); // 2007+
		} else {
			throw new Exception("解析的文件格式有误！");
		}
		return workbook;
	}

	/**
	 * 导入excel方法
	 * 
	 * @param file 文件
	 * @param sheetIndex 工作簿索引
	 * @param clazz 实体类
	 * @return
	 * @throws IOException
	 */
	public static <T> List<T> importExcel(MultipartFile file, int sheetIndex, Class<T> clazz) throws IOException {
		List<T> list = new ArrayList<T>();
		Workbook workbook = null;
		try {
			// 读取文件内容
			workbook = chooseWorkbook(file);
			// 获取工作表
			Sheet sheet = workbook.getSheetAt(sheetIndex);
			list = importExcel(sheet, clazz);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != workbook)
				workbook.close();
		}
		return list;
	}

	/**
	 * 导入excel方法
	 * 
	 * @param file 文件
	 * @param sheetname 工作簿名称
	 * @param clazz 实体类
	 * @return
	 * @throws IOException
	 */
	public static <T> List<T> importExcel(MultipartFile file, String sheetname, Class<T> clazz) throws IOException {
		List<T> list = new ArrayList<T>();
		Workbook workbook = null;
		try {
			// 读取文件内容
			workbook = chooseWorkbook(file);
			// 获取工作表
			Sheet sheet = workbook.getSheet(sheetname);
			list = importExcel(sheet, clazz);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != workbook)
				workbook.close();
		}
		return list;
	}

	/**
	 * 导入excel方法
	 * 
	 * @param sheet		工作簿
	 * @param clazz		实体类
	 * @return
	 * @throws IOException
	 */
	private static <T> List<T> importExcel(Sheet sheet, Class<T> clazz) throws IOException {
		List<T> list = new ArrayList<T>();
		try {

			// 获取sheet中第一行行号
			int firstRowNum = sheet.getFirstRowNum();
			// 获取sheet中最后一行行号
			int lastRowNum = sheet.getLastRowNum();

			// 获取该实体所有定义的属性 返回Field数组
			Field[] entityName = clazz.getDeclaredFields();

			// 循环插入数据
			for (int i = firstRowNum + 1; i <= lastRowNum; i++) {

				Row row = sheet.getRow(i);

				// 可以根据该类名生成Java对象
				T pojo = clazz.newInstance();

				// 除自增编号外，实体字段匹配sheet列
				for (int j = 0; j < entityName.length - 3; j++) {

					// 获取属性的名字,将属性的首字符大写，方便构造set方法
					String name = "set" + entityName[j + 1].getName().substring(0, 1).toUpperCase()
							.concat(entityName[j + 1].getName().substring(1).toLowerCase()) + "";
					// 获取属性的类型
					String type = entityName[j + 1].getGenericType().toString();

					Method m = null;
					// getMethod只能调用public声明的方法，而getDeclaredMethod基本可以调用任何类型声明的方法
					m = clazz.getClass().getDeclaredMethod(name, entityName[j + 1].getType());

					Cell pname = row.getCell(j);
					// 根据属性类型装入值
					switch (type) {
					case "char":
					case "java.lang.Character":
					case "class java.lang.String":
						m.invoke(pojo, getValue(pname));
						break;
					case "int":
					case "class java.lang.Integer":
						m.invoke(pojo, Integer.valueOf(getValue(pname)));
						break;
					case "class java.util.Date":
						m.invoke(pojo, getValue(pname));
						break;
					case "float":
					case "double":
					case "java.lang.Double":
					case "java.lang.Float":
					case "java.lang.Long":
					case "java.lang.Short":
					case "java.math.BigDecimal":
						m.invoke(pojo, Double.valueOf(getValue(pname)));
						break;
					default:
						break;
					}
				}
				list.add(pojo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 处理类型
	 * 
	 * @param cell
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static String getValue(Cell cell) {
		if (null != cell) {
			switch (cell.getCellType()) {
			case NUMERIC: // 数字
				String val = cell.getNumericCellValue() + "";
				int index = val.indexOf(".");
				if (Integer.valueOf(val.substring(index + 1)) == 0) {
					DecimalFormat df = new DecimalFormat("0");// 处理科学计数法
					return df.format(cell.getNumericCellValue());
				}
				return cell.getNumericCellValue() + "";// double
			case STRING: // 字符串
				return cell.getStringCellValue() + "";
			case BOOLEAN: // Boolean
				return cell.getBooleanCellValue() + "";
			case FORMULA: // 公式
				try {
					if (HSSFDateUtil.isCellDateFormatted(cell)) {
						Date date = cell.getDateCellValue();
						return (date.getYear() + 1900) + "-" + (date.getMonth() + 1) + "-" + date.getDate();
					} else {
						return String.valueOf((int) cell.getNumericCellValue());
					}
				} catch (IllegalStateException e) {
					return String.valueOf(cell.getRichStringCellValue());
				}
			case BLANK: // 空值
				return "";
			case ERROR: // 故障
				return "";
			default:
				return "未知类型   ";
			}
		} else {
			return "";
		}
	}
}
