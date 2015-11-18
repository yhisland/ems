/**
 * 
 */
package com.manager.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式验证
 * 
 * 
 */
public class RegexUtil {
	/** 验证联系电话 */
	private final static String REGEX_TELNUMBER = "^[\\d]{3,4}-?[\\d]{7,8}$";
	/** 验证价格 */
	private final static String REGEX_PRICE = "^[\\d]*\\.?[\\d]*$";

	/**
	 * 验证联系电话
	 * 
	 * @param telNumber
	 * @return
	 */
	public static boolean regexTelNumber(String telNumber) {
		if (telNumber == null || "".equals(telNumber.trim())) {
			return true;
		}
		Pattern ptn = Pattern.compile(REGEX_TELNUMBER);
		Matcher mch = ptn.matcher(telNumber);
		return mch.matches();
	}

	/**
	 * 验证价格
	 * 
	 * @param price
	 * @return
	 */
	public static boolean regexPrice(String price) {
		Pattern ptn = Pattern.compile(REGEX_PRICE);
		Matcher mch = ptn.matcher(price);
		return mch.matches();
	}
}
