package com.seglino.jingyi.webapi.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seglino.jingyi.assets.service.AssetsService;
import com.seglino.jingyi.common.excel.ExportExcel;

@RestController
@RequestMapping("test")
public class TestController {
	@Autowired
	private AssetsService assetsService;

	@GetMapping("export")
	public void export(HttpServletResponse response) {
		assetsService.list(new HashMap<String, Object>());
		ExportExcel<AssestExport> ee = new ExportExcel<AssestExport>();
//		ee.exportExcel();
	}

	public class AssestExport {
		private Object id;
		private Long code;
		private String name;
		private String brand;
		private String model;

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
	}
}
