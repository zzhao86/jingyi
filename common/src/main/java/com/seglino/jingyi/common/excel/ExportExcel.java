package com.seglino.jingyi.common.excel;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExportExcel<T> {
	/**
	 * 工作簿名称
	 */
	private String sheetName = "sheet1";
	/**
	 * 数据表格标题
	 */
	private String title = "导出数据";

	/**
	 * 表格是否需要添加边框
	 */
	private boolean isBorder = true;

	public ExportExcel() {
	}

	public ExportExcel(String title) {
		this.title = title;
	}

	public ExportExcel(String sheetName, String title) {
		this.sheetName = sheetName;
		this.title = title;
	}

	/**
	 * 导出数据到Excel
	 * 
	 * @param dataset 导出数据集合
	 * @param headMap 表格列名和数据字段名称对应Map。key：数据类字段名称，value：表格列名
	 * @return
	 */
	public XSSFWorkbook exportExcel(List<T> dataset, Map<String, String> headMap) {
		XSSFWorkbook workbook = new XSSFWorkbook();
		// 创建工作簿
		XSSFSheet sheet = workbook.createSheet(sheetName);
		// 设置默认列宽为20
		sheet.setDefaultColumnWidth(20);

		// 创建数据表格标题
		XSSFRow titleRow = sheet.createRow(0);
		titleRow.setHeightInPoints(50);
		XSSFCell titleCell = titleRow.createCell(0);
		titleCell.setCellValue(title);
		titleCell.setCellStyle(getTitleCellStyle(workbook));
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, headMap.size() - 1));

		// 创建表格列名
		XSSFRow headRow = sheet.createRow(1);
		headRow.setHeightInPoints(30);
		int index = 0;
		for (Entry<String, String> entry : headMap.entrySet()) {
			XSSFCell headCell = headRow.createCell(index++);
			headCell.setCellValue(entry.getValue());
			headCell.setCellStyle(getHeadCellStyle(workbook));
		}

		// 创建导出数据
		int dataRowIndex = 2;
		for (T t : dataset) {
			int dataCellIndex = 0;
			XSSFRow dataRow = sheet.createRow(dataRowIndex++);
			dataRow.setHeightInPoints(20);
			for (Entry<String, String> entry : headMap.entrySet()) {
				Method[] methods = t.getClass().getMethods();
				XSSFCell dataCell = dataRow.createCell(dataCellIndex++);
				for (Method method : methods) {
					String getMethodName = "get" + entry.getKey().substring(0, 1).toUpperCase() + entry.getKey().substring(1);
					if (getMethodName.equals(method.getName())) {
						try {
							String typeName = method.getReturnType().getName();
							Object value = method.invoke(t);
							XSSFCellStyle style = getDataCellStyle(workbook);
							if (null != value) {
								switch (typeName) {
								case "java.lang.Integer":
									dataCell.setCellValue((Integer) value);
									break;
								case "java.lang.Long":
									dataCell.setCellValue((Long) value);
									break;
								case "java.lang.Short":
									dataCell.setCellValue((Short) value);
									break;
								case "java.lang.Double":
									dataCell.setCellValue((Double) value);
									break;
								case "java.lang.Float":
									dataCell.setCellValue((Float) value);
									break;
								case "java.util.Date":
									dataCell.setCellValue((Date) value);
									style.setDataFormat(workbook.createDataFormat().getFormat("yyyy/mm/dd"));
									break;
								default:
									dataCell.setCellValue(value + "");
									break;
								}
							} else {
								dataCell.setBlank();
							}
							dataCell.setCellStyle(style);
							break;
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		return workbook;
	}

	/**
	 * 表格标题样式
	 * 
	 * @param workbook
	 * @return
	 */
	private XSSFCellStyle getTitleCellStyle(XSSFWorkbook workbook) {
		XSSFCellStyle style = getBaseCellStyle(workbook);
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		XSSFFont font = workbook.createFont();
		font.setBold(true);
		font.setFontHeightInPoints((short) 16);
		style.setFont(font);
		return style;
	}

	/**
	 * 表格列头单元格样式
	 * 
	 * @param workbook
	 * @return
	 */
	private XSSFCellStyle getHeadCellStyle(XSSFWorkbook workbook) {
		XSSFCellStyle style = getBaseCellStyle(workbook);
		style.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		XSSFFont font = workbook.createFont();
		font.setBold(true);
		font.setFontHeightInPoints((short) 11);
		style.setFont(font);
		return style;
	}

	/**
	 * 表格表格数据单元格样式
	 * 
	 * @param workbook
	 * @return
	 */
	private XSSFCellStyle getDataCellStyle(XSSFWorkbook workbook) {
		XSSFCellStyle style = getBaseCellStyle(workbook);

		return style;
	}

	/**
	 * 基础样式
	 * 
	 * @param workbook
	 * @return
	 */
	private XSSFCellStyle getBaseCellStyle(XSSFWorkbook workbook) {
		XSSFCellStyle style = workbook.createCellStyle();
		if (isBorder) {
			style.setBorderTop(BorderStyle.THIN);
			style.setBorderRight(BorderStyle.THIN);
			style.setBorderBottom(BorderStyle.THIN);
			style.setBorderLeft(BorderStyle.THIN);
		}
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		return style;
	}

	/**
	 * 获取工作簿名称
	 * 
	 * @return
	 */
	public String getSheetName() {
		return sheetName;
	}

	/**
	 * 设置工作簿名称
	 * 
	 * @param sheetName
	 */
	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

	/**
	 * 获取数据表格标题
	 * 
	 * @return
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * 设置数据表格标题
	 * 
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * 获取是否添加边框
	 * 
	 * @return
	 */
	public boolean getIsBorder() {
		return isBorder;
	}

	/**
	 * 设置是否添加边框
	 * 
	 * @param isBorder
	 */
	public void setIsBorder(boolean isBorder) {
		this.isBorder = isBorder;
	}
}
