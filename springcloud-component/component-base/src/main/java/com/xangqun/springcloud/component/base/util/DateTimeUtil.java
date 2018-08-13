package com.xangqun.springcloud.component.base.util;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * @author liangxianyong
 * @date 2018年5月3日
 * @time 上午10:34:21
 */
public class DateTimeUtil {

	public final static String PATTERN_A = "yyyy-MM-dd";

	public final static String PATTERN_JQUERY = "dd/MM/yyyy";

	public final static String PATTERN_ZH = "yyyy年MM月dd日";

	public final static String PATTERN_B = "yyyyMMdd";

	public final static String PATTERN_C = "yyyy/MM/dd";

	public final static String PATTERN_D = "yyyyMM";

	public final static String PATTERN_E = "yyMM";

	public final static String PATTERN_LONG = "yyyy-MM-dd HH:mm:ss";

	public final static String PATTERN_LONG_MINUTE = "yyyy-MM-dd HH:mm";

	public final static String PATTERN_LONG_ZH = "yyyy年MM月dd日 HH时mm分ss秒";

	public final static String HMS_START = " 00:00:00";

	public final static String HMS_END = " 23:59:59";

	public final static String PATTERN_LONG_SLASH = "yyyy/MM/dd HH:mm:ss";

	/**1天的毫秒数*/
	public static final long DAY_OF_MILLIS = 24 * 3600 * 1000l;

	public static String toString( Date date ) {
	    return toString(date, PATTERN_LONG);
	}

	public static String toString( Date date, String datePattern ) {
		SimpleDateFormat format = new SimpleDateFormat( datePattern );
		return format.format( date );
	}

	public static Date fromString( String date ) {
		return fromString(date, PATTERN_LONG);
	}

	public static Date fromString( String date, String datePattern ) {
		try {
			SimpleDateFormat format = new SimpleDateFormat( datePattern );
			return format.parse( date );
		} catch( ParseException e ) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * 日期Date增加指定分钟数.
	 * @author liangxianyong  2016年7月4日 下午1:06:47
	 * @param startDate
	 * @param minute
	 * @return
	 */
	public static Date addTime(final Date startDate, final int minute) {
		long millis = startDate.getTime();
		millis = millis + (minute * 60L * 1000L);
		Date date = new Date(millis);
		return date;
	}
	/**
	 * 日期Date增加指定天数.
	 * @author liangxianyong  2016年7月4日 下午1:06:51
	 * @param date
	 * @param daynum
	 * @return
	 */
	public static Date addDate(final Date date, final int daynum) {
		int minute = daynum * 60 * 24;
		return addTime(date, minute);
	}
	
	public static final String yyyyMMdd = "yyyyMMdd";

	public final static Date getStartInDate(Date date) {
		String dateStr = toString(date, yyyyMMdd);
		return fromString(dateStr, yyyyMMdd);
	}
	public final static Date getEndInDate(Date date) {
		Date startDate = getStartInDate(date);
		return addDate(startDate, 1);
	}

}
