package org.lbq.collector;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.lbq.utils.NextDateUtil;
import org.lbq.utils.PropertiesUtil;
import org.lbq.utils.Xml2JavaBeanUtil;

/**
 * 
 * @author libangqin
 * @version 1.0
 * */

public class FundCollectorTask implements Runnable, ITask
{
	ScheduledExecutorService service = null;
	List<Object> allFundObjList = new ArrayList<Object>();
	public FundCollectorTask()
	{
		service = Executors.newSingleThreadScheduledExecutor();
	}

	
	
	@Override
	public void run()
	{

		String fundCollectorLastRunTime = null;
		String xmlDataFilePath = "";
		int maxStockRecordNums = 0 ;
		try
		{
			xmlDataFilePath = PropertiesUtil.getPropertyByKey("xmlDataFilePath", StaticValue.configPath);
			maxStockRecordNums = Integer.parseInt(PropertiesUtil.getPropertyByKey("maxFundRecordNums", StaticValue.configPath));
			fundCollectorLastRunTime = PropertiesUtil.getPropertyByKey("fundCollectorLastRunTime",
			        StaticValue.configPath);
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		
		try
		{
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			/*
			 * example:
			 * String DateStr1 = "2014-08-21 10:20:16"; String DateStr2 =
			 * "2014-08-27 15:50:35"; DateFormat dateFormat = new
			 * SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); Date dateTime1 =
			 * dateFormat.parse(DateStr1); Date dateTime2 =
			 * dateFormat.parse(DateStr2); int i =
			 * dateTime1.compareTo(dateTime2); System.out.println(i < 0);
			 */
			Date lastRundate = new Date(); // 记录的最后运行时间
			Date matchDate = new Date();

			if (!fundCollectorLastRunTime.isEmpty() && null != fundCollectorLastRunTime)
			{
				Date curDate = new Date(); // 当前时间
				lastRundate = dateFormat.parse(fundCollectorLastRunTime); // 最后运行时间
				matchDate = NextDateUtil.getNextDay(lastRundate);
				int matchStatus = curDate.compareTo(matchDate);
				// >0 means curDate >matchDate
				// = 0 means curDate = matchDate
				// < 0 curDate < matchDate
				// System.out.println("matchStatus = " + matchStatus);
				if (matchStatus >= 0)
				{
					PropertiesUtil.setProperty("fundCollectorLastRunTime", NextDateUtil.getDateTime(curDate),
					        StaticValue.configPath);
					allFundObjList = new FundInfoCollector().getAllFundInfoList();
					Xml2JavaBeanUtil.putSourceRecord2XmlFile(allFundObjList, xmlDataFilePath, maxStockRecordNums, StaticValue.FundType);
				}
			}

		} catch (UnsupportedOperationException e)
		{
			e.printStackTrace();
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		} catch (ParseException e)
		{
			e.printStackTrace();
		}

	}

	public void start()
	{
		// 第二个参数为首次执行的延时时间，第三个参数为定时执行的间隔时间
		// 实际需求 不符合该条件 故使用自定义方法实现
		service.scheduleAtFixedRate(this, 0, 1, TimeUnit.MINUTES);

	}
}
