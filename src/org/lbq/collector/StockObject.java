package org.lbq.collector;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.log4j.Logger;


//@XmlAccessorType(XmlAccessType.FIELD)
/*@XmlType(propOrder={"stockCode","stockName","todayStartPrice","yesterdayStopPrice","currentPrice","todayHighestPrice","todayLowestPrice","inPrice"
		,"outPrice","numberOfTraded","amountOfTurnover","oneHandStockNums","oneHandPrice","secondHandStockNums","secondHandPrice","thridHandStockNums"
		,"thridHandPrice","fourthHandStockNums","fourthHandPrice","fifthHandStockNums","fifthHandPrice","oneHandStockOutNums","oneHandStockOutPrice"
		,"secondHandStockOutNums","secondHandStockOutPrice","thridHandStockOutNums","thridHandStockOutPrice","fourthHandStockOutNums","fourthHandStockOutPrice","fifthHandStockOutNums"
		,"fifthHandStockOutPrice","date","time"})*/
@XmlRootElement(name = "StockObject") 
public class StockObject
{
	private static Logger logger = Logger.getLogger(StockObject.class);
	// var
	// hq_str_sh601006="大秦铁路,7.760,7.790,7.790,7.840,7.630,7.780,7.790,43288175,334903513.000,190700,7.780,
	// 308500,7.770,243800,7.760,152600,7.750,103100,7.740,45600,7.790,142000,7.800,185800,7.810,288241,7.820,319800,7.830,2017-04-19,15:00:00,00";
	// 0：”大秦铁路”，股票名字；
	/// 1：”27.55″，今日开盘价；
	// 2：”27.25″，昨日收盘价；
	// 3：”26.91″，当前价格；
	// 4：”27.55″，今日最高价；
	// 5：”26.20″，今日最低价；
	// 6：”26.91″，竞买价，即“买一”报价；
	// 7：”26.92″，竞卖价，即“卖一”报价；
	// 8：”22114263″，成交的股票数，由于股票交易以一百股为基本单位，所以在使用时，通常把该值除以一百；
	// 9：”589824680″，成交金额，单位为“元”，为了一目了然，通常以“万元”为成交金额的单位，所以通常把该值除以一万；
	// 10：”4695″，“买一”申请4695股，即47手；
	// 11：”26.91″，“买一”报价；
	// 12：”57590″，“买二”
	// 13：”26.90″，“买二”
	// 14：”14700″，“买三”
	// 15：”26.89″，“买三”
	// 16：”14300″，“买四”
	// 17：”26.88″，“买四”
	// 18：”15100″，“买五”
	// 19：”26.87″，“买五”
	// 20：”3100″，“卖一”申报3100股，即31手；
	// 21：”26.92″，“卖一”报价
	// (22, 23), (24, 25), (26,27), (28, 29)分别为“卖二”至“卖四的情况”
	// 30：”2008-01-11″，日期；
	// 31：”15:05:32″，时间；
	
	
	//@XmlElement(name="stockCode",required=true)
	private String stockCode = null ;
	//@XmlElement(name="stockName",required=true)
	private String stockName = "";
	//@XmlElement(name="todayStartPrice",required=true)
	private String todayStartPrice = "";
	//@XmlElement(name="yesterdayStopPrice",required=true)
	private String yesterdayStopPrice = "";
	//@XmlElement(name="currentPrice",required=true)
	private String currentPrice = "";
	//@XmlElement(name="todayHighestPrice",required=true)
	private String todayHighestPrice = "";
	//@XmlElement(name="todayLowestPrice",required=true)
	private String todayLowestPrice = "";
	//@XmlElement(name="inPrice",required=true)
	private String inPrice = "";
	//@XmlElement(name="outPrice",required=true)
	private String outPrice = "";
	//@XmlElement(name="numberOfTraded",required=true)
	private String numberOfTraded = ""; // 成交数
	//@XmlElement(name="amountOfTurnover",required=true)
	private String amountOfTurnover = ""; // 成交的金额
	
	//@XmlElement(name="oneHandStockNums",required=true)
	private String oneHandStockNums = "";
	//@XmlElement(name="oneHandPrice",required=true)
	private String oneHandPrice = ""; // 一手股票价格
	//@XmlElement(name="secondHandStockNums",required=true)
	private String secondHandStockNums = ""; // 二手股票数量
	//@XmlElement(name="secondHandPrice",required=true)
	private String secondHandPrice = ""; // 二手股票价格
	//@XmlElement(name="thridHandStockNums",required=true)
	private String thridHandStockNums = "";
	//@XmlElement(name="thridHandPrice",required=true)
	private String thridHandPrice = "";
	//@XmlElement(name="fourthHandStockNums",required=true)
	private String fourthHandStockNums = "";
	//@XmlElement(name="fourthHandPrice",required=true)
	private String fourthHandPrice = "";
	//@XmlElement(name="fifthHandStockNums",required=true)
	private String fifthHandStockNums = "";
	//@XmlElement(name="fifthHandPrice",required=true)
	private String fifthHandPrice = "";
	
