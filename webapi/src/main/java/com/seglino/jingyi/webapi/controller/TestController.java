package com.seglino.jingyi.webapi.controller;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seglino.jingyi.assets.service.AssetsService;
import com.seglino.jingyi.common.excel.ExportExcel;
import com.seglino.jingyi.common.utils.DateUtils;

@RestController
@RequestMapping("test")
public class TestController {
	@Autowired
	private AssetsService assetsService;

	@GetMapping("export")
	public void export(HttpServletResponse response) {
		List<AssetsExport> list = new ArrayList<AssetsExport>();
		list.add(new AssetsExport(1, "测试1", "品牌1", "型号1", "2019-9-9 11:50:07"));
		list.add(new AssetsExport(2, "测试2", "品牌2", "型号2", "2019-9-9 11:50:07"));
		list.add(new AssetsExport(3, "测试3", "品牌3", "型号3", "2019-9-9 11:50:07"));
		list.add(new AssetsExport(4, "测试4", "品牌4", "", "2019-9-9 11:50:07"));
		Map<String, String> headMap = new HashMap<String, String>();
		headMap.put("code", "编码");
		headMap.put("name", "名称");
		headMap.put("brand", "品牌");
		headMap.put("model", "型号");
		headMap.put("date", "日期");
		ExportExcel<AssetsExport> ee = new ExportExcel<AssetsExport>("导出测试");
		XSSFWorkbook workbook = ee.exportExcel(list, headMap);
		try {
			String ext = "." + workbook.getWorkbookType().name();
			response.setHeader("Content-Disposition", "attachment;Filename=" + System.currentTimeMillis() + ext);
			workbook.write(response.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public class AssetsExport {
		public AssetsExport() {

		}

		public AssetsExport(long code, String name, String brand, String model, String date) {
			this.code = code;
			this.name = name;
			this.brand = brand;
			this.model = model;
			this.date = DateUtils.toDate(date);
		}

		private Object id;
		private Long code;
		private String name;
		private String brand;
		private String model;
		private Date date;

		public Object getId() {
			return id;
		}

		public void setId(Object id) {
			this.id = id;
		}

		public Long getCode() {
			return code;
		}

		public void setCode(Long code) {
			this.code = code;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getBrand() {
			return brand;
		}

		public void setBrand(String brand) {
			this.brand = brand;
		}

		public String getModel() {
			return model;
		}

		public void setModel(String model) {
			this.model = model;
		}

		public Date getDate() {
			return date;
		}

		public void setDate(Date date) {
			this.date = date;
		}
	}
}
