package cn.winstone.core;

import cn.winstone.log.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Winstone
 * @date 2020/9/17 - 2:55 下午
 * 文件路径扫描器
 */
public class FileScanner {
	public static List<String> getFilePaths(String path, Boolean hasDir){
		File dir = new File(path);
		if (!dir.exists()) {
			Logger.error("文件夹不存在");
			return null;
		}
		if (!dir.isDirectory()) {
			Logger.error("该路径不是文件夹");
			return null;
		}
		if (hasDir){
			return getAllFilePaths(dir);
		}
		return getFilePathsWithoutDir(dir);
	}

	/**
	 * 获取 文件夹下所有文件以及子文件夹下的所有文件 的路径
	 */
	private static List<String> getAllFilePaths(File dir) {
		List<String> filePaths = new ArrayList<>();
		File[] files = dir.listFiles();
		for (File file : files) {
			if (!file.isFile() && file.isDirectory()) {
				filePaths.addAll(getAllFilePaths(new File(file.getPath())));
			}
			if (file.isFile()){
				filePaths.add(file.getPath());
			}
		}
		return filePaths;
	}

	/**
	 * 获取文件夹下所有文件路径
	 */
	private static List<String> getFilePathsWithoutDir(File dir){
		List<String> filePaths = new ArrayList<>();
		File[] files = dir.listFiles();
		for (File file : files) {
			if (file.isFile()){
				filePaths.add(file.getPath());
			}
		}
		return filePaths;
	}
}
