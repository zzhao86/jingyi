package com.seglino.jingyi.file.service.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jodconverter.JodConverter;
import org.jodconverter.office.LocalOfficeManager;
import org.jodconverter.office.OfficeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
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
		String sourceFilePath = rootPath + url;
		this.download(new File(sourceFilePath), fileName, response);
	}

	/**
	 * 下载文件
	 * 
	 * @param file 文件对象
	 * @param fileName 文件名称
	 * @param response
	 */
	public void download(File file, String fileName, HttpServletResponse response) {

		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			response.reset();
			response.setContentType("application/octet-stream");
			response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
			response.setCharacterEncoding("UTF-8");

			bis = new BufferedInputStream(new FileInputStream(file));
			bos = new BufferedOutputStream(response.getOutputStream());

			byte[] buffer = new byte[1024];
			int length;
			while ((length = bis.read(buffer)) != -1) {
				bos.write(buffer, 0, length);
			}
		} catch (IOException e) {
			logger.error("{}", e);
		} finally {
			try {
				if (null != bis)
					bis.close();
				if (null != bos)
					bos.close();
			} catch (IOException e) {
				logger.error("{}", e);
			}
		}
	}

	/**
	 * 下载ZIP文件
	 * 
	 * @param fileMap key：文件名称，value：文件路径
	 * @param fileName 文件名称
	 * @param response
	 */
	public void downloadZip(Map<String, String> fileMap, String fileName, HttpServletResponse response) {
		BufferedInputStream bis = null;
		ZipOutputStream zos = null;
		File zipFile = null;
		try {
			response.reset();
			response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
			response.setCharacterEncoding("UTF-8");

			zos = new ZipOutputStream(response.getOutputStream());

			Map<String, File> zipFileMap = new HashMap<String, File>();
			for (Entry<String, String> entry : fileMap.entrySet()) {
				File file = new File(rootPath + entry.getValue());
				if (file.exists()) {
					zipFileMap.put(entry.getKey(), file);
				}
			}
			zipFile = ZipUtils.compress(zipFileMap);
			this.download(zipFile, fileName, response);

		} catch (IOException e) {
			logger.error("{}", e);
		} finally {
			try {
				if (null != bis)
					bis.close();
				if (null != zos)
					zos.close();
				if (null != zipFile && zipFile.exists()) {
					zipFile.delete();
				}
			} catch (IOException e) {
				logger.error("{}", e);
			}
		}
	}

	/**
	 * office文档转PDF
	 * 
	 * @param path 文档路径
	 * @return PDF文档路径
	 */
	public String toPDF(String path) {
		// PDF文档路径
		String pdfPath = path.substring(0, path.lastIndexOf(".")) + ".pdf";
		// 源文件扩展名
		String ext = path.substring(path.lastIndexOf(".") + 1);
		// 可以转换PDF的文件类型
		String types = "doc,docx,xls,xlsx,ppt,pptx";
		// 判断扩展名是否是office文档
		if (!types.contains(ext)) {
			return null;
		}

		File pdfFile = new File(rootPath + pdfPath);
		if (pdfFile.exists()) {
			return pdfPath;
		}
		File sourceFile = new File(rootPath + path);

		LocalOfficeManager manager = LocalOfficeManager.builder().officeHome(getOfficHome()).install().build();
		try {
			manager.start();
			JodConverter.convert(sourceFile).to(pdfFile).execute();
		} catch (OfficeException e) {
			logger.error("{}", e);
		} finally {
			if (null != manager && manager.isRunning()) {
				try {
					manager.stop();
				} catch (OfficeException e) {
					logger.error("{}", e);
				}
			}
		}
		return pdfPath;
	}

	/**
	 * 获取OpenOffice安装目录
	 * 
	 * @return
	 */
	private String getOfficHome() {
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
