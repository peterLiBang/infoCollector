package org.lbq.utils;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

/**
 * Title: 无线管控中心
 *
 * Description: 日期工具类
 *
 * Copyright(c): 2015-2099, Netbox Tech. Co., Ltd.
 *
 * Company: 珠海网博信息科技股份有限公司
 *
 * @author lingwf
 * @version 1.0
 * @date 2015年12月10日 下午2:17:37
 */
public class DateUtil {
	public static final String timePattern = "yyyy-MM-dd HH:mm:ss";
	public static final int rowkeyDDHHMMSS = 32246060;
	public static final long maxSecondTimde = 30000000000L;

	/**
	 * 获取当前系统时间的绝对秒
	 * 
	 * @return
	 */
	public static Long getCurrentSecondTime() {
		return Long.valueOf(System.currentTimeMillis() / 1000L);
	}

	/**
	 * 获取当前系统时间的绝对毫秒
	 * 
	 * @return
	 */
	public static Long getCurrentMsTime() {
		return Long.valueOf(System.currentTimeMillis());
	}

	/**
	 * 
	 * @return
	 */
	public static Long getCurrentMsTimeReverse() {
		return Long.valueOf(9999999999999L - System.currentTimeMillis());
	}

	/**
	 * 
	 * @return
	 */
	public static String getCurrentSecondTimeStr() {
		SimpleDateFormat df = new SimpleDateFormat(timePattern);
		return df.format(new Date());
	}

	/**
	 * 获取时间
	 * 
	 * @param secondTime
	 *            时间绝对秒
	 * @return 返回yyyy-MM-dd HH:mm:ss
	 */
	public static String getDateTime(long secondTime) {
		return getDateTime(secondTime, timePattern);
	}

	/**
	 * 获取时间
	 * 
	 * @param date
	 * @return 返回yyyy-MM-dd HH:mm:ss
	 */
	public static String getDateTime(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(timePattern);
		return dateFormat.format(date);
	}

	/**
	 * 获取时间
	 * 
	 * @param time
	 *            时间绝对秒或日期
	 * @return 返回yyyy-MM-dd HH:mm:ss
	 */
	public static String getDateTime(String time) {
		if (time.contains("-") || time.contains("/"))
			return time;
		return getDateTime(Long.parseLong(time));
	}

