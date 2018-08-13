/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.xangqun.springcloud.component.base.util;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Months;
import org.joda.time.format.DateTimeFormat;

import java.math.BigDecimal;
import java.util.*;

/**
 * The common date/time utils.
 * 
 * @author douguoqiang
 * @since 2018年1月15日
 */
public class DateUtil {
	public static final String DATE_FMT_YMD = "yyyyMMdd";
	public static final String DATE_FMT_Y_M_D = "yyyy-MM-dd";
	public static final String DATE_FMT_YMDHMSSSSS = "yyyyMMddHHmmssSSS";
	public static final String DATE_FMT_Y_M_D_HMSS = "yyyy-MM-dd HH:mm:ss";
	public static final String DATE_FMT_YMDHMS = "yyyyMMddHHmmss";
	public static final String DATE_FMT_YMDH = "yyyyMMddHH";
	public static final String BATCH_NUMBER_FMT_YMDHM = "yyMMddHHmm";
	public static final String DATE_FMT_HMS = "HHmmss";
	public static final String DATE_FMT_Y_M_D_HMS = DATE_FMT_Y_M_D_HMSS;
	public static final String DATE_SHORT_FMT_YMDHM = BATCH_NUMBER_FMT_YMDHM;
	public static final String DATE_SHORT_FMT_YMD = "yyMMdd";
	public static final String DATE_SHORT_FMT_YM = "yyMM";
	public static final String DATE_FMT_YM = "yyyyMM";
	public static final String DATE_FMT_YYYY = "yyyy";
	public static final String DATE_FMT_Y_M_D_HMSSS = "yyyy-MM-dd HH:mm:ss.SSS";

	private static long MILLIS_PER_DAY = 24 * 60 * 60 * 1000L;
	private static final double DAYS_PER_YEAR = 365.25;

	public static long getOneDayTimeMillseconds() {
		return MILLIS_PER_DAY;
	}

	/**
	 * just use this method for test, in which case you need change a day length
	 * 
	 * @param testValue
	 */
	public static void setOneDayTimeMillSeconds(long testValue) {
		MILLIS_PER_DAY = testValue;
	}

	/**
	 * Formats {@link Date} to string according to the specific format
	 * passed in.
	 *
	 * @param date
	 *            {@link Date}
	 * @param format
	 *            refer to {@link org.joda.time.format.DateTimeFormat}
	 * @return the formatted date string.
	 */
	public static String getDate(Date date, String format) {
		return new DateTime(date.getTime()).toString(format);
	}

	/**
	 * Formats {@link Date} to string as "yyyyMMdd".
	 *
	 * @param date
	 *            {@link Date}
	 * @return the formatted date string.
	 */
	public static String getDate(Date date) {
		return new DateTime(date.getTime()).toString(DATE_FMT_YMD);
	}

	/**
	 * Parses a date string.
	 *
	 * @param dateString
	 *            the date string
	 * @throws UnsupportedOperationException
	 *             if parsing is not supported
	 * @throws IllegalArgumentException
	 *             if the text to parse is invalid
	 */
	public static Date parseStringToDate(String dateString) {
		return DateTime.parse(dateString).toDate();
	}

	/**
	 * Calculates months between two date.
	 *
	 * @param from
	 *            the from date
	 * @param to
	 *            the to date
	 * @return the number of months
	 */
	public static int calculateMonthIn(Date from, Date to) {
		return Months.monthsBetween(new DateTime(from), new DateTime(to))
				.getMonths();
	}

	public static long daysBetween(Date startDate, Date endDate) {
		Calendar start = new GregorianCalendar();
		start.setTime(startDate);
		Calendar end = new GregorianCalendar();
		end.setTime(endDate);
		return daysBetween(start, end);
	}

	public static long daysBetween(Calendar startDate, Calendar endDate) {
		Calendar date = (Calendar) startDate.clone();
		long daysBetween = 0;
		while (date.before(endDate)) {
			date.add(Calendar.DAY_OF_MONTH, 1);
			daysBetween++;
		}
		return daysBetween;
	}

	public static long monthsBetween(Date startDate, Date endDate) {
		Calendar start = new GregorianCalendar();
		start.setTime(startDate);
		Calendar end = new GregorianCalendar();
		end.setTime(endDate);
		return monthsBetween(start, end);
	}

