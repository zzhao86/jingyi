package com.seglino.jingyi.webapi.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletResponse;

import org.jodconverter.JodConverter;
import org.jodconverter.office.LocalOfficeManager;
import org.jodconverter.office.OfficeException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seglino.jingyi.common.utils.ApplicationUtils;

@RestController
@RequestMapping("test")
public class TestController {

	@GetMapping("topdf")
	public void officToPdf(HttpServletResponse response) {
		String sourcepath = ApplicationUtils.getRootPath() + "/upload/file/20190815/a76953d6fa1a3e3ec135483c895f268d.docx";
		String targetpath = ApplicationUtils.getRootPath() + "/upload/file/test.pdf";
		LocalOfficeManager manager = null;
		File sourceFile = new File(sourcepath);
		File targetFile = new File(targetpath);
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			manager = LocalOfficeManager.builder().officeHome(getOfficHome()).install().build();
			manager.start();			
			JodConverter.convert(sourceFile).to(targetFile).execute();

			response.reset();
			response.setContentType("application/pdf");
			response.setCharacterEncoding("UTF-8");
			bis = new BufferedInputStream(new FileInputStream(targetFile));
			bos = new BufferedOutputStream(response.getOutputStream());
			
			byte[] buffer = new byte[1024];
			int length = 0;
			while ((length = bis.read(buffer)) != -1) {
				bos.write(buffer, 0, length);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != manager && manager.isRunning()) {
					manager.stop();
				}
				if (null != bos) {
					bos.close();
				}
			} catch (OfficeException | IOException e) {
				e.printStackTrace();
			}
		}
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
