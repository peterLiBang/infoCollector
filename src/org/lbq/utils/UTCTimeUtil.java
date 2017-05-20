package org.lbq.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.junit.Test;

public class UTCTimeUtil
{

	/**
	 * utc转本地时间
	 * 
	 * @param time
	 * @return
	 * @throws ParseException
	 */
	public static final String timePattern = "yyyy-MM-dd HH:mm:ss";
	
	public static String utc2Local(String time) throws ParseException {
		String pattern = time.contains(".") ? "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'" : "yyyy-MM-dd'T'HH:mm:ss'Z'";
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		SimpleDateFormat sdf1 = new SimpleDateFormat(timePattern);
		Date d = sdf.parse(time);
		return sdf1.format(d);
	}
	

	/* 
     * 将时间转换为时间�?
     */    
    public static String dateToStamp(String s) throws ParseException{
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = simpleDateFormat.parse(s);
        long ts = date.getTime();
        res = String.valueOf(ts);
        return res.substring(0,res.length()-3); //1488362450 不带毫秒�?
    }
	
    
	
	@Test
	public void  testUtc2Local()
	{
		String utcTime = "2017-03-01T10:00:50Z";
		try
		{
			System.out.println(UTCTimeUtil.utc2Local(utcTime));
			System.out.println(UTCTimeUtil.dateToStamp(UTCTimeUtil.utc2Local(utcTime)));
			
		} catch (ParseException e)
		{
			e.printStackTrace();
		}
	}
	
	
}
