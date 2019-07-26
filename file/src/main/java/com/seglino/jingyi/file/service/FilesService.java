package com.seglino.jingyi.file.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.seglino.jingyi.common.core.service.BaseService;
import com.seglino.jingyi.file.dto.FileType;
import com.seglino.jingyi.file.pojo.Files;

public interface FilesService extends BaseService<Files> {

	/**
	 * 上传文件
	 * @param request
	 * @param type 上传文件的类型。image/file/radio/video
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	public List<Files> upload(HttpServletRequest request, FileType type) throws IllegalStateException, IOException;
}
