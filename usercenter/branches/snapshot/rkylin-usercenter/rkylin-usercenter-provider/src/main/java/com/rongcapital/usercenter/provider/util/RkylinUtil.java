package com.rongcapital.usercenter.provider.util;

import java.text.DecimalFormat;
import java.util.Arrays;

public class RkylinUtil {
	/**
	 * 将数字格式化成固定长度的字符串
	 * @param decimal
	 * @param format
	 * @return
	 */
	public static final String formatDecimal(Long decimal, String format){
		DecimalFormat df = new DecimalFormat(format); 
	    return df.format(decimal);
	}
	
	/**
	 * 将数字格式化成固定长度的字符串
	 * @param decimal
	 * @param format
	 * @return
	 */
	public static final String formatDecimal(Integer decimal, String format){
		DecimalFormat df = new DecimalFormat(format); 
	    return df.format(decimal);
	}
	
	/**
	 * 格式化固定长度字符串
	 * @param str
	 * @param len
	 * @param paddingChar
	 * @return
	 */
	public static final String fixString(String str, int len, char paddingChar) {
        if (str == null) {
            return null;
        }
        if (len <= 0) {
           return str;
        }
        if (str.length() >= len) {
            return str.substring(0, len);
        }
        char[] cs = new char[len];
        str.getChars(0, str.length(), cs, 0);
        Arrays.fill(cs, str.length(), len, paddingChar);
        return new String(cs);
    }
}
