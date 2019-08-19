package com.seglino.jingyi.common.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ZipUtils {
	private static Logger logger = LoggerFactory.getLogger(ZipUtils.class);

	/**
	 * 压缩文件或文件夹到ZIP包
	 * 
	 * @param files 文件列表
	 * @return ZIP包文件全路径
	 */
	public static File compress(String[] paths) {
		File[] files = new File[paths.length];
		for (int i = 0; i < paths.length; i++) {
			files[i] = new File(paths[i]);
		}
		return compress(files);
	}

	/**
	 * 压缩文件或文件夹到ZIP包
	 * 
	 * @param files 文件列表
	 * @return ZIP包文件全路径
	 */
	public static File compress(File[] files) {
		if (null == files || files.length == 0) {
			return null;
		}
		Map<String, File> map = new HashMap<String, File>();
		for (int i = 0; i < files.length; i++) {
			map.put(i + "*", files[i]);
		}
		return compress(map);
	}

	/**
	 * 压缩文件或文件夹到ZIP包
	 * 
	 * @param fileMap Key：文件名称，Value：文件对象
	 * @return
	 */
	public static File compress(Map<String, File> fileMap) {
		String path = ApplicationUtils.getRootPath() + "/upload/file/" + DateUtils.getNowString("yyyyMMdd") + "/";
		String fileName = UUID.randomUUID().toString().replace("-", "") + ".zip";
		String fileFullPath = path + fileName;

		File dir = new File(path);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		FileOutputStream fos = null;
		ZipOutputStream zos = null;
		try {
			File zipFile = new File(fileFullPath);
			fos = new FileOutputStream(zipFile);
			zos = new ZipOutputStream(fos);
			for (Entry<String, File> entry : fileMap.entrySet()) {
				String name = entry.getKey();
				File file = entry.getValue();
				if (name.contains("*")) {
					name = file.getName();
				}
				compressByType(file, name, zos, "");
			}
			return zipFile;
		} catch (IOException e) {
			logger.error("{}", e);
		} finally {
			try {
				if (null != zos)
					zos.close();
				if (null != fos)
					fos.close();
			} catch (IOException e) {
				logger.error("{}", e);
			}
		}
		return null;
	}

	/**
	 * 压缩目标类型，是文件或是文件夹
	 */
	private static void compressByType(File file, String fileName, ZipOutputStream zos, String basePath) {
		if (!file.exists())
			return;
		// 判断压缩目标是文件还是文件夹
		if (file.isFile()) {
			// 压缩目标是文件
			compressFile(file, fileName, zos, basePath);
		} else if (file.isDirectory()) {
			// 压缩目标是文件夹
			compressDirectory(file, fileName, zos, basePath);
		}
	}

	/**
	 * 压缩文件
	 */
	private static void compressFile(File source, String fileName, ZipOutputStream zos, String basePath) {
		BufferedInputStream bis = null;
		try {
			bis = new BufferedInputStream(new FileInputStream(source));
			ZipEntry entry = new ZipEntry(basePath + fileName);
			zos.putNextEntry(entry);

			int count;
			byte[] buffer = new byte[1024];
			while ((count = bis.read(buffer)) != -1) {
				zos.write(buffer, 0, count);
			}
		} catch (IOException e) {
			logger.error("{}", e);
		} finally {
			try {
				if (null != bis) {
					bis.close();
				}
			} catch (IOException e) {
				logger.error("{}", e);
			}
		}
	}

	/**
	 * 压缩文件夹
	 */
	private static void compressDirectory(File source, String fileName, ZipOutputStream zos, String basePath) {
		String path = basePath + source.getName() + File.separator;
		File[] files = source.listFiles();
		if (files.length == 0) {
			try {
				ZipEntry entry = new ZipEntry(path);
				zos.putNextEntry(entry);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		for (File file : files) {
			compressByType(file, fileName, zos, path);
		}
	}
}
