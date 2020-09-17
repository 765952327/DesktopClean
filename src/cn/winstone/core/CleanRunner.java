package cn.winstone.core;

import cn.winstone.config.Config;
import cn.winstone.log.Logger;
import cn.winstone.utils.FileUtils;

import java.io.File;
import java.util.List;

/**
 * @author Winstone
 * @date 2020/9/17 - 2:20 下午
 */
public class CleanRunner {
	private Config config = new Config("/Users/weigaolei/CodeSpace/learn/DesktopClean/resource/config/application.conf");
	public void run() {
		doClean();
	}

	private Boolean doClean() {
		Logger.init("初始化中......");

		Logger.init("配置文件加载完成......");
		Logger.init("开始整理文件");
		Menu menu = new Menu(config);
		Config.ConfigItem configItem = menu.start();
		List<String> filePaths = FileScanner.getFilePaths(configItem.getSrc(), configItem.isHasDir());
		for (String path : filePaths) {
			if (ignore(path)){
				continue;
			}
			String kind = config.getFileKindByPath(path);
			String outPath = getOutPath(configItem.getOut(), kind);
			moveFile(path, outPath);
		}
		Logger.init("文件整理完成");
		return true;
	}

	private String getOutPath(String out, String kind) {
		if (out.endsWith(File.separator)) {
			out += kind;
		} else {
			out += File.separator + kind;
		}
		FileUtils.mkdir(out);
		return out;
	}

	private void moveFile(String source, String out) {
		File file = new File(source);
		if (file.isFile() && !file.isHidden()) {
			File toDir = new File(out);
			if (!toDir.exists()){
				toDir.mkdirs();
			}
			String outPath = out + File.separator + file.getName();
			if (!outPath.equals(source)){
				File toFile = new File(outPath);
				if (toFile.exists()){
					toFile = FileUtils.renameFile(toFile);
				}
				file.renameTo(toFile);
			}
		}
	}

	private boolean ignore(String path){
		File file = new File(path);
		String s = config.getFileKindByType(file.getName());
		return s.equals("忽略");
	}
}
