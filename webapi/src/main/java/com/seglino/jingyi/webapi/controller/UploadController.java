package com.seglino.jingyi.webapi.controller;

import java.io.File;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.seglino.jingyi.common.response.ApiResult;
import com.seglino.jingyi.common.utils.ApplicationUtils;
import com.seglino.jingyi.common.utils.DateUtils;

@RestController()
@RequestMapping("upload")
public class UploadController {
	private final String rootPath = ApplicationUtils.getRootPath();
	private final String imagePath = rootPath + "/upload/image/" + DateUtils.getNowString("yyyyMMdd") + "/";

	@PostMapping("image")
	public ApiResult image(HttpServletRequest request) {
		ApiResult aResult = new ApiResult();
		try {
			MultipartHttpServletRequest multipart = (MultipartHttpServletRequest) request;
			Iterator<String> iterator = multipart.getFileNames();
			while (iterator.hasNext()) {
				MultipartFile file = multipart.getFile(iterator.next().toString());
				if (null != file) {
					String name=file.getOriginalFilename();
					String ext = name.substring(name.lastIndexOf("."));
					String path = imagePath + System.currentTimeMillis() + ext;
					file.transferTo(new File(path));
				}
			}
		} catch (Exception e) {
			aResult.addError(e);
		}
		return aResult;
	}
}
