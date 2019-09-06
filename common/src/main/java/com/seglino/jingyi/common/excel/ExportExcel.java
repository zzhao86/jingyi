package com.seglino.jingyi.common.excel;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;

public class ExportExcel<T> {
	/**
	 * 工作簿名称
	 */
	private String sheetName = "sheet1";
	/**
	 * 数据表格标题
	 */
	private String title;

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
	 * @param dataset 导出数据集合
	 * @param headMap 表格列名和数据字段名称对应Map。key：数据类字段名称，value：表格列名
	 * @return
	 */
	public byte[] exportExcel(List<T> dataset, Map<String, String> headMap) {
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 创建工作簿
		HSSFSheet sheet = workbook.createSheet(sheetName);
		// 设置默认列宽为20
		sheet.setDefaultColumnWidth(20);

		// 创建数据表格标题
		HSSFRow titleRow = sheet.createRow(0);
		titleRow.setHeightInPoints(30);
		HSSFCell titleCell = titleRow.createCell(0);
		titleCell.setCellValue(title);
		titleCell.setCellStyle(getTitleCellStyle(workbook));
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, headMap.size()));

		// 创建表格列名
		
		// 
		for (T t : dataset) {
			Field[] fields = t.getClass().getDeclaredFields();
			for (Field field : fields) {
				
			}
		}

		return workbook.getBytes();
	}

	// 数据表格标题样式
	private HSSFCellStyle getTitleCellStyle(HSSFWorkbook workbook) {
		HSSFCellStyle style = workbook.createCellStyle();
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		HSSFFont font = workbook.createFont();
		font.setBold(true);
		font.setFontHeightInPoints((short) 16);
		style.setFont(font);
		return style;
	}

}
