package com.seglino.jingyi.common.excel;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

public class ImportExcel<T> {
	/**
	 * 2003及以前版本的Excel
	 */
	private final static String excel2003L = ".xls";
	/**
	 * 2007及以后版本的Excel
	 */
	private final static String excel2007U = ".xlsx";

	/**
	 * 设置是否导入ID字段，默认false不导入
	 */
	protected boolean isImportID = false;
	/**
	 * 忽略导入的行数。默认为1，跳过标题行
	 */
	protected int ignoreRowCount = 1;
	/**
	 * 忽略导入的列数。默认为0，不跳过任何列
	 */
	protected int ignoreColumnCount = 0;

	/**
	 * 实例化
	 */
	public ImportExcel() {

	}

	/**
	 * 实例化
	 * 
	 * @param ignoreRowCount 忽略导入的行数
	 * @param ignoreColumnCount 忽略导入的列数
	 */
	public ImportExcel(int ignoreRowCount, int ignoreColumnCount) {
		this.ignoreRowCount = ignoreRowCount;
		this.ignoreColumnCount = ignoreColumnCount;
	}

	/**
	 * 实例化
	 * 
	 * @param isImportID 设置是否导入ID字段
	 * @param ignoreRowCount 忽略导入的行数
	 * @param ignoreColumnCount 忽略导入的列数
	 */
	public ImportExcel(boolean isImportID, int ignoreRowCount, int ignoreColumnCount) {
		this.isImportID = isImportID;
		this.ignoreRowCount = ignoreRowCount;
		this.ignoreColumnCount = ignoreColumnCount;
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
	public List<T> importExcel(MultipartFile file, int sheetIndex, Class<T> clazz) throws IOException {
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
	public List<T> importExcel(MultipartFile file, String sheetname, Class<T> clazz) throws IOException {
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
	 * @param sheet 工作簿
	 * @param clazz 实体类
	 * @return
	 * @throws IOException
	 */
	protected List<T> importExcel(Sheet sheet, Class<T> clazz) throws IOException {
		List<T> list = new ArrayList<T>();
		try {
			// 获取sheet中第一行行号
			int firstRowNum = sheet.getFirstRowNum();
			// 获取sheet中最后一行行号
			int lastRowNum = sheet.getLastRowNum();

			// 循环插入数据
			for (int i = firstRowNum; i < lastRowNum; i++) {
				if (i < ignoreRowCount)
					continue;

				Row row = sheet.getRow(i);
				T t = fillData(row, clazz);
				list.add(t);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 填充数据到实体类中
	 * 
	 * @param row Excel数据行
	 * @param clazz 实体类
	 * @return
	 * @throws Exception
	 */
	protected T fillData(Row row, Class<T> clazz) throws Exception {
		T t = clazz.newInstance();
		// 获取该实体所有定义的属性 返回Field数组
		Field[] entityName = t.getClass().getDeclaredFields();

		for (int i = 0; i < entityName.length; i++) {
			if ("id".equalsIgnoreCase(entityName[i].getName()) && !isImportID)
				continue;

			// 获取属性的名字,将属性的首字符大写，方便构造set方法
			String name = "set" + entityName[i].getName().substring(0, 1).toUpperCase().concat(entityName[i].getName().substring(1).toLowerCase()) + "";
			// 获取属性的类型
			String type = entityName[i].getGenericType().toString();

			// getMethod只能调用public声明的方法，而getDeclaredMethod基本可以调用任何类型声明的方法
			Method method = t.getClass().getDeclaredMethod(name, entityName[i].getType());

			Cell pname = row.getCell(i + ignoreColumnCount);
			// 根据属性类型装入值
			switch (type) {
			case "char":
			case "java.lang.Character":
			case "class java.lang.String":
				method.invoke(t, getCellValue(pname));
				break;
			case "int":
			case "class java.lang.Integer":
				method.invoke(t, Integer.valueOf(getCellValue(pname)));
				break;
			case "class java.util.Date":
				method.invoke(t, getCellValue(pname));
				break;
			case "float":
			case "double":
			case "java.lang.Double":
			case "java.lang.Float":
			case "java.lang.Long":
			case "java.lang.Short":
			case "java.math.BigDecimal":
				method.invoke(t, Double.valueOf(getCellValue(pname)));
				break;
			default:
				break;
			}
		}
		return t;
	}

	/**
	 * 获取单元格的值
	 * 
	 * @param cell
	 * @return
	 */
	@SuppressWarnings("deprecation")
	protected String getCellValue(Cell cell) {
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

	/**
	 * 根据文件选择Excel版本
	 * 
	 * @return
	 * @throws Exception
	 */
	private static Workbook chooseWorkbook(MultipartFile file) throws Exception {
		Workbook workbook = null;

		String filename = file.getOriginalFilename();
		String fileType = (filename.substring(filename.lastIndexOf("."), filename.length())).toLowerCase();

		if (excel2003L.equals(fileType)) {
			workbook = new HSSFWorkbook(file.getInputStream()); // 2003-
		} else if (excel2007U.equals(fileType)) {
			workbook = new XSSFWorkbook(file.getInputStream()); // 2007+
		} else {
			throw new Exception("解析的文件格式有误！");
		}
		return workbook;
	}
}
