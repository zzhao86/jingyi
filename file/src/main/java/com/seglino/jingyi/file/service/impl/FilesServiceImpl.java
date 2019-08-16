package com.seglino.jingyi.file.service.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.seglino.jingyi.common.core.service.BaseServiceImpl;
import com.seglino.jingyi.common.encrypt.EncryptUtils;
import com.seglino.jingyi.common.utils.ApplicationUtils;
import com.seglino.jingyi.common.utils.DateUtils;
import com.seglino.jingyi.common.utils.ZipUtils;
import com.seglino.jingyi.file.dao.FilesDao;
import com.seglino.jingyi.file.dto.FileType;
import com.seglino.jingyi.file.pojo.Files;
import com.seglino.jingyi.file.service.FilesService;

@Service
public class FilesServiceImpl extends BaseServiceImpl<FilesDao, Files> implements FilesService {
	private final String rootPath = ApplicationUtils.getRootPath();
	private String relativePath = "/upload/{0}/" + DateUtils.getNowString("yyyyMMdd") + "/";

	/**
	 * 上传文件
	 * 
	 * @param request
	 * @param type 上传文件的类型。image/file/radio/video
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	@Override
	public List<Files> upload(HttpServletRequest request, FileType type) throws IllegalStateException, IOException {
		relativePath = MessageFormat.format(relativePath, type.getType());
		List<Files> list = new ArrayList<Files>();
		// 判断文件夹是否存在，不存在则创建
		File dir = new File(rootPath + relativePath);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		// 上传文件
		MultipartHttpServletRequest multipart = (MultipartHttpServletRequest) request;
		Iterator<String> iterator = multipart.getFileNames();
		while (iterator.hasNext()) {
			MultipartFile multipartFile = multipart.getFile(iterator.next());
			if (null != multipartFile) {
				String md5 = EncryptUtils.getFileMD5(multipartFile.getInputStream());
				String name = multipartFile.getOriginalFilename();
				long size = multipartFile.getSize();
				String ext = name.substring(name.lastIndexOf("."));
				String path = rootPath + relativePath;
				String fileName = md5 + ext;

				// 查找数据库中有没有MD5相同的文件
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("MD5", md5);
				param.put("isDeleted", false);
				Files files = this.detail(param);
				if (null == files) {
					multipartFile.transferTo(new File(path + fileName));

					// 上传文件信息插入数据库
					files = new Files();
					files.setName(name);
					files.setType(ext);
					files.setSize(size);
					files.setMD5(md5);

					files.setUrl(relativePath + fileName);
					int count = this.insert(files);
					if (count == 1) {
						list.add(files);
					}
				} else {
					File file = new File(rootPath + files.getUrl());
					if (!file.exists()) {
						multipartFile.transferTo(file);
					}
					files.setName(name);
					list.add(files);
				}
			}

		}
		return list;
	}

	/**
	 * 下载文件
	 * 
	 * @param url 文件相对路径
	 * @param fileName 文件名称
	 * @param response
	 */
	public void download(String url, String fileName, HttpServletResponse response) {
		String sourceFilePath = ApplicationUtils.getRootPath() + url;

		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			response.reset();
			response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
			response.setCharacterEncoding("UTF-8");

			File file = new File(sourceFilePath);
			bis = new BufferedInputStream(new FileInputStream(file));
			bos = new BufferedOutputStream(response.getOutputStream());

			byte[] buffer = new byte[1024];
			int length;
			while ((length = bis.read(buffer)) != -1) {
				bos.write(buffer, 0, length);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != bis)
					bis.close();
				if (null != bos)
					bos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 下载ZIP文件
	 * 
	 * @param url 文件相对路径
	 * @param fileName 文件名称
	 * @param response
	 */
	public void downloadZip(String url, String fileName, HttpServletResponse response) {
		String sourceFilePath = ApplicationUtils.getRootPath() + url;
		BufferedInputStream bis = null;
		ZipOutputStream zos = null;
		try {
			String zipFileName = fileName.substring(0, fileName.lastIndexOf(".")) + ".zip";
			response.reset();
			response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(zipFileName, "UTF-8"));
			response.setCharacterEncoding("UTF-8");

			File file = new File(sourceFilePath);
			bis = new BufferedInputStream(new FileInputStream(file));
			zos = new ZipOutputStream(response.getOutputStream());
			zos.putNextEntry(new ZipEntry(fileName));
			int length;
			byte[] buffer = new byte[1024];
			while ((length = bis.read(buffer)) != -1) {
				zos.write(buffer, 0, length);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != bis)
					bis.close();
				if (null != zos)
					zos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
