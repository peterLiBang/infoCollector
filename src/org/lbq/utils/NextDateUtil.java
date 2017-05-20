package org.lbq.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

public class NextDateUtil
{

	public static final String timePattern = "yyyy-MM-dd HH:mm:ss";

	@SuppressWarnings("deprecation")
	@Test
	public void testNextDate()
	{
		Date date = new Date();// 新建此时的的系统时间
		System.out.println(date.toLocaleString());
		System.out.println(getNextDay(date).toLocaleString());// 返回明天的时间
	}

	public static Date getNextDay(Date date)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, +1);// +1今天的时间加一天
		date = calendar.getTime();
		return date;
	}

	public static String getDateTime(Date date)
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat(timePattern);
		return dateFormat.format(date);
	}

}