	public static long monthsBetween(Calendar startDate, Calendar endDate) {
		Calendar date = (Calendar) startDate.clone();
		long daysBetween = 0;
		while (date.before(endDate)) {
			date.add(Calendar.MONTH, 1);
			daysBetween++;
		}

		return daysBetween;
	}

	public static long yearsBetween(Date startDate, Date endDate) {
		Calendar start = new GregorianCalendar();
		start.setTime(startDate);
		Calendar end = new GregorianCalendar();
		end.setTime(endDate);
		return yearsBetween(start, end);
	}

	public static long yearsBetween(Calendar startDate, Calendar endDate) {
		Calendar date = (Calendar) startDate.clone();
		long yearsBetween = 0;
		date.add(Calendar.YEAR, 1);
		while (date.before(endDate)) {
			date.add(Calendar.YEAR, 1);
			yearsBetween++;
		}

		return yearsBetween;
	}

	public static Date getFutureNormalizedDay(int numOfdays) {
		int actualNumOfDays = numOfdays - 1;
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, actualNumOfDays);
		return normalizeDay(cal.getTime());
	}

	public static Date normalizeDay(Date date) {
		return setTime(date, 0, 0, 0);
	}

	public static Date normalizeMonth(Date date) {
		if (date == null)
			return null;

		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, 1);

		return normalizeDay(cal.getTime());
	}

	public static Date normalizeYear(Date date) {
		if (date == null)
			return null;
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.set(Calendar.MONTH, Calendar.JANUARY);

		return normalizeMonth(cal.getTime());
	}

	public static Date setTime(Date baseDate, int hourOfDay, int min, int sec) {
		if (baseDate == null)
			return null;

		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(baseDate);
		cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
		cal.set(Calendar.MINUTE, min);
		cal.set(Calendar.SECOND, sec);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	/**
	 * Returns true if <code>targetDate</code> is in the range of [
	 * <code>startDate</code>, <code>endDate</code>], otherwise false.
	 *
	 * @param startDate
	 *            start date.
	 * @param endDate
	 *            end date.
	 * @param targetDate
	 *            the date to be calculated
	 * @return true if <code>targetDate</code> is in the range of [
	 *         <code>startDate</code>, <code>endDate</code>], otherwise false.
	 */
	public static boolean isBetween(Date startDate, Date endDate,
			Date targetDate) {
		if (startDate == null || endDate == null || targetDate == null) {
			throw new NullPointerException("Parameter date shouldn't be null");
		}
		return startDate.getTime() <= targetDate.getTime()
				&& targetDate.getTime() <= endDate.getTime();
	}

	/**
	 * Returns the count of days between <code>startDate</code> and
	 * <code>endDate</code>.
	 *
	 * @param startDate
	 *            start date
	 * @param endDate
	 *            end date
	 * @return the count of days between <code>startDate</code> and
	 *         <code>endDate</code>.
	 */
	public static long diffDays(Date startDate, Date endDate) {
		if (startDate == null || endDate == null) {
			throw new NullPointerException("Parameter date shouldn't be null");
		}
		return (endDate.getTime() - startDate.getTime()) / MILLIS_PER_DAY;
	}

	/**
	 * return the date of <code>days</code> before now.
	 *
	 * @param days
	 * @return
	 */
	public static Date daysBeforeNow(int days) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_YEAR, -days);
		return calendar.getTime();
	}

	/**
	 * return the date of <code>days</code> before <code>date</code>.
	 *
	 * @param date
	 * @param days
	 * @return
	 */
	public static Date dateBefore(Date date, int days) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_YEAR, -days);
		return calendar.getTime();
	}

	/**
	 * Formats {@link Date} to string as "yyyyMMddHHmmss".
	 *
	 * @param date
	 *            {@link Date}
	 * @return the formatted date string.
	 */
	public static String getDateTime(Date date) {
		return new DateTime(date.getTime()).toString(DATE_FMT_YMDHMS);
	}

	/**
	 * Formats {@link Date} to string as "yyyyMMddHHmmssSSS".
	 *
	 * @param date
	 *            {@link Date}
	 * @return the formatted date string.
	 */
	public static String getDateTimeMillisecond(Date date) {
		return new DateTime(date.getTime()).toString(DATE_FMT_YMDHMSSSSS);
	}

	/**
	 * Formats {@link Date} to string as "HHmmss".
	 *
	 * @param date
	 *            {@link Date}
	 * @return the formatted date string.
	 */
	public static String getHourMinuteOfTime(Date date) {
		return new DateTime(date.getTime()).toString(DATE_FMT_HMS);
	}

	/**
	 * Formats {@link Date} to string as "yyyy-MM-dd HH:mm:ss".
	 *
	 * @param date
	 *            {@link Date}
	 * @return the formatted date string.
	 */
	public static String getDateDetail(Date date) {
		return new DateTime(date.getTime()).toString(DATE_FMT_Y_M_D_HMSS);
	}

	public static Date addYears(Date date, int years) {
		return new DateTime(date).plusYears(years).toDate();
	}

	/**
	 * Adds the specific months to date passed in.
	 *
	 * @param date
	 *            {@link Date}
	 * @param months
	 *            the number of month
	 * @return the new date plus the increased months
	 */
	public static Date addMonths(Date date, int months) {
		return new DateTime(date).plusMonths(months).toDate();
	}

	/**
	 * Adds the specific minutes to date passed in.
	 *
	 * @param date
	 *            {@link Date}
	 * @param minutes
	 *            the number of minute
	 * @return the new date plus the increased minutes
	 */
	public static Date addMinutes(Date date, int minutes) {
		return new DateTime(date).plusMinutes(minutes).toDate();
	}

	public static Date addHours(Date date, int hours) {
		return new DateTime(date).plusHours(hours).toDate();
	}

	public static Date addSeconds(Date date, int seconds) {
		return new DateTime(date).plusSeconds(seconds).toDate();
	}

	/**
	 * Parses a string formatted as "yyyy-MM-dd" to {@link Date}。
	 *
	 * @param time
	 *            the time string
	 * @return {@link Date} if succeed.
	 * @throws UnsupportedOperationException
	 *             if parsing is not supported
	 * @throws IllegalArgumentException
	 *             if the text to parse is invalid
	 */
	public static Date parseDate(String time) {
		return DateTimeFormat.forPattern(DATE_FMT_Y_M_D).parseDateTime(time)
				.toLocalDate().toDate();
	}

	public static String formatDate(Date date, String format) {
		return DateTimeFormat.forPattern(format).print(new DateTime(date));
	}

	/**
	 * Parses a time string to the format specified.
	 *
	 * @param time
	 *            the time string
	 * @param format
	 *            refer to {@link org.joda.time.format.DateTimeFormat}
	 * @throws UnsupportedOperationException
	 *             if parsing is not supported
	 * @throws IllegalArgumentException
	 *             if the text to parse is invalid
	 */
	public static Date parseDate(String time, String format) {
		return DateTimeFormat.forPattern(format).parseDateTime(time).toDate();
	}

	public static Date addDays(Date date, int days) {
		return new DateTime(date).plusDays(days).toDate();
	}

	public static Date getBeginOfDate(Date date) {
		return new DateTime(date).withTime(0, 0, 0, 0).toDate();
	}

	public static Date getEndOfDate(Date date) {
		return new DateTime(date).withTime(23, 59, 59, 0).toDate();
	}

	public static Date getYearMonthDay(Date date) {
		return normalizeDay(date);
	}

	public static Date daysAfterNow(int numOfdays) {
		int actualNumOfDays = numOfdays - 1;
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, actualNumOfDays);
		return getYearMonthDay(cal.getTime());
	}

	public static Date monthsAfterNow(int num) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, num);
		return getYearMonthDay(cal.getTime());
	}

	// /////////////////////////////////////////
	// from SLDateUtils
	// ///////////////////////////////////////

	public static String getCurrentDate(String aFormat) {
		return getCurrentDate(aFormat, Calendar.getInstance().getTimeInMillis());
	}

	public static String getCurrentDate(String aFormat, long time) {
		return new DateTime(time).toString(aFormat);
	}

	public static String getCurrentDate() {
		return getCurrentDate(DATE_FMT_YMD);
	}

	public static String getCurrentTime() {
		return getCurrentDate(DATE_FMT_HMS);
	}

	public static String getCurrentDateAndTime() {
		return getCurrentDate(DATE_FMT_YMDHMS);
	}

	public static List<Integer> getYears(int duration) {
		List<Integer> list = new ArrayList<Integer>();
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		for (int i = 0; i < duration; i++) {
			list.add(year - i);
		}
		return list;
	}

	public enum DateRange {
		TODAY(0, 0, 0), PAST_7_DAYS(-7, 0, 0), PAST_MONTH(0, -1, 0), PAST_3_MONTH(
				0, -3, 0), PAST_6_MONTH(0, -6, 0),
		// past_1_year(0,0,-1)
		;

		private int dayOffset = 0;
		private int monthOffset = 0;
		private int yearOffset = 0;

		DateRange(int dayOffset, int monthOffset, int yearOffset) {
			this.dayOffset = dayOffset;
			this.monthOffset = monthOffset;
			this.yearOffset = yearOffset;
		}

		public Date getStartDate(Date date) {
			Calendar startC = Calendar.getInstance();
			startC.setTime(date);
			startC.add(Calendar.YEAR, yearOffset);
			startC.add(Calendar.MONTH, monthOffset);
			startC.add(Calendar.DAY_OF_MONTH, dayOffset);
			return startC.getTime();
		}
	}

	// /////////////////////
	// from XMDateUtil
	// ////////////////////////

	public static BigDecimal getThisYearDays() {
		return BigDecimal.valueOf(DAYS_PER_YEAR);
	}

	public static Date getYesterday(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_WEEK, -1);
		return cal.getTime();
	}

	public static int getDaysBetween(Date smdate, Date bdate) {
		return Days.daysBetween(new DateTime(smdate), new DateTime(bdate))
				.getDays();
	}

	public static int getDayOfYear() {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(new Date());

		return cal.get(Calendar.DAY_OF_YEAR);
	}

	public static int getDayOfMonth(Date date) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);
		return cal.get(Calendar.DAY_OF_MONTH);
	}

	public static boolean isSameDay(Date date1, Date date2) {
		if (date1 == null || date2 == null)
			return false;
		return (DateUtil.normalizeDay(date1).getTime() == DateUtil
				.normalizeDay(date2).getTime());
	}

	public static boolean isSameMonth(Date date1, Date date2) {
		if (date1 == null || date2 == null)
			return false;
		Date d1 = DateUtil.normalizeMonth(date1);
		Date d2 = DateUtil
				.normalizeMonth(date2);
		if(null == d1 || null == d2){
			return false;
		}
		return (d1.getTime() == d2.getTime());
	}

	public static Date getNowDate() {
		return new Date();
	}

	/**
	 * generate the precision age by birthday
	 *
	 * @param dob
	 * @return
	 */
	public static Integer getAgeByDob(Date dob) {
		if (dob != null) {
			Calendar dobDate = Calendar.getInstance();
			dobDate.setTime(dob);
			Calendar nowDate = Calendar.getInstance();
			int dobYear = dobDate.get(Calendar.YEAR);
			int dobMonth = dobDate.get(Calendar.MONTH);
			int dobDay = dobDate.get(Calendar.DAY_OF_MONTH);
			int currentYear = nowDate.get(Calendar.YEAR);
			int currentMonth = nowDate.get(Calendar.MONTH);
			int currentDay = nowDate.get(Calendar.DAY_OF_MONTH);
			int tempAge = currentYear - dobYear;
			if ((currentMonth < dobMonth)
					|| (currentMonth == dobMonth && currentDay < dobDay)) {
				tempAge--;
			}
			return tempAge;
		}
		return null;
	}

	/**
	 * Parses a string formatted as "yyyy" to {@link Date}。
	 *
	 * @param time
	 *            the time string
	 * @return {@link Date} if succeed.
	 * @throws UnsupportedOperationException
	 *             if parsing is not supported
	 * @throws IllegalArgumentException
	 *             if the text to parse is invalid
	 */
	public static Date parseDateToYYYY(String time) {
		return DateTimeFormat.forPattern(DATE_FMT_YYYY).parseDateTime(time)
				.toLocalDate().toDate();
	}

	public static boolean isWeekend(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		return (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)
				|| (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY);
	}

	public static int getDayOfWeek(Date date) {
		if (date == null) {
			throw new IllegalArgumentException("The date is null");
		}
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(date.getTime());
		return cal.get(Calendar.DAY_OF_WEEK);
	}

}
