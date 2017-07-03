package com.rongcapital.usercenter.provider.util;
import java.util.Date;

import org.joda.time.DateTime;

public class RkylinDateUtil {
	/**
	 * 比较日期大小
	 * @param largeDt
	 * @param littleDt
	 * @return
	 */
	public static final int compareDateTime(Date largeDt, Date littleDt){
		return (new DateTime(largeDt)).toString("yyyyMMddHHmmss")
				.compareTo((new DateTime(littleDt)).toString("yyyyMMddHHmmss"));
	}
	
	/**
	 * 获取到分钟格式的日期字符串
	 * @param date
	 * @return
	 */
	public static final String getMinuteTime(Date date){
		return new DateTime(date).toString("yyyyMMddHHmm");
	}
	
	/**
	 * 获取到秒格式的日期字符串
	 * @param date
	 * @return
	 */
	public static final String getSecondTime(Date date){
		return new DateTime(date).toString("yyyyMMddHHmmss");
	}
	
	/**
	 * 返回指定日期的零点整日期
	 * @param date
	 * @return
	 */
	public static final Date getDayZeroTime(Date date){
		DateTime dt = new DateTime(date);
		return (new DateTime(dt.getYear(),dt.getMonthOfYear(),dt.getDayOfMonth(),0,0)).toDate();
	}
	
	/**
	 * 获取前一日的同一时间
	 * @param date
	 * @return
	 */
	public static final Date getLastDayTime(Date date){
		DateTime dt = new DateTime(date);
		return dt.minusDays(1).toDate();
	}
	
	/**
	 * 获取几分钟前的同一时间
	 * @param date
	 * @param minutes
	 * @return
	 */
	public static final Date getPastMinutesTime(Date date, int minutes){
		DateTime dt = new DateTime(date);
		return dt.minusMinutes(minutes).toDate();
	}
	
	/**
	 * 当前时间加指定小时数
	 * @param date
	 * @param hours
	 * @return
	 */
	public static final Date plusHours(Date date, int hours){
		DateTime dt = new DateTime(date);
		return dt.plusHours(hours).toDate();
	}
	
	/**
	 * 当前时间加指定分钟数
	 * @param date
	 * @param minutes
	 * @return
	 */
	public static final Date plusMinutes(Date date, int minutes){
		DateTime dt = new DateTime(date);
		return dt.plusMinutes(minutes).toDate();
	}
}
