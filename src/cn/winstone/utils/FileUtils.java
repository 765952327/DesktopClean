package cn.winstone.utils;

import cn.winstone.log.Logger;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Winstone
 * @date 2020/9/17 - 12:01 下午
 */
public class FileUtils {
	/**
	 * 读取文件
	 *
	 * @param path
	 * @return
	 */
	public static File readFile(String path) {
		File file = new File(path);
		if (!file.exists()) {
			Logger.error("没有找到当前文件");
		}
		if (!file.isFile()) {
			Logger.error("当前路径不是文件");
		}
		return file;
	}

	/**
	 * 获取文件名
	 *
	 * @param file
	 * @return
	 */
	public static String getFileName(File file) {
		String fileName = file.getName();
		return fileName;
	}

	/**
	 * 获取文件名
	 *
	 * @param path
	 * @return
	 */
	public static String getFileName(String path) {
		File file = readFile(path);
		return getFileName(file);
	}

	/**
	 * 创建文件夹
	 *
	 * @param path
	 */
	public static void mkdir(String path) {
		File file = new File(path);
		if (!file.exists() && file.isDirectory()) {
			Logger.info("创建文件夹::" + path);
			file.mkdir();
		}
	}

	public static String mkdir(String baseUrl, String dirName) {
		File file = new File(baseUrl);
		if (!file.isDirectory()) {
			Logger.error(baseUrl + "::不是可用文件夹");
		}
		if (!file.exists()) {
			Logger.info(baseUrl + "::路径不存在");
		}
		String path = null;
		if (baseUrl.substring(baseUrl.length() - 1).equals("/")) {
			path = baseUrl + dirName;
		}
		path = baseUrl + File.separator + dirName;
		mkdir(path);
		return path;
	}

	public static String getFileType(String fileName) {
		String type = null;
		if (fileName.contains(".") && fileName.lastIndexOf(".") > 0) {
			type = fileName.substring(fileName.lastIndexOf("."));
		}
		return type;
	}

	public static String getFileTypeByPath(String path) {
		String fileName = getFileName(path);
		return getFileType(fileName);
	}

	public static String renameFile(String path) {
		int i = 1;
		File file = new File(path);
		String name = file.getName();
		while (file.isFile() && file.exists()) {
			String filename = name.substring(0, name.lastIndexOf("."));
			filename += "(" + i + ")";
			filename += getFileType(name);
			i++;
			path = path.substring(0, path.lastIndexOf("/") + 1) + filename;
			file = new File(path);
		}
		return file.getPath();
	}

	public static File renameFile(File file) {
		String filepath = renameFile(file.getPath());
		file = new File(filepath);
		return file;
	}

}