	/**
	 * 获取时间
	 * 
	 * @param secondTime
	 *            时间绝对秒
	 * @param timePattern
	 *            时间格式
	 * @return
	 */
	public static String getDateTime(long secondTime, String timePattern) {
		if (timePattern == null) {
			throw new IllegalArgumentException("time parttern is null");
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat(timePattern);
		return dateFormat.format(new Date(secondTime * 1000L));
	}

	/**
	 * 获取绝对秒
	 * 
	 * @param time
	 * @return 绝对秒
	 */
	public static long getTotalSec(String time) {
		return getTotalSecMS(time) / 1000;
	}

	/**
	 * 获取绝对秒
	 * 
	 * @param date
	 * @return
	 */
	public static long getTotalSec(Date date) {
		return getTotalSecMS(date) / 1000L;
	}

	/**
	 * 获取绝对秒
	 * 
	 * @param time
	 * @param timePattern
	 * @return
	 */
	public static long getTotalSec(String time, String timePattern) {
		return getTotalSecMS(time, timePattern) / 1000L;
	}

	/**
	 * 获取绝对毫秒值
	 * 
	 * @param time
	 * @return
	 */
	public static long getTotalSecMS(String time) {
		if (time.contains("-") || time.contains("/")) {
			if (time.toUpperCase().contains("T") || time.toUpperCase().contains("Z"))
				try {
					time = utc2Local(time);
				} catch (ParseException e) {
				}
			return getTotalSecMS(time, getTimePattern(time));
		} else {
			long t = Long.parseLong(time);
			return t > maxSecondTimde ? t : Long.parseLong(time) * 1000;
		}
	}

	/**
	 * 获取绝对毫秒值
	 * 
	 * @param date
	 * @return
	 */
	public static long getTotalSecMS(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(timePattern);
		String time = dateFormat.format(date);
		return getTotalSecMS(time);
	}

	/**
	 * 获取绝对毫秒值
	 * 
	 * @param time
	 *            日期
	 * @param timePattern
	 * @return
	 */
	public static long getTotalSecMS(String time, String timePattern) {
		if (timePattern == null) {
			throw new IllegalArgumentException("time parttern is null");
		}
		if (time == null) {
			throw new IllegalArgumentException("time is null");
		}
		if (time.contains("-") || time.contains("/")) {
			SimpleDateFormat dateFormat = new SimpleDateFormat(timePattern);
			try {
				return dateFormat.parse(time).getTime();
			} catch (ParseException e) {
			}
		} else {
			long t = Long.parseLong(time);
			return t > maxSecondTimde ? t : Long.parseLong(time) * 1000;
		}
		throw new RuntimeException("Invalid formate");
	}

	/**
	 * 获取时间格式
	 * 
	 * @param time
	 * @return
	 */
	private static String getTimePattern(String time) {
		if (time == null) {
			throw new IllegalArgumentException("time is null");
		}
		String[] spl = time.split(" ");
		String timePattern = "";
		if (spl[0].contains("/"))
			timePattern = String.format("yyyy%sMM%sdd", "/", "/");
		else if (spl[0].contains("-"))
			timePattern = String.format("yyyy%sMM%sdd", "-", "-");
		if (spl != null && spl.length > 1) {
			timePattern = String.format("%s HH%smm%sss", timePattern, ":", ":");
			if (spl[1].contains("."))
				timePattern = String.format("%s.SSS", timePattern);
		}
		return timePattern;
	}

	/**
	 * 获取日时分秒
	 * 
	 * @param time
	 *            时间
	 * @return
	 */
	public static String getDDHHMMSS(String time) {
		if (time.contains("-") || time.contains("/"))
			return getDDHHMMSS(getTotalSec(time));
		else
			return getDDHHMMSS(Long.parseLong(time));
	}

	/**
	 * 获取日时分秒
	 * 
	 * @param time
	 *            时间
	 * @return
	 */
	public static String getDDHHMMSS(long time) {
		Calendar cal = new GregorianCalendar();
		cal.setTimeInMillis(time * 1000L);
		String DDHHMMSS = String.format("%02d%02d%02d%02d", cal.get(Calendar.DATE), cal.get(Calendar.HOUR_OF_DAY),
				cal.get(Calendar.MINUTE), cal.get(Calendar.SECOND));
		return DDHHMMSS;
	}

	/**
	 * 按时间降序
	 * 
	 * @param time
	 * @return
	 */
	public static String getRowkeyDDHHMMSS(String time) {
		int ddhhmmss = Integer.parseInt(getDDHHMMSS(time));
		return String.format("%08d", rowkeyDDHHMMSS - ddhhmmss);
	}

	/**
	 * 按时间降序
	 * 
	 * @param time
	 * @return
	 */
	public static String getRowkeyDDHHMMSS(long time) {
		int ddhhmmss = Integer.parseInt(getDDHHMMSS(time));
		return String.format("%08d", rowkeyDDHHMMSS - ddhhmmss);
	}

	/**
	 * 按时间降序
	 * 
	 * @param ddhhmmss
	 * @return
	 */
	public static String getRowKeyDDHHMMSSByDDHHMMSS(int ddhhmmss) {
		return String.format("%08d", rowkeyDDHHMMSS - ddhhmmss);
	}

	/**
	 * 获取年月
	 * 
	 * @param time
	 *            时间
	 * @return
	 */
	public static String getYYMM(String time) {
		if (time.contains("-") || time.contains("/"))
			return getYYMM(getTotalSec(time));
		else
			return getYYMM(Long.parseLong(time));
	}

	/**
	 * 获取年月
	 * 
	 * @param time
	 * @return
	 */
	public static String getYYMM(long time) {
		Calendar cal = new GregorianCalendar();
		cal.setTimeInMillis(time * 1000L);
		String YYMM = String.format("%04d%02d", cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1);
		return YYMM;
	}

	/**
	 * 获取年月日
	 * 
	 * @param time
	 *            时间
	 * @return
	 */
	public static String getYYMMDD(String time) {
		if (time.contains("-") || time.contains("/"))
			return getYYMMDD(getTotalSec(time));
		else
			return getYYMMDD(Long.parseLong(time));
	}

	/**
	 * 获取年月日
	 * 
	 * @param time
	 * @return
	 */
	public static String getYYMMDD(long time) {
		Calendar cal = new GregorianCalendar();
		cal.setTimeInMillis(time * 1000L);
		String YYMM = String.format("%04d%02d%02d", cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1,
				cal.get(Calendar.DATE));
		return YYMM;
	}

	/**
	 * 获取年月
	 * 
	 * @param begintTime
	 * @param endTime
	 * @return
	 */
	public static List<String> listYYMMBetweenLongDate(long begintTime, long endTime) {
		List<String> dataList = new ArrayList<String>();
		if (begintTime > 0 && begintTime <= endTime) {
			Calendar btime = getYYMMTime(begintTime);
			Calendar etime = getYYMMTime(endTime);
			while (btime.compareTo(etime) <= 0) {
				dataList.add(getYYMM(etime.getTimeInMillis() / 1000L));
				etime.add(Calendar.MONTH, -1);
			}
		}
		return dataList;
	}

	/**
	 * 获取年月日
	 * 
	 * @param begintTime
	 * @param endTime
	 * @return
	 */
	public static List<String> listYYMMDDBetweenLongDate(long begintTime, long endTime) {
		List<String> dataList = new ArrayList<String>();
		if (begintTime > 0 && begintTime <= endTime) {
			Calendar btime = getYYMMDDTime(begintTime);
			Calendar etime = getYYMMDDTime(endTime);
			while (btime.compareTo(etime) <= 0) {
				dataList.add(getYYMMDD(etime.getTimeInMillis() / 1000L));
				etime.add(Calendar.DAY_OF_MONTH, -1);
			}
		}
		return dataList;
	}

	/**
	 * 
	 * @param time
	 * @return
	 */
	public static Calendar getYYMMTime(long time) {
		Calendar btime = new GregorianCalendar();
		btime.setTimeInMillis(time * 1000L);
		return new GregorianCalendar(btime.get(Calendar.YEAR), btime.get(Calendar.MONTH), 1);
	}

	/**
	 * 
	 * @param time
	 * @return
	 */
	public static Calendar getYYMMDDTime(long time) {
		Calendar btime = new GregorianCalendar();
		btime.setTimeInMillis(time * 1000L);
		return new GregorianCalendar(btime.get(Calendar.YEAR), btime.get(Calendar.MONTH),
				btime.get(Calendar.DAY_OF_MONTH));
	}

	/**
	 * 本地时间转UTC
	 * 
	 * @param time
	 * @return
	 */
	public static String local2Utc(String time) {
		return local2Utc(getTotalSec(time));
	}

	/**
	 * 本地时间转UTC
	 * 
	 * @param time
	 * @return
	 */
	public static String local2Utc(Long time) {
		long t = time * 1000; // + 8 * 60 * 60 * 1000;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		return sdf.format(t);
	}

	/**
	 * us转本地时间
	 * 
	 * @param time
	 * @return
	 * @throws ParseException
	 */
	public static String us2Local(String time) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", java.util.Locale.US);
		SimpleDateFormat sdf1 = new SimpleDateFormat(timePattern);
		return sdf1.format(sdf.parse(time));
	}

	/**
	 * utc转本地时间
	 * 
	 * @param time
	 * @return
	 * @throws ParseException
	 */
	public static String utc2Local(String time) throws ParseException {
		String pattern = time.contains(".") ? "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'" : "yyyy-MM-dd'T'HH:mm:ss'Z'";
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		SimpleDateFormat sdf1 = new SimpleDateFormat(timePattern);
		// sdf1.setTimeZone(TimeZone.getDefault());
		Date d = sdf.parse(time);
		return sdf1.format(d);
	}

	public static int compare(Date dt1, Date dt2) {
		if (dt1.getTime() > dt2.getTime()) {
			return 1;
		} else if (dt1.getTime() < dt2.getTime()) {
			return -1;
		} else {
			return 0;
		}
	}
}
