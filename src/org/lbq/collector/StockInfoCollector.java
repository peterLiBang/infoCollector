package org.lbq.collector;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.log4j.Logger;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.junit.Test;
import org.lbq.utils.DateUtil;
import org.lbq.utils.GetStockIDUtils;
import org.lbq.utils.GloableHttpUtils;
import org.lbq.utils.PropertiesUtil;
import org.lbq.utils.StreamToStringUtils;
import org.lbq.utils.Xml2JavaBeanUtil;
import org.lbq.utils.XmlStringUtils;

public class StockInfoCollector
{

	//数据示例  上证指数,3215.3963,3222.1673,3196.7133,3225.0546,3196.4884,0,0,188661353,211866767668,0,0,
	// (13) 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2017-04-18,15:01:03,00
	
	//0：”大秦铁路”，股票名字；
	///1：”27.55″，今日开盘价；
	//2：”27.25″，昨日收盘价；
	//3：”26.91″，当前价格；
	//4：”27.55″，今日最高价；
	//5：”26.20″，今日最低价；
	//6：”26.91″，竞买价，即“买一”报价；
	//7：”26.92″，竞卖价，即“卖一”报价；
	//8：”22114263″，成交的股票数，由于股票交易以一百股为基本单位，所以在使用时，通常把该值除以一百；
	//9：”589824680″，成交金额，单位为“元”，为了一目了然，通常以“万元”为成交金额的单位，所以通常把该值除以一万；
	//10：”4695″，“买一”申请4695股，即47手；
	//11：”26.91″，“买一”报价；
	//12：”57590″，“买二”
	//13：”26.90″，“买二”
	//14：”14700″，“买三”
	//15：”26.89″，“买三”
	//16：”14300″，“买四”
	//17：”26.88″，“买四”
	//18：”15100″，“买五”
	//19：”26.87″，“买五”
	//20：”3100″，“卖一”申报3100股，即31手；
	//21：”26.92″，“卖一”报价
	//(22, 23), (24, 25), (26,27), (28, 29)分别为“卖二”至“卖四的情况”
	//30：”2008-01-11″，日期；
	//31：”15:05:32″，时间；
	private static Logger logger = Logger.getLogger(StockInfoCollector.class);
	HtmlCleaner htmlCleaner = new HtmlCleaner();

	//获取股票信息
	//caijingUrl='http://hq.sinajs.cn/list='+seqNum
	public String collectStockInfo(String seqNum) throws ClientProtocolException, IOException
	{
		String stackInfoUrl="http://hq.sinajs.cn/list="+seqNum ;
		String htmlContent = "" ;
		htmlContent = getStockInfoByHtmlCleaner(stackInfoUrl);
		return htmlContent ;
		//使用该方法先解决中文字符问题
		//htmlContent = getStackInfoByGetMethod(stackInfoUrl);
	}

	private String getStockInfoByHtmlCleaner(String stackInfoUrl) throws IOException, MalformedURLException
	{
		String htmlContent = "" ;
		@SuppressWarnings("deprecation")
		TagNode root = htmlCleaner.clean(new URL(stackInfoUrl),"GBK");
		htmlContent = root.getText().toString();
		if(null==htmlContent||htmlContent.isEmpty())
		{
			//System.out.println("htmlContent is null or empty,pealse check your url!");
			logger.warn("htmlContent is null or empty,pealse check your url!");
			return null ;
		}
		return htmlContent ;
	}

	public String getStockInfoByGetMethod(String stackInfoUrl) throws IOException, ClientProtocolException
	{
		// HttpClient 超时配置
		CloseableHttpClient httpClient = GloableHttpUtils.getInstance().getCloseableHttpClient();
		CloseableHttpResponse response = null;
		HttpGet httpGet = null;
		httpGet =  GloableHttpUtils.getInstance().getHttpGetByUri(stackInfoUrl);
		httpGet.setHeader("Content-Type","application/x-javascript;charset=GBK");
		InputStream in;
		response = httpClient.execute(httpGet);
		System.out.println("responseEncoding = " + response.getEntity().getContentEncoding());
		in = response.getEntity().getContent();
		//字符编码问题需要解决
		String htmlContent = StreamToStringUtils.convertStreamToString(in);
		//htmlContent = new String(htmlContent.getBytes("gbk"),"utf-8");
		return htmlContent ;
	}
	
	public StockObject getstockInfoObject(String htmlContent,String stockCode)
	{
		//String result = "";
		String stockObjectStr = "" ;
		String[] stockAttrs = new String[]{};
		List<String> stockAttrList = null ;
		StockObject stockObj = null ;
		 
		stockObjectStr = htmlContent.substring(htmlContent.indexOf("\"") + 1, htmlContent.length() - 3) ;
		stockAttrs = stockObjectStr.split(",");
		if(stockAttrs.length>0&&null!=stockAttrs)
		{
			//此处需要强制转换为ArrayList实例对象
			stockAttrList = new ArrayList<String>(Arrays.asList(stockAttrs));
			if(!stockAttrList.isEmpty()&&null!=stockAttrList)
			{
				stockAttrList.remove(stockAttrList.size()-1); //移除最后一个元素
				stockAttrList.add(stockCode); //stockCode作为最后一个元素添加
				stockObj = new StockObject(stockAttrList);
			}
			else
			{
				logger.warn("action Array2List get someThing wrong!");
			}
		}
		logger.info(stockObj.toString());	
		return stockObj;
	}

	public void putStockRecord2XmlFile(List<Object> stockObjList) throws IOException
	{
		String fileName = "" ;
		String xmlDataFilePath = "" ;
		long millis = 0L ;
		String resultXmlStr = "" ;
		String baseFileName = "stock.xml" ;
		millis = DateUtil.getTotalSec(new Date()); //获取当前毫妙数
		fileName = millis + "_"+baseFileName;
		xmlDataFilePath = PropertiesUtil.getPropertyByKey("xmlDataFilePath", StaticValue.configPath);
		if(null!=fileName && !fileName.isEmpty() && null!=xmlDataFilePath && !xmlDataFilePath.isEmpty())
		{
			resultXmlStr = Xml2JavaBeanUtil.converBeanList2Xml(stockObjList);
			XmlStringUtils.putString2XmlFile(resultXmlStr, fileName, xmlDataFilePath);
		}
		else
		{
			logger.warn("stockInfo's fileName error!");
		}
	}
	
	//发现的问题 使用element模式会导致 数据的冗余信息太多  可以考虑将数据组织成attribute的方式
	@Test
	public void testStackInfo()
	{
		//String path = new File("").getAbsolutePath()+File.separator+"config.properties" ;
		//String testStockCode = "sh000001"; //default test code 
		String filePath = "stockCode.txt" ;
		List<String> stockCodeList = new ArrayList<String>();
		try 
		{
			stockCodeList = GetStockIDUtils.getAllLines(filePath);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try
		{
			for(String stockCode:stockCodeList) 
			{
				getstockInfoObject(this.collectStockInfo(stockCode),stockCode);
			}
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
