/**
 * 
 */
package com.manager.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 */
public class DateUtil {
	
	/**默认的格式化方式*/
	private static final String defaultFormat = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 以指定格式返回当前时间的字符串表现形式
	 * @param format
	 * @return String
	 */
	public static String getCurrentFormatTime(String format) {
		Date date = new Date();
		date.setTime(System.currentTimeMillis());
		if (format == null || "".equals(format.trim())) {
			format = defaultFormat;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}

}
