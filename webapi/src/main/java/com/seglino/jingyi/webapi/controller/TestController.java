package com.seglino.jingyi.webapi.controller;

import java.io.File;
import java.util.regex.Pattern;

import org.jodconverter.JodConverter;
import org.jodconverter.office.LocalOfficeManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seglino.jingyi.common.utils.ApplicationUtils;

@RestController
@RequestMapping("test")
public class TestController {

	@GetMapping("topdf")
	public File officToPdf() {
		String filepath = ApplicationUtils.getRootPath() + "/upload/file/20190815/fee356d886882e3f1a6446930a70c372.xlsx";
		String sourcepath = ApplicationUtils.getRootPath() + "/upload/file/test.pdf";
		try {
			File file = new File(filepath);
			LocalOfficeManager manager = LocalOfficeManager.builder().officeHome(getOfficHome()).install().build();
			manager.start();
			JodConverter.convert(file).to(new File(sourcepath)).execute();			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private static String getOfficHome() {
		String osName = System.getProperty("os.name");
		if (Pattern.matches("Windows.*", osName)) {
			return "C:/Program Files (x86)/OpenOffice 4";
		} else if (Pattern.matches("Linux.*", osName)) {
			return "/usr/temp";
		} else if (Pattern.matches("Mac.*", osName)) {
			return "/Application/openOfficeSoft";
		}
		return null;
	}
}
