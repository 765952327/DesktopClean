package cn.winstone.log;

import cn.winstone.utils.DateUtils;

/**
 * @author Winstone
 * @date 2020/9/17 - 12:05 下午
 */
public class Logger {
	public static void init(String msg){
		info(msg);
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}
	public static void error(String msg){
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder
				.append("[ERROR] \t")
				.append(DateUtils.getDefaultDate())
				.append(" ")
				.append(msg);
		System.err.println(stringBuilder.toString());
	}

	public static void info(String msg){
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder
				.append("[INFO] \t")
				.append(DateUtils.getDefaultDate())
				.append(" ")
				.append(msg);
		System.out.println(stringBuilder.toString());
	}
}
