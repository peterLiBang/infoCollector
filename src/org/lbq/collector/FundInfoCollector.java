package org.lbq.collector;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.log4j.Logger;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;
import org.junit.Test;
import org.lbq.utils.GloableHttpUtils;
import org.lbq.utils.PropertiesUtil;
import org.lbq.utils.StreamToStringUtils;
import org.lbq.utils.Xml2JavaBeanUtil;
import org.lbq.utils.XmlStringUtils;

public class FundInfoCollector
{

	// http://fund.eastmoney.com/data/fundranking.html#tall;c0;r;szzf;pn50;ddesc;qsd20160407;qed20170407;qdii;zq;gg;gzbd;gzfs;bbzt;sfbb
	// *[@id="dbtable"]

	// 通过下面的这个方法可以直接获取到源数据 开放式基金净值表 js格式数据返回
	// http://fund.eastmoney.com/Data/Fund_JJJZ_Data.aspx?t=1&lx=1&letter=&gsid=&text=&sort=zdf,desc&page=2,200&dt=1491711281114&atfc=&onlySale=0

	// http://fund.eastmoney.com/000628.html 其中000628是基金代码 进入对应的基金吧
	// 查看对应的走势图
	// *[@id="estimatedMap"]/div[1]

	// 基金排行
	// 第一页 V=...参数不是必须 sd=2016-04-07&ed=2017-04-07 指定天数范围内的排行
	// http://fund.eastmoney.com/data/rankhandler.aspx?op=ph&dt=kf&ft=all&rs=&gs=0&sc=zzf&st=desc&sd=2016-04-07&ed=2017-04-07&qdii=&tabSubtype=,,,,,&pi=1&pn=50&dx=1&v=0.7440539393708256
	// 第二页
	// http://fund.eastmoney.com/data/rankhandler.aspx?op=ph&dt=kf&ft=all&rs=&gs=0&sc=zzf&st=desc&sd=2016-04-07&ed=2017-04-07&qdii=&tabSubtype=,,,,,&pi=2&pn=50&dx=1&v=0.32578910780577597

	// 基金公司一览表 按照管理规模降序
	// http://fund.eastmoney.com/company/default.html#scomscope;ddesc
	// http://fund.eastmoney.com/Data/FundRankScale.aspx?v=0.5474496543249804
	// //js格式返回的数据

	private static Logger logger = Logger.getLogger(FundInfoCollector.class);
	HtmlCleaner htmlCleaner = new HtmlCleaner();
	FundNetWorthPageInfoObj pageInfo = new FundNetWorthPageInfoObj();
	String urlAddr = "http://fund.eastmoney.com/fund.html#os_0;isall_0;ft_;pt_1";

	// 该方式通过html页面解析 缺点下一页由js控制 处理困难
	public void getFundInfoFromTianTian() throws MalformedURLException, IOException, XPatherException
	{

		TagNode root = htmlCleaner.clean(new URL(urlAddr));
		root.getText();
		Object[] results = root.evaluateXPath("//*[@id=\"oTable\"]/tbody");
		if (results.length > 0)
		{
			//System.out.println("there :" + results.length + " object");
			for (Object tag : results)
			{
				@SuppressWarnings({ "unchecked", "unused" })
				List<TagNode> list = ((TagNode) tag).getAllChildren();
				//System.out.println("list 's size = " + list.size());

			}
		}
	}

	
	
	// 第二种方法通过接口调用直接获取数据 但是需要进行格式解析
	// 暂时不清楚count 与 indexsy的作用
	// String count =
	// subHtmlStr.substring(subHtmlStr.indexOf("count:")+"count:".length(),
	// subHtmlStr.indexOf(",record"));
	// System.out.println("count = " + count);
	// String indexsy =
	// subHtmlStr.substring(subHtmlStr.indexOf("indexsy:")+"indexsy:".length(),
	// subHtmlStr.indexOf(",showday"));
	// System.out.println("indexsy = "+indexsy);
	// record:"5010",pages:"26",curpage:"2",indexsy:[-0.91,-0.80,-1.26,],showday:["2017-04-14","2017-04-13"]

