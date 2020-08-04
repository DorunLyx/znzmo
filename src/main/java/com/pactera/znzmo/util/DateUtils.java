package com.pactera.znzmo.util;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @Description: TODO
 * @author ZhangZhiyun
 * @date 2017-4-7 下午4:55:11
 * @version V1.0
 */
public class DateUtils {
	protected static Log logger = LogFactory.getLog(DateUtils.class);

	/** 格式：年－月－日 小时：分钟：秒  **/
	public static final String FORMAT_ONE = "yyyy-MM-dd HH:mm:ss";
	/** 格式：年－月－日 小时：分钟  **/
	public static final String FORMAT_NEW = "yyyy年MM月dd日 HH:mm";

	/** 格式：年－月－日 小时：分钟 **/
	public static final String FORMAT_TWO = "yyyy-MM-dd HH:mm";

	/** 格式：年月日 小时分钟秒 **/
	public static final String FORMAT_THREE = "yyyyMMdd-HHmmss";

	/** 格式：年－月－日 **/
	public static final String LONG_DATE_FORMAT = "yyyy-MM-dd";

	/** 格式：月－日 **/
	public static final String SHORT_DATE_FORMAT = "MM-dd";

	/** 格式：月－日 **/
	public static final String SHORT_DATE_FORMAT_TWO = "yyyy年MM月dd日";

	/** 格式：小时：分钟：秒 **/
	public static final String LONG_TIME_FORMAT = "HH:mm:ss";

    /** yyyyMMddHHmmss 紧凑类型 **/
	public static final String LONG_DATE_COMPACT_FORMAT = "yyyyMMddHHmmss";

	/** yyyy/MM/dd Excel类型 **/
	public static final String DATE_EXCEL_FORMAT = "yyyy/MM/dd";

	// 年的加减
	public static final int SUB_YEAR = Calendar.YEAR;

	// 月加减
	public static final int SUB_MONTH = Calendar.MONTH;

	// 天的加减
	public static final int SUB_DAY = Calendar.DATE;

	// 小时的加减
	public static final int SUB_HOUR = Calendar.HOUR;

	// 分钟的加减
	public static final int SUB_MINUTE = Calendar.MINUTE;

	// 秒的加减
	public static final int SUB_SECOND = Calendar.SECOND;

	// 用来全局控制 上一周，本周，下一周的周数变化
	private int weeks = 0;

	private int MaxDate;// 一月最大天数

	private int MaxYear;// 一年最大天数

	public static final String DATE_FORMAT = "yyyy-MM-dd";

	public DateUtils() {
	}

	/**
	 * 把符合日期格式的字符串转换为日期类型
	 *
	 * @param dateStr
	 * @return
	 */
	public static Date StringtoDate(String dateStr, String format) {
		Date d = null;
		SimpleDateFormat formater = new SimpleDateFormat(format);
		try {
			formater.setLenient(false);
			d = formater.parse(dateStr);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			d = null;
		}
		return d;
	}

	/**
	 * 把符合日期格式的字符串转换为日期类型
	 */
	public static Date StringtoDate(String dateStr, String format,
			ParsePosition pos) {
		Date d = null;
		SimpleDateFormat formater = new SimpleDateFormat(format);
		try {
			formater.setLenient(false);
			d = formater.parse(dateStr, pos);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			d = null;
		}
		return d;
	}

	/**
	 * 把日期转换为字符串
	 *
	 * @param date
	 * @return
	 */
	public static String DateToString(Date date, String format) {
		String result = "";
		SimpleDateFormat formater = new SimpleDateFormat(format);
		try {
			result = formater.format(date);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return result;
	}

	/**
	 * 获取当前时间的指定格式
	 *
	 * @param format
	 * @return
	 */
	public static String getCurrDate(String format) {
		return DateToString(new Date(), format);
	}

	/**
	 *
	 * @param dateStr
	 * @param amount
	 * @return
	 */
	public static String DateSub(int dateKind, String dateStr, int amount) {
		Date date = StringtoDate(dateStr, FORMAT_ONE);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(dateKind, amount);
		return DateToString(calendar.getTime(), FORMAT_ONE);
	}

	/**
	 * 两个日期相减
	 *
	 * @param firstTime
	 * @param secTime
	 * @return 相减得到的秒数
	 */
	public static long TimeSub(String firstTime, String secTime) {

		try{
			long first = StringtoDate(firstTime, FORMAT_ONE)==null?0l:StringtoDate(firstTime, FORMAT_ONE).getTime();
			long second = StringtoDate(secTime, FORMAT_ONE)==null?0l:StringtoDate(secTime, FORMAT_ONE).getTime();
			return (second - first) / 1000;
		}catch (Exception e){
			logger.error(e.getMessage(),e);
		}
		return 0;
	}

	/**
	 * 获得某月的天数
	 *
	 * @param year
	 *            int
	 * @param month
	 *            int
	 * @return int
	 */
	public static int getDaysOfMonth(String year, String month) {
		int days = 0;
		if (month.equals("1") || month.equals("3") || month.equals("5")
				|| month.equals("7") || month.equals("8") || month.equals("10")
				|| month.equals("12")) {
			days = 31;
		} else if (month.equals("4") || month.equals("6") || month.equals("9")
				|| month.equals("11")) {
			days = 30;
		} else {
			if ((Integer.parseInt(year) % 4 == 0 && Integer.parseInt(year) % 100 != 0)
					|| Integer.parseInt(year) % 400 == 0) {
				days = 29;
			} else {
				days = 28;
			}
		}

		return days;
	}

	/**
	 * 获取某年某月的天数
	 *
	 * @param year
	 *            int
	 * @param month
	 *            int 月份[1-12]
	 * @return int
	 */
	public static int getDaysOfMonth(int year, int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month - 1, 1);
		return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 获得当前日期
	 *
	 * @return int
	 */
	public static int getToday() {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.DATE);
	}