	//@XmlElement(name="oneHandStockOutNums",required=true)
	private String oneHandStockOutNums = "";
	//@XmlElement(name="oneHandStockOutPrice",required=true)
	private String oneHandStockOutPrice = "";
	//@XmlElement(name="secondHandStockOutNums",required=true)
	private String secondHandStockOutNums = "";
	//@XmlElement(name="secondHandStockOutPrice",required=true)
	private String secondHandStockOutPrice = "";
	//@XmlElement(name="thridHandStockOutNums",required=true)
	private String thridHandStockOutNums = "";
	//@XmlElement(name="thridHandStockOutPrice",required=true)
	private String thridHandStockOutPrice = "";
	//@XmlElement(name="fourthHandStockOutNums",required=true)
	private String fourthHandStockOutNums = "";
	//@XmlElement(name="fourthHandStockOutPrice",required=true)
	private String fourthHandStockOutPrice = "";
	//@XmlElement(name="fifthHandStockOutNums",required=true)
	private String fifthHandStockOutNums = "";
	//@XmlElement(name="fifthHandStockOutPrice",required=true)
	private String fifthHandStockOutPrice = "";
	
	//@XmlElement(name="date",required=true)
	private String date = "";
	//@XmlElement(name="time",required=true)
	private String time = "";

	public StockObject()
	{
	}
	
	public String toString()
	{
		StringBuffer resultBuff = new StringBuffer();
		resultBuff.append( "stockCode = " +this.getStockCode() + " stackName = " + this.getStackName() + " todayStartPrice = "+ this.getTodayStartPrice()+" yesterdayStopPrice = " +this.getYesterdayStopPrice()+"\n");
		resultBuff.append("currentPrice = "+this.getCurrentPrice() + " todayHighestPrice = " + this.getTodayHighestPrice()+" todayLowestPrice = " + this.getTodayLowestPrice()+"\n");
		resultBuff.append("inPrice = " + this.getInPrice() + " outPrice = " + this.getOutPrice() + " numberOfTraded = " + this.getNumberOfTraded() + "\n");
		resultBuff.append("amountOfTurnover = "  +this.getAmountOfTurnover() + " oneHandStockNums = " + this.getOneHandStockNums()+" oneHandStockPrice = "+this.getOneHandPrice()+"\n");
		resultBuff.append("date = " + this.getDate() + "time = " + this.getTime() );
		return resultBuff.toString();
	}
	
	public StockObject(List<String> stockAttrList)
	{
		this.init(stockAttrList);
	}
	
	private void init(List<String> stockAttrList)
	{
		if(stockAttrList.size()<StaticValue.StockFieldsNums)
		{
			logger.warn("stockAttrList's size wrong! please check!");
		}
		this.setStackName(stockAttrList.get(0));
		this.setTodayStartPrice(stockAttrList.get(1));
		this.setYesterdayStopPrice(stockAttrList.get(2));
		this.setCurrentPrice(stockAttrList.get(3));
		this.setTodayHighestPrice(stockAttrList.get(4));
		this.setTodayLowestPrice(stockAttrList.get(5));
		this.setInPrice(stockAttrList.get(6));
		this.setOutPrice(stockAttrList.get(7));
		this.setNumberOfTraded(stockAttrList.get(8));
		this.setAmountOfTurnover(stockAttrList.get(9));
		
		this.setOneHandStockNums(stockAttrList.get(10));
		this.setOneHandPrice(stockAttrList.get(11));
		this.setSecondHandStockNums(stockAttrList.get(12));
		this.setSecondHandPrice(stockAttrList.get(13));
		this.setThridHandStockNums(stockAttrList.get(14));
		this.setThridHandPrice(stockAttrList.get(15));
		this.setFourthHandStockNums(stockAttrList.get(16));
		this.setFourthHandPrice(stockAttrList.get(17));
		this.setFifthHandStockNums(stockAttrList.get(18));
		this.setFifthHandPrice(stockAttrList.get(19));
		
		this.setOneHandStockOutNums(stockAttrList.get(20));
		this.setOneHandStockOutPrice(stockAttrList.get(21));
		this.setSecondHandStockOutNums(stockAttrList.get(22));
		this.setSecondHandStockOutPrice(stockAttrList.get(23));
		this.setThridHandStockOutNums(stockAttrList.get(24));
		this.setThridHandStockOutPrice(stockAttrList.get(25));
		this.setFourthHandStockOutNums(stockAttrList.get(26));
		this.setFourthHandStockOutPrice(stockAttrList.get(27));
		this.setFifthHandStockOutNums(stockAttrList.get(28));
		this.setFifthHandStockOutPrice(stockAttrList.get(29));
		
		this.setDate(stockAttrList.get(30));
		this.setTime(stockAttrList.get(31));
		this.setStockCode(stockAttrList.get(32));
	}
	
