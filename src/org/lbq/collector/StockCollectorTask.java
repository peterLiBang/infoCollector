package org.lbq.collector;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.lbq.utils.GetStockIDUtils;
import org.lbq.utils.PropertiesUtil;
import org.lbq.utils.Xml2JavaBeanUtil;



public class StockCollectorTask implements Runnable, ITask {

	private static Logger logger = Logger.getLogger(StockCollectorTask.class);
	ScheduledExecutorService service = null;
	StockInfoCollector stockCollector = null;
	List<Object> stockObjList = new ArrayList<Object>();
	
	public StockCollectorTask() {
		service = Executors.newSingleThreadScheduledExecutor();
		stockCollector = new StockInfoCollector();
	}

	@Override
	public void run() {

		//String configPath = new File("").getAbsolutePath()+File.separator+"config.properties" ; //获取相对路径
		//String testStockCode = "sh000001"; //default test code
		//String stockListFilePath = new File("").getAbsolutePath()+File.separator + "stockCode.txt";
		int threadNum = 0;
		String xmlDataFilePath = "";
		int maxStockRecordNums = 0 ;
		try
		{
			xmlDataFilePath = PropertiesUtil.getPropertyByKey("xmlDataFilePath", StaticValue.configPath);
			maxStockRecordNums = Integer.parseInt(PropertiesUtil.getPropertyByKey("maxStockRecordNums", StaticValue.configPath));
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		ThreadPoolExecutor executor = null;
		List<String> stockCodeList = new ArrayList<String>();
		try {
			stockCodeList = GetStockIDUtils.getAllLines(StaticValue.stockListFilePath);
		} catch (IOException e) {
			e.printStackTrace();
		}
		threadNum = getThreadNums(StaticValue.configPath, threadNum);

		if (threadNum > 0) {
			executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(threadNum);
			logger.info("start service and threadNums = " + threadNum);
		} else {
			//System.out.println("now in testModel!");
			logger.warn("now in testModel!");
			// handlerServiceLog.warn("thread num must >0 ");
		}

		for (int i = 0; i < stockCodeList.size(); i++) {
			MyTask myTask = new MyTask(stockCodeList.get(i));
			executor.execute(myTask);
		}

		try
		{
			//问题 第一次获取的时候会出现stockObjList为空的情况
			//Thread.sleep(3*1000); //waiting 3s for result
			//System.out.println("stockList'size = " + stockObjList.size());
			if(null!=stockObjList && stockObjList.size()>0)
			{
				logger.warn("stockList'size = " + stockObjList.size());
				Xml2JavaBeanUtil.putSourceRecord2XmlFile(stockObjList,xmlDataFilePath,maxStockRecordNums,StaticValue.StockType);
				stockObjList.clear();
			}
		} catch (IOException e)
		{
			e.printStackTrace();
		} 
		
		executor.shutdown();
	}

	private int getThreadNums(String configPath, int threadNum) {
		// 获取线程数 是否进入调试模式等 配置
		try {
			if (PropertiesUtil.getPropertyByKey("testModel", configPath)
					.equals("true")) {
				threadNum = Integer.parseInt(PropertiesUtil.getPropertyByKey(
						"testTime", configPath));
			} else {
				threadNum = Integer.parseInt(PropertiesUtil.getPropertyByKey(
						"threadNum", configPath));
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return threadNum;
	}

	public void start() {
		service.scheduleAtFixedRate(this, 0, 1, TimeUnit.MINUTES);
	}
//匿名內部类
	class MyTask implements Runnable {
		private String stockCode = "";
		public MyTask(String stockCode) {
			this.stockCode = stockCode;
		}

		@Override
		public void run() {
			this.task();
		}

		public void task() {
			try {
				StockObject stockObj = new StockObject();
				String htmlContent = stockCollector.collectStockInfo(this.stockCode);
				stockObj = stockCollector.getstockInfoObject(htmlContent, this.stockCode);
				stockObjList.add(stockObj);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
