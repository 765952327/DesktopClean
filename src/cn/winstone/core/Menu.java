package cn.winstone.core;

import cn.winstone.config.Config;

import java.sql.SQLOutput;
import java.util.List;
import java.util.Scanner;

/**
 * @author Winstone
 * @date 2020/9/17 - 3:32 下午
 */
public class Menu {
	private Config config;
	private static final String sli = "----------------";

	public Menu(Config config) {
		this.config = config;
	}

	public Config.ConfigItem start() {
		System.out.println("是否开启默认清理?  yes/no");
		Scanner scanner = new Scanner(System.in);
		String s = scanner.nextLine();
		if (s.toLowerCase().equals("yes")) {
			this.printCurrConfig();
			List<Config.ConfigItem> configItems = config.getPath();
			System.out.println("请选择清理对象[输入序号]");
			int i = scanner.nextInt();
			if (i < 1 || i > configItems.size()) {
				System.out.println("输入不合法");
			}
			return configItems.get(i-1);
		}
		return null;
	}

	public void printCurrConfig() {
		List<Config.ConfigItem> configItems = this.config.getPath();
		System.out.println("请选择 清理对象");
		int i = 1;
		for (Config.ConfigItem configItem : configItems) {
			System.out.println(sli);
			System.out.println(i + "、" + configItem.getName());
			System.out.println("原路径：" + configItem.getSrc());
			System.out.println("新路径：" + configItem.getOut());
			System.out.println("是否整理文件夹：" + configItem.isHasDir());
			i++;
		}
	}
}