	@XmlAttribute(name="stockCode",required=true)
	public String getStockCode()
	{
		return stockCode;
	}
	public void setStockCode(String stockCode)
	{
		this.stockCode = stockCode;
	}
	
	@XmlAttribute(name="stockName",required=true)
	public String getStackName()
	{
		return stockName;
	}
	public void setStackName(String stockName)
	{
		this.stockName = stockName;
	}

	@XmlAttribute(name="todayStartPrice",required=true)
	public String getTodayStartPrice()
	{
		return todayStartPrice;
	}
	public void setTodayStartPrice(String todayPrice)
	{
		this.todayStartPrice = todayPrice;
	}
	
	@XmlAttribute(name="yesterdayStopPrice",required=true)
	public String getYesterdayStopPrice()
	{
		return yesterdayStopPrice;
	}
	public void setYesterdayStopPrice(String yesterdayPrice)
	{
		this.yesterdayStopPrice = yesterdayPrice;
	}
	
	@XmlAttribute(name="currentPrice",required=true)
	public String getCurrentPrice()
	{
		return currentPrice;
	}
	public void setCurrentPrice(String currentPrice)
	{
		this.currentPrice = currentPrice;
	}
	
	@XmlAttribute(name="todayHighestPrice",required=true)
	public String getTodayHighestPrice()
	{
		return todayHighestPrice;
	}
	public void setTodayHighestPrice(String todayHighestPrice)
	{
		this.todayHighestPrice = todayHighestPrice;
	}
	
	@XmlAttribute(name="todayLowestPrice",required=true)
	public String getTodayLowestPrice()
	{
		return todayLowestPrice;
	}
	public void setTodayLowestPrice(String todayLowestPrice)
	{
		this.todayLowestPrice = todayLowestPrice;
	}
	
	@XmlAttribute(name="inPrice",required=true)
	public String getInPrice()
	{
		return inPrice;
	}
	public void setInPrice(String inPrice)
	{
		this.inPrice = inPrice;
	}
	
	@XmlAttribute(name="outPrice",required=true)
	public String getOutPrice()
	{
		return outPrice;
	}
	public void setOutPrice(String outPrice)
	{
		this.outPrice = outPrice;
	}
	
	@XmlAttribute(name="numberOfTraded",required=true)
	public String getNumberOfTraded()
	{
		return numberOfTraded;
	}
	public void setNumberOfTraded(String numberOfTraded)
	{
		this.numberOfTraded = numberOfTraded;
	}
	
	@XmlAttribute(name="amountOfTurnover",required=true)
	public String getAmountOfTurnover()
	{
		return amountOfTurnover;
	}
	public void setAmountOfTurnover(String amountOfTurnover)
	{
		this.amountOfTurnover = amountOfTurnover;
	}
	
	@XmlAttribute(name="oneHandStockNums",required=true)
	public String getOneHandStockNums()
	{
		return oneHandStockNums;
	}
	public void setOneHandStockNums(String oneHandStockNums)
	{
		this.oneHandStockNums = oneHandStockNums;
	}
	
	@XmlAttribute(name="oneHandPrice",required=true)
	public String getOneHandPrice()
	{
		return oneHandPrice;
	}
	public void setOneHandPrice(String oneHandPrice)
	{
		this.oneHandPrice = oneHandPrice;
	}
	
	@XmlAttribute(name="secondHandStockNums",required=true)
	public String getSecondHandStockNums()
	{
		return secondHandStockNums;
	}
	public void setSecondHandStockNums(String secondHandStockNums)
	{
		this.secondHandStockNums = secondHandStockNums;
	}
	
	@XmlAttribute(name="secondHandPrice",required=true)
	public String getSecondHandPrice()
	{
		return secondHandPrice;
	}
	public void setSecondHandPrice(String secondHandPrice)
	{
		this.secondHandPrice = secondHandPrice;
	}
	
	@XmlAttribute(name="thridHandStockNums",required=true)
	public String getThridHandStockNums()
	{
		return thridHandStockNums;
	}
	public void setThridHandStockNums(String thridHandStockNums)
	{
		this.thridHandStockNums = thridHandStockNums;
	}
	
	@XmlAttribute(name="thridHandPrice",required=true)
	public String getThridHandPrice()
	{
		return thridHandPrice;
	}
	public void setThridHandPrice(String thridHandPrice)
	{
		this.thridHandPrice = thridHandPrice;
	}
	
