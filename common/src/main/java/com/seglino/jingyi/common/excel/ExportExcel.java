package com.seglino.jingyi.common.excel;

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
	private String sheetName;
	private String title;
	private String[] headers;

	public byte[] exportExcel() {
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 创建工作簿
		HSSFSheet sheet = workbook.createSheet(sheetName);
		// 设置默认列宽为20
		sheet.setDefaultColumnWidth(20);

		// 创建标题
		HSSFRow titleRow = sheet.createRow(0);
		titleRow.setHeightInPoints(30);
		HSSFCell titleCell = titleRow.createCell(0);
		titleCell.setCellValue(title);
		titleCell.setCellStyle(getTitleCellStyle(workbook));
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, headers.length));

		return null;
	}

	// 标题样式
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