	public List<Object> getAllFundInfoList() throws InterruptedException, UnsupportedOperationException, IOException
	{
		// HttpClient 超时配置
		List<Object>AllFundObjList = new ArrayList<Object>();
		CloseableHttpClient httpClient = GloableHttpUtils.getInstance().getCloseableHttpClient();
		CloseableHttpResponse response = null;
		// 创建一个GET请求获取基金净值信息
		HttpGet httpGet = null;
		InputStream in = null;
		pageInfo.setNextPage(StaticValue.InitPage);
		while (!pageInfo.isEndPage())
		{
			List<Object> pageFundObjList = new ArrayList<Object>();
			httpGet = GloableHttpUtils.getInstance().getHttpGetByUri(this.getFundUriByIndex(pageInfo.getNextPage()));
			// 控制时间
			Thread.sleep(5000);
			// 发送请求，并执行
			if (null != httpGet)
			{
				response = httpClient.execute(httpGet);
			}
			in = response.getEntity().getContent();
			String html = StreamToStringUtils.convertStreamToString(in);
			String subHtmlStr = html.substring(html.indexOf("datas:"), html.indexOf("}"));
			String datas = subHtmlStr.substring(subHtmlStr.indexOf("datas:[") + "datas:[".length(),
			        subHtmlStr.indexOf("]],") + 1);
			extractNetWorthPageInfo(subHtmlStr);
			pageFundObjList = getFundObjectList(datas);
			AllFundObjList.addAll(pageFundObjList);
			
		}
		if (in != null)
		{
			in.close();
		}
		return AllFundObjList;

	}

	private String getFundUriByIndex(int index)
	{
		if (index <= 0)
		{
			logger.warn("illegal index please check again!");
			return null;
		}
		String fundUri = "http://fund.eastmoney.com/Data/Fund_JJJZ_Data.aspx?t=1&lx=1&letter=&gsid=&text=&sort=zdf,desc&page="
		        + index + ",200&atfc=&onlySale=0";
		// String fundUri =
		// "http://fund.eastmoney.com/Data/Fund_JJJZ_Data.aspx?t=1&lx=1&letter=&gsid=&text=&sort=zdf,desc&page="+index+",200&dt=1491711281&atfc=&onlySale=0";
		return fundUri;
	}

	private List<Object> getFundObjectList(String datas) throws IOException
	{
		String[] dataObjs = datas.split("]");
		List<Object> fundObjList = new ArrayList<Object>();
		List<String> dataObjList = new ArrayList<String>();
		dataObjList = Arrays.asList(dataObjs);
		//String xmlDataFilePath = PropertiesUtil.getPropertyByKey("xmlDataFilePath", StaticValue.configPath);
		if (null != dataObjs && dataObjs.length >= 12)
		{
			// 基金代号 基金名称 基金名称缩写 单位净值 累计净值 单位净值 累计净值（前一天） 日增长值 日增长率 申购状态 赎回状态
			// 手续费(倒数第四个field值)
			for (String ObjStr : dataObjList)
			{
				FundObject fundObj = new FundObject();
				String dataObjStr = ObjStr.substring("[".length());
				// data的每个字段内容 需要配合规则文件生成对象
				String[] fields = dataObjStr.split(",");
				fundObj.setFundCode(repairStr(fields[0]));
				//System.out.println("fund code = " + fundObj.getFundCode());
				fundObj.setFundName(repairStr(fields[1]));
				fundObj.setFundNickName(repairStr(fields[2]));
				fundObj.setUnitNet(Double.parseDouble(repairStr(fields[3])));
				fundObj.setAccumulatedNet(Double.parseDouble(repairStr(fields[4])));
				fundObj.setYesterdayUnitNet(Double.parseDouble(repairStr(fields[5])));
				fundObj.setYesterdayAccumulatedNet(Double.parseDouble(repairStr(fields[6])));
				fundObj.setDailyGrowthValue(Double.parseDouble(repairStr(fields[7])));
				fundObj.setDailyGrowthRate(Double.parseDouble(repairStr(fields[8])));
				// System.out.println("after repair the string is " +
				// repairStr(fields[10]));
				fundObj.setSubscriptionStatus(getFundStatusType(repairStr(fields[9])));
				fundObj.setRedemptionStatus(getFundStatusType(repairStr(fields[10])));
				fundObj.setCommissionCharge(repairStr(fields[fields.length - 4]));
				//System.out.println("fund's CommissionCharge = " + fundObj.getCommissionCharge());
				String date = pageInfo.getShowDay().substring(0, "2017-04-17".length());
				fundObj.setDate(date);
				logger.info("get a fund and it's detail info is:");
				logger.info(fundObj);
				
				//fileName =  fundObj.getFundCode() + "_" + date + "_" + baseFileName ;
				//String fundObjXmlStr = Xml2JavaBeanUtil.convertBean2Xml(fundObj);
				/*if(null!=fileName && !fileName.isEmpty() && null!=xmlDataFilePath && !xmlDataFilePath.isEmpty())
				{
					XmlStringUtils.putString2XmlFile(fundObjXmlStr, fileName,xmlDataFilePath);
				}
				else
				{
					logger.warn("check your xmlDataFilePath");
				}*/
				fundObjList.add(fundObj);
			}
		}
		return fundObjList;
	}