	@XmlAttribute(name="fourthHandStockNums",required=true)
	public String getFourthHandStockNums()
	{
		return fourthHandStockNums;
	}
	public void setFourthHandStockNums(String fourthHandStockNums)
	{
		this.fourthHandStockNums = fourthHandStockNums;
	}
	
	@XmlAttribute(name="fourthHandPrice",required=true)
	public String getFourthHandPrice()
	{
		return fourthHandPrice;
	}
	public void setFourthHandPrice(String fourthHandPrice)
	{
		this.fourthHandPrice = fourthHandPrice;
	}
	
	@XmlAttribute(name="fifthHandStockNums",required=true)
	public String getFifthHandStockNums()
	{
		return fifthHandStockNums;
	}
	public void setFifthHandStockNums(String fifthHandStockNums)
	{
		this.fifthHandStockNums = fifthHandStockNums;
	}
	
	@XmlAttribute(name="fifthHandPrice",required=true)
	public String getFifthHandPrice()
	{
		return fifthHandPrice;
	}
	public void setFifthHandPrice(String fifthHandPrice)
	{
		this.fifthHandPrice = fifthHandPrice;
	}
	
	@XmlAttribute(name="oneHandStockOutNums",required=true)
	public String getOneHandStockOutNums()
	{
		return oneHandStockOutNums;
	}
	public void setOneHandStockOutNums(String oneHandStockOutNums)
	{
		this.oneHandStockOutNums = oneHandStockOutNums;
	}
	
	@XmlAttribute(name="oneHandStockOutPrice",required=true)
	public String getOneHandStockOutPrice()
	{
		return oneHandStockOutPrice;
	}
	public void setOneHandStockOutPrice(String oneHandStockOutPrice)
	{
		this.oneHandStockOutPrice = oneHandStockOutPrice;
	}
	
	@XmlAttribute(name="secondHandStockOutNums",required=true)
	public String getSecondHandStockOutNums()
	{
		return secondHandStockOutNums;
	}
	public void setSecondHandStockOutNums(String secondHandStockOutNums)
	{
		this.secondHandStockOutNums = secondHandStockOutNums;
	}
	
	@XmlAttribute(name="secondHandStockOutPrice",required=true)
	public String getSecondHandStockOutPrice()
	{
		return secondHandStockOutPrice;
	}
	public void setSecondHandStockOutPrice(String secondHandStockOutPrice)
	{
		this.secondHandStockOutPrice = secondHandStockOutPrice;
	}
	
	@XmlAttribute(name="thridHandStockOutNums",required=true)
	public String getThridHandStockOutNums()
	{
		return thridHandStockOutNums;
	}
	public void setThridHandStockOutNums(String thridHandStockOutNums)
	{
		this.thridHandStockOutNums = thridHandStockOutNums;
	}
	
	@XmlAttribute(name="thridHandStockOutPrice",required=true)
	public String getThridHandStockOutPrice()
	{
		return thridHandStockOutPrice;
	}
	public void setThridHandStockOutPrice(String thridHandStockOutPrice)
	{
		this.thridHandStockOutPrice = thridHandStockOutPrice;
	}
	
	@XmlAttribute(name="fourthHandStockOutNums",required=true)
	public String getFourthHandStockOutNums()
	{
		return fourthHandStockOutNums;
	}
	public void setFourthHandStockOutNums(String fourthHandStockOutNums)
	{
		this.fourthHandStockOutNums = fourthHandStockOutNums;
	}
	
	@XmlAttribute(name="fourthHandStockOutPrice",required=true)
	public String getFourthHandStockOutPrice()
	{
		return fourthHandStockOutPrice;
	}
	public void setFourthHandStockOutPrice(String fourthHandStockOutPrice)
	{
		this.fourthHandStockOutPrice = fourthHandStockOutPrice;
	}
	
	@XmlAttribute(name="fifthHandStockOutNums",required=true)
	public String getFifthHandStockOutNums()
	{
		return fifthHandStockOutNums;
	}
	public void setFifthHandStockOutNums(String fifthHandStockOutNums)
	{
		this.fifthHandStockOutNums = fifthHandStockOutNums;
	}
	
	@XmlAttribute(name="fifthHandStockOutPrice",required=true)
	public String getFifthHandStockOutPrice()
	{
		return fifthHandStockOutPrice;
	}
	public void setFifthHandStockOutPrice(String fifthHandStockOutPrice)
	{
		this.fifthHandStockOutPrice = fifthHandStockOutPrice;
	}
	
	@XmlAttribute(name="date",required=true)
	public String getDate()
	{
		return date;
	}
	public void setDate(String date)
	{
		this.date = date;
	}
	
	@XmlAttribute(name="time",required=true)
	public String getTime()
	{
		return time;
	}

	public void setTime(String time)
	{
		this.time = time;
	}

}
