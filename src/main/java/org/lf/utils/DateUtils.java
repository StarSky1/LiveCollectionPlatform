package org.lf.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
	private static DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	/**
	 * @return 系统时间：YYYY-MM-DD HH:mm:ss
	 */
	public static String getLongDate(Date date) {
		return toString("yyyy-MM-dd HH:mm:ss", date);
	}
	
	/**
	 * @return 系统时间：YYYY-MM-DD
	 */
	public static String getShortDate(Date date) {
		return toString("yyyy-MM-dd", date);
	}
	
	public static String toString(String format, Date date) {
		DateFormat df = new SimpleDateFormat(format);
		
		return df.format(date);
	}
	
	/**
	 * 将数据库中取出的int型，转换成换成format的格式字符串输出
	 * @param value
	 */
	public static String toString(String format, long value) {
		return toString(format, new Timestamp(value));
	}
	
	/**
	 * 将yyyy-MM-dd HH:mm:ss的格式字符串类型转换成Date型
	 * @param str
	 * @throws ParseException
	 */
	public static Date toDate(String str) throws ParseException {
		return sdf.parse(str);
	}
	
	/**
	 * 将Date转换成int型存入到数据库中
	 * @param date
	 */
	public static long toLong(Date date) {
		return date.getTime();
	}
	
	public static Date toDate(String format, String content) {
		DateFormat df = new SimpleDateFormat(format);
		Date date = null;

		try {
			date = df.parse(content);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return date;
	}
	
	public static int getYear(Date d) {
		Calendar c=Calendar.getInstance();
		c.setTime(d);
		return c.get(Calendar.YEAR) + 1900;
	}
	
	public static int getMonth(Date d) {
		Calendar c=Calendar.getInstance();
		c.setTime(d);
		return c.get(Calendar.MONTH) + 1;
	}
	
	public static Date getRandomDate(Date minDate, Date maxDate) {
		long min = minDate.getTime();
		long max = maxDate.getTime();
		if (min >= max) {
			throw new IllegalArgumentException("后面的时间要晚于前面的时间");
		}
		
		// 得到大于等于min小于max的double值
		// 将double值舍入为整数，转化成long类型
		return new Date((long) (Math.random() * (max - min)) + min);
	}
}
