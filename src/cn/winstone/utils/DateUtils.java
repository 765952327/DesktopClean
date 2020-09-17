package cn.winstone.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Winstone
 * @date 2020/9/17 - 12:07 下午
 */
public class DateUtils {
	public static String getDefaultDate(){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return simpleDateFormat.format(new Date());
	}
}
