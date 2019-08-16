package com.seglino.jingyi.common.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
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
			for (File file : files) {
				fos = new FileOutputStream(zipFile);
				zos = new ZipOutputStream(fos);
				compressByType(file, zos, "");
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
	private static void compressByType(File file, ZipOutputStream zos, String basePath) {
		if (!file.exists())
			return;
		// 判断压缩目标是文件还是文件夹
		if (file.isFile()) {
			// 压缩目标是文件
			compressFile(file, zos, basePath);
		} else if (file.isDirectory()) {
			// 压缩目标是文件夹
			compressDirectory(file, zos, basePath);
		}
	}

	/**
	 * 压缩文件
	 */
	private static void compressFile(File source, ZipOutputStream zos, String basePath) {
		BufferedInputStream bis = null;
		try {
			bis = new BufferedInputStream(new FileInputStream(source));
			ZipEntry entry = new ZipEntry(basePath + source.getName());
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
	private static void compressDirectory(File source, ZipOutputStream zos, String basePath) {
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
			compressByType(file, zos, path);
		}
	}
}