	/**
	 * 获得当前月份
	 *
	 * @return int
	 */
	public static int getToMonth() {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.MONTH) + 1;
	}

	/**
	 * 获得当前年份
	 *
	 * @return int
	 */
	public static int getToYear() {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.YEAR);
	}

	/**
	 * 返回日期的天
	 *
	 * @param date
	 *            Date
	 * @return int
	 */
	public static int getDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.DATE);
	}

	/**
	 * 返回日期的年
	 *
	 * @param date
	 *            Date
	 * @return int
	 */
	public static int getYear(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.YEAR);
	}

	/**
	 * 返回日期的月份，1-12
	 *
	 * @param date
	 *            Date
	 * @return int
	 */
	public static int getMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.MONTH) + 1;
	}

	/**
	 * 计算两个日期相差的天数，如果date2 > date1 返回正数，否则返回负数
	 *
	 * @param date1
	 *            Date
	 * @param date2
	 *            Date
	 * @return long
	 */
	public static long dayDiff(Date date1, Date date2) {
		return (date2.getTime() - date1.getTime()) / 86400000;
	}

	/**
	 * 比较两个日期的年差
	 *
	 * @param befor
	 * @param after
	 * @return
	 */
	public static int yearDiff(String before, String after) {
		Date beforeDay = StringtoDate(before, LONG_DATE_FORMAT);
		Date afterDay = StringtoDate(after, LONG_DATE_FORMAT);
		return getYear(afterDay) - getYear(beforeDay);
	}

	/**
	 * 比较指定日期与当前日期的差
	 *
	 * @param befor
	 * @param after
	 * @return
	 */
	public static int yearDiffCurr(String after) {
		Date beforeDay = new Date();
		Date afterDay = StringtoDate(after, LONG_DATE_FORMAT);
		return getYear(beforeDay) - getYear(afterDay);
	}

	public static int getFirstWeekdayOfMonth(int year, int month) {
		Calendar c = Calendar.getInstance();
		c.setFirstDayOfWeek(Calendar.SATURDAY); // 星期天为第一天
		c.set(year, month - 1, 1);
		return c.get(Calendar.DAY_OF_WEEK);
	}

	public static int getLastWeekdayOfMonth(int year, int month) {
		Calendar c = Calendar.getInstance();
		c.setFirstDayOfWeek(Calendar.SATURDAY); // 星期天为第一天
		c.set(year, month - 1, getDaysOfMonth(year, month));
		return c.get(Calendar.DAY_OF_WEEK);
	}


	/**
	 * 获得当前日期字符串，格式"yyyy-MM-dd HH:mm:ss"
	 *
	 * @return
	 */
	public static String getNow() {
		Calendar today = Calendar.getInstance();
		return DateToString(today.getTime(), FORMAT_ONE);
	}

	/**
	 * 根据生日获取星座
	 *
	 * @param birth
	 *            YYYY-mm-dd
	 * @return
	 */
	public static String getAstro(String birth) {
		if (!isDate(birth)) {
			birth = "2000" + birth;
		}
		if (!isDate(birth)) {
			return "";
		}
		int month = Integer.parseInt(birth.substring(birth.indexOf("-") + 1,
				birth.lastIndexOf("-")));
		int day = Integer.parseInt(birth.substring(birth.lastIndexOf("-") + 1));
		logger.debug(month + "-" + day);
		String s = "魔羯水瓶双鱼牡羊金牛双子巨蟹狮子处女天秤天蝎射手魔羯";
		int[] arr = { 20, 19, 21, 21, 21, 22, 23, 23, 23, 23, 22, 22 };
		int start = month * 2 - (day < arr[month - 1] ? 2 : 0);
		return s.substring(start, start + 2) + "座";
	}

	/**
	 * 判断日期是否有效,包括闰年的情况
	 *
	 * @param date
	 *            YYYY-mm-dd
	 * @return
	 */
	public static boolean isDate(String date) {
		StringBuffer reg = new StringBuffer(
				"^((\\d{2}(([02468][048])|([13579][26]))-?((((0?");
		reg.append("[13578])|(1[02]))-?((0?[1-9])|([1-2][0-9])|(3[01])))");
		reg.append("|(((0?[469])|(11))-?((0?[1-9])|([1-2][0-9])|(30)))|");
		reg.append("(0?2-?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][12");
		reg.append("35679])|([13579][01345789]))-?((((0?[13578])|(1[02]))");
		reg.append("-?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))");
		reg.append("-?((0?[1-9])|([1-2][0-9])|(30)))|(0?2-?((0?[");
		reg.append("1-9])|(1[0-9])|(2[0-8]))))))");
		Pattern p = Pattern.compile(reg.toString());
		return p.matcher(date).matches();
	}

	/**
	 * 取得指定日期过 months 月后的日期 (当 months 为负数表示指定月之前);
	 *
	 * @param date
	 *            日期 为null时表示当天
	 * @param month
	 *            相加(相减)的月数
	 */
	public static Date nextMonth(Date date, int months) {
		Calendar cal = Calendar.getInstance();
		if (date != null) {
			cal.setTime(date);
		}
		cal.add(Calendar.MONTH, months);
		return cal.getTime();
	}

	/**
	 * 取得指定日期过 day 天后的日期 (当 day 为负数表示指定月之前);
	 *
	 * @param date
	 *            日期 为null时表示当天
	 * @param month
	 *            相加(相减)的月数
	 */
	public static Date nextDay(Date date, int day) {
		Calendar cal = Calendar.getInstance();
		if (date != null) {
			cal.setTime(date);
		}
		cal.add(Calendar.DAY_OF_YEAR, day);
		return cal.getTime();
	}

	/**
	 * 取得指定日期过 day 周后的日期 (当 day 为负数表示指定月之前)
	 *
	 * @param date
	 *            日期 为null时表示当天
	 */
	public static Date nextWeek(Date date, int week) {
		Calendar cal = Calendar.getInstance();
		if (date != null) {
			cal.setTime(date);
		}
		cal.add(Calendar.WEEK_OF_MONTH, week);
		return cal.getTime();
	}

	/**
	 * 获取当前的日期
	 *
	 * @return
	 */
	public static String currDay() {
		return DateUtils.DateToString(new Date(), DateUtils.LONG_DATE_FORMAT);
	}

	/**
	 * 获取昨天的日期
	 *
	 * @return
	 */
	public static String befoDay() {
		return DateUtils.DateToString(DateUtils.nextDay(new Date(), -1),
				DateUtils.LONG_DATE_FORMAT);
	}

	/**
	 * 获取明天的日期
	 */
	public static String afterDay() {
		return DateUtils.DateToString(DateUtils.nextDay(new Date(), 1),
				DateUtils.LONG_DATE_FORMAT);
	}

	/**
	 * 取得当前时间距离1900/1/1的天数
	 *
	 * @return
	 */
	public static int getDayNum() {
		int daynum = 0;
		GregorianCalendar gd = new GregorianCalendar();
		Date dt = gd.getTime();
		GregorianCalendar gd1 = new GregorianCalendar(1900, 1, 1);
		Date dt1 = gd1.getTime();
		daynum = (int) ((dt.getTime() - dt1.getTime()) / (24 * 60 * 60 * 1000));
		return daynum;
	}

	/**
	 * getDayNum的逆方法(用于处理Excel取出的日期格式数据等)
	 *
	 * @param day
	 * @return
	 */
	public static Date getDateByNum(int day) {
		GregorianCalendar gd = new GregorianCalendar(1900, 1, 1);
		Date date = gd.getTime();
		date = nextDay(date, day);
		return date;
	}

	/** **************************************************************************************** */

	/**
	 * 得到二个日期间的间隔天数
	 */
	public static String getTwoDay(String sj1, String sj2) {
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
		long day = 0;
		try {
			Date date = myFormatter.parse(sj1);
			Date mydate = myFormatter.parse(sj2);
			day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
		} catch (Exception e) {
			return "";
		}
		return day + "";
	}

	/**
	 * 根据一个日期，返回是星期几的字符串
	 *
	 * @param sdate
	 * @return
	 */
	public static String getWeek(String sdate) {
		// 再转换为时间
		Date date = DateUtils.strToDate(sdate);
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		// int hour=c.get(Calendar.DAY_OF_WEEK);
		// hour中存的就是星期几了，其范围 1~7
		// 1=星期日 7=星期六，其他类推
		return new SimpleDateFormat("EEEE").format(c.getTime());
	}

	/**
	 * 将短时间格式字符串转换为时间 yyyy-MM-dd
	 *
	 * @param strDate
	 * @return
	 */
	public static Date strToDate(String strDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(strDate, pos);
		return strtodate;
	}

	/**
	 * 两个时间之间的天数
	 *
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static long getDays(String date1, String date2) {
		if (date1 == null || date1.equals(""))
			return 0;
		if (date2 == null || date2.equals(""))
			return 0;
		// 转换为标准时间
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		Date mydate = null;
		long day =0;
		try {
			date = myFormatter.parse(date1);
			mydate = myFormatter.parse(date2);
			day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return day;
	}



    // 计算当月最后一天,返回字符串
	public String getDefaultDay() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar lastDate = Calendar.getInstance();
		lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
		lastDate.add(Calendar.MONTH, 1);// 加一个月，变为下月的1号
		lastDate.add(Calendar.DATE, -1);// 减去一天，变为当月最后一天

		str = sdf.format(lastDate.getTime());
		return str;
	}

	// 上月第一天
	public String getPreviousMonthFirst() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar lastDate = Calendar.getInstance();
		lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
		lastDate.add(Calendar.MONTH, -1);// 减一个月，变为下月的1号
		// lastDate.add(Calendar.DATE,-1);//减去一天，变为当月最后一天

		str = sdf.format(lastDate.getTime());
		return str;
	}

	// 获取当月第一天
	public String getFirstDayOfMonth() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar lastDate = Calendar.getInstance();
		lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
		str = sdf.format(lastDate.getTime());
		return str;
	}

	// 获得本周星期日的日期
	public String getCurrentWeekday() {
		weeks = 0;
		int mondayPlus = this.getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus + 6);
		Date monday = currentDate.getTime();

		DateFormat df = DateFormat.getDateInstance();
		String preMonday = df.format(monday);
		return preMonday;
	}

	// 获取当天时间
	public String getNowTime(String dateformat) {
		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat(dateformat);// 可以方便地修改日期格式
		String hehe = dateFormat.format(now);
		return hehe;
	}

	// 获得当前日期与本周日相差的天数
	private int getMondayPlus() {
		Calendar cd = Calendar.getInstance();
		// 获得今天是一周的第几天，星期日是第一天，星期二是第二天......
		int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK) - 1; // 因为按中国礼拜一作为第一天所以这里减1
		if (dayOfWeek == 1) {
			return 0;
		} else {
			return 1 - dayOfWeek;
		}
	}

	// 获得本周一的日期
	public String getMondayOFWeek() {
		weeks = 0;
		int mondayPlus = this.getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus);
		Date monday = currentDate.getTime();

		DateFormat df = DateFormat.getDateInstance();
		String preMonday = df.format(monday);
		return preMonday;
	}

	// 获得相应周的周六的日期
	public String getSaturday() {
		int mondayPlus = this.getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus + 7 * weeks + 6);
		Date monday = currentDate.getTime();
		DateFormat df = DateFormat.getDateInstance();
		String preMonday = df.format(monday);
		return preMonday;
	}

	// 获得上周星期日的日期
	public String getPreviousWeekSunday() {
		weeks = 0;
		weeks--;
		int mondayPlus = this.getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus + weeks);
		Date monday = currentDate.getTime();
		DateFormat df = DateFormat.getDateInstance();
		String preMonday = df.format(monday);
		return preMonday;
	}

	// 获得上周星期一的日期
	public String getPreviousWeekday() {
		weeks--;
		int mondayPlus = this.getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus + 7 * weeks);
		Date monday = currentDate.getTime();
		DateFormat df = DateFormat.getDateInstance();
		String preMonday = df.format(monday);
		return preMonday;
	}

	// 获得下周星期一的日期
	public String getNextMonday() {
		weeks++;
		int mondayPlus = this.getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus + 7);
		Date monday = currentDate.getTime();
		DateFormat df = DateFormat.getDateInstance();
		String preMonday = df.format(monday);
		return preMonday;
	}

	// 获得下周星期日的日期
	public String getNextSunday() {

		int mondayPlus = this.getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus + 7 + 6);
		Date monday = currentDate.getTime();
		DateFormat df = DateFormat.getDateInstance();
		String preMonday = df.format(monday);
		return preMonday;
	}

	public int getMonthPlus() {
		Calendar cd = Calendar.getInstance();
		int monthOfNumber = cd.get(Calendar.DAY_OF_MONTH);
		cd.set(Calendar.DATE, 1);// 把日期设置为当月第一天
		cd.roll(Calendar.DATE, -1);// 日期回滚一天，也就是最后一天
		MaxDate = cd.get(Calendar.DATE);
		if (monthOfNumber == 1) {
			return -MaxDate;
		} else {
			return 1 - monthOfNumber;
		}
	}

	// 获得上月最后一天的日期
	public String getPreviousMonthEnd() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar lastDate = Calendar.getInstance();
		lastDate.add(Calendar.MONTH, -1);// 减一个月
		lastDate.set(Calendar.DATE, 1);// 把日期设置为当月第一天
		lastDate.roll(Calendar.DATE, -1);// 日期回滚一天，也就是本月最后一天
		str = sdf.format(lastDate.getTime());
		return str;
	}

	// 获得下个月第一天的日期
	public String getNextMonthFirst() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar lastDate = Calendar.getInstance();
		lastDate.add(Calendar.MONTH, 1);// 减一个月
		lastDate.set(Calendar.DATE, 1);// 把日期设置为当月第一天
		str = sdf.format(lastDate.getTime());
		return str;
	}

	// 获得下个月最后一天的日期
	public String getNextMonthEnd() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar lastDate = Calendar.getInstance();
		lastDate.add(Calendar.MONTH, 1);// 加一个月
		lastDate.set(Calendar.DATE, 1);// 把日期设置为当月第一天
		lastDate.roll(Calendar.DATE, -1);// 日期回滚一天，也就是本月最后一天
		str = sdf.format(lastDate.getTime());
		return str;
	}

	// 获得明年最后一天的日期
	public String getNextYearEnd() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar lastDate = Calendar.getInstance();
		lastDate.add(Calendar.YEAR, 1);// 加一个年
		lastDate.set(Calendar.DAY_OF_YEAR, 1);
		lastDate.roll(Calendar.DAY_OF_YEAR, -1);
		str = sdf.format(lastDate.getTime());
		return str;
	}

	// 获得明年第一天的日期
	public String getNextYearFirst() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar lastDate = Calendar.getInstance();
		lastDate.add(Calendar.YEAR, 1);// 加一个年
		lastDate.set(Calendar.DAY_OF_YEAR, 1);
		str = sdf.format(lastDate.getTime());
		return str;

	}

	// 获得本年有多少天
	public int getMaxYear() {
		Calendar cd = Calendar.getInstance();
		cd.set(Calendar.DAY_OF_YEAR, 1);// 把日期设为当年第一天
		cd.roll(Calendar.DAY_OF_YEAR, -1);// 把日期回滚一天。
		int MaxYear = cd.get(Calendar.DAY_OF_YEAR);
		return MaxYear;
	}

	private int getYearPlus() {
		Calendar cd = Calendar.getInstance();
		int yearOfNumber = cd.get(Calendar.DAY_OF_YEAR);// 获得当天是一年中的第几天
		cd.set(Calendar.DAY_OF_YEAR, 1);// 把日期设为当年第一天
		cd.roll(Calendar.DAY_OF_YEAR, -1);// 把日期回滚一天。
		int MaxYear = cd.get(Calendar.DAY_OF_YEAR);
		if (yearOfNumber == 1) {
			return -MaxYear;
		} else {
			return 1 - yearOfNumber;
		}
	}

	// 获得本年第一天的日期
	public String getCurrentYearFirst() {
		int yearPlus = this.getYearPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, yearPlus);
		Date yearDay = currentDate.getTime();
		DateFormat df = DateFormat.getDateInstance();
		String preYearDay = df.format(yearDay);
		return preYearDay;
	}

	// 获得本年最后一天的日期 *
	public String getCurrentYearEnd() {
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");// 可以方便地修改日期格式
		String years = dateFormat.format(date);
		return years + "-12-31";
	}

	// 获得上年第一天的日期 *
	public String getPreviousYearFirst() {
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");// 可以方便地修改日期格式
		String years = dateFormat.format(date);
		int years_value = Integer.parseInt(years);
		years_value--;
		return years_value + "-1-1";
	}

	// 获得上年最后一天的日期
	public String getPreviousYearEnd() {
		weeks--;
		int yearPlus = this.getYearPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, yearPlus + MaxYear * weeks
				+ (MaxYear - 1));
		Date yearDay = currentDate.getTime();
		DateFormat df = DateFormat.getDateInstance();
		String preYearDay = df.format(yearDay);
		getThisSeasonTime(11);
		return preYearDay;
	}

//	 获得参数月的本季度第一天
	public String getThisSeasonFirstDay(int month) {
		int array[][] = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 }, { 10, 11, 12 } };
		int season = 1;
		if (month >= 1 && month <= 3) {
			season = 1;
		}
		if (month >= 4 && month <= 6) {
			season = 2;
		}
		if (month >= 7 && month <= 9) {
			season = 3;
		}
		if (month >= 10 && month <= 12) {
			season = 4;
		}
		int start_month = array[season - 1][0];

		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");// 可以方便地修改日期格式
		String years = dateFormat.format(date);
		int years_value = Integer.parseInt(years);

		int start_days = 1;// years+"-"+String.valueOf(start_month)+"-1";//getLastDayOfMonth(years_value,start_month);
		return years_value + "-" + start_month + "-" + start_days;

	}

	// 获得本季度
	public String getThisSeasonTime(int month) {
		int array[][] = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 }, { 10, 11, 12 } };
		int season = 1;
		if (month >= 1 && month <= 3) {
			season = 1;
		}
		if (month >= 4 && month <= 6) {
			season = 2;
		}
		if (month >= 7 && month <= 9) {
			season = 3;
		}
		if (month >= 10 && month <= 12) {
			season = 4;
		}
		int start_month = array[season - 1][0];
		int end_month = array[season - 1][2];

		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");// 可以方便地修改日期格式
		String years = dateFormat.format(date);
		int years_value = Integer.parseInt(years);

		int start_days = 1;// years+"-"+String.valueOf(start_month)+"-1";//getLastDayOfMonth(years_value,start_month);
		int end_days = getLastDayOfMonth(years_value, end_month);
		String seasonDate = years_value + "-" + start_month + "-" + start_days
				+ ";" + years_value + "-" + end_month + "-" + end_days;
		return seasonDate;

	}

	/**
	 * 获取某年某月的最后一天
	 *
	 * @param year
	 *            年
	 * @param month
	 *            月
	 * @return 最后一天
	 */
	private int getLastDayOfMonth(int year, int month) {
		if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8
				|| month == 10 || month == 12) {
			return 31;
		}
		if (month == 4 || month == 6 || month == 9 || month == 11) {
			return 30;
		}
		if (month == 2) {
			if (isLeapYear(year)) {
				return 29;
			} else {
				return 28;
			}
		}
		return 0;
	}



	public static Date getTomorrow() {
		Calendar current = Calendar.getInstance();
		current.add(Calendar.DATE, 1);// 加一天
		Date tomorrow=current.getTime();
		System.out.println(tomorrow);
		return tomorrow;

	}
	/**
	 * 是否闰年
	 *
	 * @param year
	 *            年
	 * @return
	 */
	public boolean isLeapYear(int year) {
		return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
	}


	/**
	 * 获取参数月的上 12个月的 起始时间,返回值是从最远的时间点开始，比如当前为2012年9月，那么则返回[2011/9,2011/10,....2012/8]
	 *
	 * @param year
	 * @param month
	 * @return list[i][0] = start; list[i][1] = end; list[i][2] = year+"/"+month;
	 */
	public static String[][] getPreTwelveList(int year,int month){
		String[][] list = new String[12][3];

		String start = "";
		String end = "";
		for (int i=0; i<12; i++) {

			int days = DateUtils.getDaysOfMonth(year, month);

			if (month>=1) {

				start = year + "-"+ month +"-01 00:00:00";
				end = year + "-"+ month +"-" + days + " 00:00:00";

			}else{
				month=12;

				start =year -1 + "-"+ month +"-01 00:00:00";
				end = year -1 + "-"+ month +"-" + days + " 00:00:00";
				year--;
			}

			list[12-i-1][0] = start;
			list[12-i-1][1] = end;
			list[12-i-1][2] = year+"/"+month;

			month--;
		}

		return list;
	}




	/**
	 * 获取 在指定年份 的 指定月份之前的 月份
	 *  例如：2012年8月之前的月份 包含8月,最近的月份放在第一个element中
	 * @param month
	 * @return list[i][0] = start; list[i][1] = end; list[i][2] = year+"/"+month;
	 */
	public static String[][] getPreMonthList(int month,int year){
		String[][] list = new String[12][3];

		String start = "";
		String end = "";
		for (int i=0; i< 12; i++) {

			if(month >=1 ){
				int days = DateUtils.getDaysOfMonth(year, month);

				start = year + "-"+ month +"-01 00:00:00";
				end = year + "-"+ month +"-" + days + " 00:00:00";

				list[12-i-1][0] = start;
				list[12-i-1][1] = end;
				list[12-i-1][2] = year+"/"+month;

				month--;
			}
		}

		return list;
	}

	/**
	 * 返回从beginYear年开始到去年的所有年份，最近的年份放在list的最开始处。
	 * 例如beginYear2009，今年是2012年，则返回[2011,2010,2009]
	 * @param beginYear
	 * @return
	 */
	public static List<Integer> getPreYear(int beginYear) {
		int preYear=DateUtils.getToYear()-1;  //去年
		int noOfYear=preYear-beginYear+1;

		List<Integer> yearList = new ArrayList<Integer>(noOfYear);

		for (int year=preYear; year>=beginYear; year--) {
			yearList.add(year);
		}

		return yearList;
	}

	/**
	 * 包括beginYear年。
	 * 返回从beginYear年开始到去年的所有年份，最近的年份放在list的最开始处。
	 * 例如beginYear2009，今年是2012年，则返回[2012,2011,2010,2009]
	 * @param beginYear
	 * @return
	 */
	public static List<Integer> getBeginYearToPreYear(int beginYear) {
		int curyear=DateUtils.getToYear();  //去年
		int noOfYear=curyear-beginYear+1;

		List<Integer> yearList = new ArrayList<Integer>(noOfYear);

		for (int year=curyear; year>=beginYear; year--) {
			yearList.add(year);
		}

		return yearList;
	}

	/**
	 * 方法描述：获取当前月的第一天
	 * @return String
	 * 创建人：zhaojun
	 * 创建时间：2017年9月30日 下午3:56:33
	 */
	public static String getMonthFirstDay() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH,
		calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		SimpleDateFormat firstDay= new SimpleDateFormat("yyyy-MM-dd");
		return  firstDay.format(calendar.getTime());
	}

	/**
	 * 方法描述：获取当前月的最后一天
	 * @return String
	 * 创建人：zhaojun
	 * 创建时间：2017年9月30日 下午3:56:59
	 */
    public static String getMonthLastDay() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH,
		calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		SimpleDateFormat lastDay= new SimpleDateFormat("yyyy-MM-dd");
		return  lastDay.format(calendar.getTime());
	}

    /**
     * 格式化日期
     * @param date 日期对象
     * @return String 日期字符串
     */
    public static String formatDate(Date date){
        SimpleDateFormat f = new SimpleDateFormat(LONG_DATE_FORMAT);
        String sDate = f.format(date);
        return sDate;
    }

    /**
     * 获取某年第一天日期
     * @param year 年份
     * @return Date
     */
    public static Date getCurrYearFirst(int year){
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        Date currYearFirst = calendar.getTime();
        return currYearFirst;
    }

    /**
     * 获取某年最后一天日期
     * @param year 年份
     * @return Date
     */
    public static Date getCurrYearLast(int year){
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        calendar.roll(Calendar.DAY_OF_YEAR, -1);
        Date currYearLast = calendar.getTime();

        return currYearLast;
    }
    /**
     * @Description: 获取当天得某一具体时间点
     * @param key 时间
     * @return
     * @author yuanyedong
     * @date   2017年12月10日 下午5:28:22
     */
    public static Date getHour(int key) {
    	Calendar calendar = Calendar.getInstance();
    	calendar.set(Calendar.HOUR_OF_DAY, key);
    	calendar.set(Calendar.MINUTE, 0);
    	calendar.set(Calendar.SECOND, 0);
    	return calendar.getTime();
    }

    /**
     * @Author lvhz
     * @Description 得到指定时间的前后指定时间
     * @Date 14:45 2019/9/24
     * @Param minute
     * @return java.util.Date
     **/
	public static Date getTimeByMinute(Date date,int minute) {
		long ss = date.getTime();
		Long l=new Long(minute);
		l=l*60*1000;
		ss = ss - l;
		Date time2 = new Date();
		time2.setTime(ss);
		return time2;
	}

	/**
	 * @Author lvhz
	 * @Description 获取指定日期的周一
	 * @Date 14:45 2019/9/24
	 * @Param minute
	 * @return java.util.Date
	 **/
	public static String getWeekBegin(Date date) throws ParseException {
		SimpleDateFormat sdf=new SimpleDateFormat(LONG_DATE_FORMAT); //设置时间格式
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		//判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
		int dayWeek = cal.get(Calendar.DAY_OF_WEEK);//获得当前日期是一个星期的第几天
		if(1 == dayWeek) {
			cal.add(Calendar.DAY_OF_MONTH, -1);
		}

		cal.setFirstDayOfWeek(Calendar.MONDAY);//设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一

		int day = cal.get(Calendar.DAY_OF_WEEK);//获得当前日期是一个星期的第几天
		cal.add(Calendar.DATE, cal.getFirstDayOfWeek()-day);//根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
		return sdf.format(cal.getTime());
	}

	/**
	 * @Author lvhz
	 * @Description 获取指定日期的周日
	 * @Date 14:45 2019/9/24
	 * @Param minute
	 * @return java.util.Date
	 **/
	public static String getWeekEnd(Date date) throws ParseException {
		SimpleDateFormat sdf=new SimpleDateFormat(LONG_DATE_FORMAT); //设置时间格式
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		//判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
		int dayWeek = cal.get(Calendar.DAY_OF_WEEK);//获得当前日期是一个星期的第几天
		if(1 == dayWeek) {
			cal.add(Calendar.DAY_OF_MONTH, -1);
		}

		cal.setFirstDayOfWeek(Calendar.MONDAY);//设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一

		cal.add(Calendar.DATE, 6);
		return sdf.format(cal.getTime());
	}

	/**
	 * @Author lvhz
	 * @Description 获取指定日期的下周一
	 * @Date 14:45 2019/9/24
	 * @Param minute
	 * @return java.util.Date
	 **/
	public static String getNextWeekBegin(Date date) throws ParseException {
		SimpleDateFormat sdf=new SimpleDateFormat(LONG_DATE_FORMAT); //设置时间格式
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		//判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
		int dayWeek = cal.get(Calendar.DAY_OF_WEEK);//获得当前日期是一个星期的第几天
		if(1 == dayWeek) {
			cal.add(Calendar.DAY_OF_MONTH, -1);
		}

		cal.setFirstDayOfWeek(Calendar.MONDAY);//设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一

		cal.add(Calendar.DATE, 7);
		return sdf.format(cal.getTime());
	}

	/**
	 * @Title: compareTime
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param time1
	 * @param time2
	 * @param timestampFormat
	 * @return 1 大于，-1 小于
	 * @author LiuGuiChao
	 * @date 2019年10月21日 下午2:59:42
	*/
	public static int compareTime (String time1, String time2, String format) {
		SimpleDateFormat timestampFormat = new SimpleDateFormat(format);
		int comparer = 3;
        int rev = 0;
        if(time1.equals(time2)) // same
            return rev;
        try {
            Date timestamp1 = timestampFormat.parse(time1);
            Date timestamp2 = timestampFormat.parse(time2);

            switch (comparer) {

                case 1: //Compare 1 - function getTime()
                    if(timestamp1.getTime() >  timestamp2.getTime()) // time1 has more seconds -> time1 is newer
                        return 1;
                    else return -1;

                case 2: //Compare 2 - function before/after
                    if(timestamp1.after(timestamp2)) // time 1 is newer
                        return 1;
                    else return -1;

                case 3: //Compare 3 - function calendar compareTo
                    Calendar cal1 = Calendar.getInstance();
                    Calendar cal2 = Calendar.getInstance();
                    cal1.setTime(timestamp1);
                    cal2.setTime(timestamp2);
                    if(cal1.compareTo(cal2) > 0) // cal1 is bigger -> cal1 is newer
                        return 1;
                    else return -1;

                default:
                    break;
            }

        } catch (ParseException e) {
        	logger.error(e.getMessage(),e);
        }

        return rev;
    }

	/**
	 * 将日期转为系统格式的（年月日）日期字符串
	 */
	public static final String convertDateToString(Date aDate) {
		return getDateTime(DATE_FORMAT, aDate);
	}

	/**
	 * 将日期转为指定格式的字符串
	 *
	 * @Methods Name getDateTime
	 * @param aMask 日期格式
	 * @param aDate 日期
	 * @return String
	 */
	public static final String getDateTime(String aMask, Date aDate) {
		SimpleDateFormat df = null;
		String returnValue = "";
		if (aDate != null) {
			df = new SimpleDateFormat(aMask);
			returnValue = df.format(aDate);
		}
		return (returnValue);
	}

	/**
	 * @Title: getOradvanceTimes
	 * @Description: 获取提前/推迟时间
	 * @param startDate 需要处理的时间
	 * @param minutes 增加的时间 （提前，用正整数， 推后时间用负整数）
	 * @return Date
	 * @author LiuGuiChao
	 * @date 2019年11月1日 下午2:31:25
	*/
	public static Date getOradvanceTimes(Date startDate, int minutes){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(startDate);
		calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE) - minutes);
		return calendar.getTime();
	}


	/**
	 * 将日期转为指定格式的字符串
	 *
	 * @Methods Name getDateTime
	 * @param aMask 日期格式
	 *
	 * @return String
	 */
	public static final String getMonthFirstDay(String day) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cale = Calendar.getInstance();
		// 获取当月第一天和最后一天
		String firstday;

		Date date = null;
		try {
			date = format.parse(day);
		} catch (ParseException e) {
			logger.error(e.getMessage(),e);
		}
		cale.setTime(date);

		// 获取前月的第一天
		cale.add(Calendar.MONTH, 0);
		cale.set(Calendar.DAY_OF_MONTH, 1);
		firstday = format.format(cale.getTime());
		return firstday;
	}


	/**
	 * 将日期转为指定格式的字符串
	 *
	 * @Methods Name getDateTime
	 * @param aMask 日期格式
	 * @param aDate 日期
	 * @return String
	 */
	public static final String getMonthLastDay(String day) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cale = Calendar.getInstance();
		// 获取当月第一天和最后一天
		String lastday;

		Date date = null;
		try {
			date = format.parse(day);
		} catch (ParseException e) {
			logger.error(e.getMessage(),e);
		}
		cale.setTime(date);

		cale.add(Calendar.MONTH, 1);
		cale.set(Calendar.DAY_OF_MONTH, 0);
		lastday = format.format(cale.getTime());
		return lastday;
	}

	//判断一个时间是否再区间内
	public static boolean isEffectiveDate(Date nowTime, Date startTime, Date endTime) {
		if (nowTime.getTime() == startTime.getTime()
				|| nowTime.getTime() == endTime.getTime()) {
			return true;
		}

		Calendar date = Calendar.getInstance();
		date.setTime(nowTime);

		Calendar begin = Calendar.getInstance();
		begin.setTime(startTime);

		Calendar end = Calendar.getInstance();
		end.setTime(endTime);

		if (date.after(begin) && date.before(end)) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isValidDate(String str) {
        boolean convertSuccess=true;
        //指定日期格式为四位年/两位月份/两位日期，注意yyyy/MM/dd区分大小写；
         SimpleDateFormat format = new SimpleDateFormat(LONG_DATE_FORMAT);
         try {
        	 //设置lenient为false. 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
            format.setLenient(false);
            format.parse(str);
         } catch (ParseException e) {
        	 logger.error(e.getMessage(),e);
        	 // e.printStackTrace();
        	 // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
             convertSuccess=false;
         }
         return convertSuccess;
    }

	/**
	 * 将日期字符串转换为LocalDateTime对象
	 * @param dateStr 如：2019-06-05
	 * @param
	 * @return
	 */
    public static LocalDateTime parseDate(String dateStr, String dateFormat){
    	DateTimeFormatter df = DateTimeFormatter.ofPattern(dateFormat);
		try {
			return LocalDateTime.of(LocalDate.parse(dateStr,df), LocalTime.MIN);
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
	}

    /**
     * @Description: 将LocalDateTime对象转换为字符串
    */
    public static String localDateTimeToString(LocalDateTime time, String dateFormat) {
    	DateTimeFormatter df = DateTimeFormatter.ofPattern(dateFormat);
		return df.format(time);
	}

	/**
	 * @Author lvhz
	 * @Description 获取当天开始时间
	 * @Date 11:01 2020/1/2
	 * @Param [day]
	 * @return java.util.Date
	 **/
	public static Date getStartTime(String day) {
		Date date = StringtoDate(day, DATE_FORMAT);
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	/*
	 * 获取当前天的结束时间
	 */
	public static Date getEndTime(String day) {
		Date date = StringtoDate(day, DATE_FORMAT);
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		return calendar.getTime();
	}

}