	private String repairStr(String data)
	{
		if (null == data)
		{
			//System.out.println("in repairStr function that string is null!");
			logger.warn("in repairStr function that string is null!");
			return "0.0";
		}
		if (data.contains("\""))
		{
			data = data.replace('"', ' ').trim();
		}
		if (data.contains("["))
		{
			data = data.replace('[', ' ').trim();
		}
			
		if (data.trim().isEmpty())
		{
			data = "0.0";
		}
		return data.trim();
	}

	private int getFundStatusType(String detailStr)
	{
		int status = 0;
		if (detailStr.equals("限大额"))
		{
			status = StaticValue.LargePurchaseStatus;
		}
		if (detailStr.equals("封闭期"))
		{
			status = StaticValue.StopStatus;
		}
		if (detailStr.equals("开放申购") || detailStr.equals("开放赎回"))
		{
			status = StaticValue.OpenStatus;
		}
		if (detailStr.equals("暂停申购") || detailStr.equals("暂停赎回"))
		{
			status = StaticValue.puaseStatus;
		}
		return status;

	}

	private void extractNetWorthPageInfo(String subHtmlStr)
	{

		// 数据格式
		// record:"5010",pages:"26",curpage:"2",indexsy:[-0.91,-0.80,-1.26,],showday:["2017-04-14","2017-04-13"]
		String record = "";
		String pages = "";
		String showDay = "";
		String curpage = "";

		curpage = subHtmlStr.substring(subHtmlStr.indexOf("curpage:") + "curpage:".length() + 1,
		        subHtmlStr.indexOf(",indexsy") - 1);
		pageInfo.setCurrentPage(Integer.parseInt(curpage));
		pageInfo.setNextPage(pageInfo.getCurrentPage() + 1);
		//System.out.println("current page = " + curpage);
		if (pageInfo.getCurrentPage() == 1)
		{
			pageInfo.setFirstPageFlag(true);
		} else
		{
			pageInfo.setFirstPageFlag(false);
		}

		if (pageInfo.isFirstPage())
		{
			//System.out.println("start getting first page info.....");
			logger.info("start getting first page info.....");

			record = subHtmlStr.substring(subHtmlStr.indexOf("record:") + "record:".length() + 1,
			        subHtmlStr.indexOf(",pages") - 1);
			logger.info("record = " + record);
			//System.out.println("record = " + record);
			pageInfo.setRecords(Integer.parseInt(record));

			pages = subHtmlStr.substring(subHtmlStr.indexOf("pages:") + "pages:".length() + 1,
			        subHtmlStr.indexOf(",curpage") - 1);
			pageInfo.setPages(Integer.parseInt(pages));
			//System.out.println("pages = " + pages);
			logger.info("pages = " + pages);

			showDay = subHtmlStr.substring(subHtmlStr.indexOf("showday:") + "showday:".length() + 1,
			        subHtmlStr.length() - 1);
			showDay = this.repairStr(showDay);
			//System.out.println("showday = " + showDay);
			logger.info("showday = " + showDay);
			pageInfo.setShowDay(showDay);
		}

		if (pageInfo.getCurrentPage() >= pageInfo.getPages())
		{
			logger.warn("end getting......");
			pageInfo.setEndPageFlag(true);
		}

	}

	@Test
	public void testGetMethod2()
	{
		try
		{
			System.out.println("AllFundInfoList's size = " + this.getAllFundInfoList().size());
		} catch (UnsupportedOperationException e)
		{
			e.printStackTrace();
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	@Test
	public void testGetMethod1()
	{
		try
		{
			this.getFundInfoFromTianTian();
		} catch (MalformedURLException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		} catch (XPatherException e)
		{
			e.printStackTrace();
		}
	}

}
