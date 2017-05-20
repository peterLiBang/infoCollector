package org.lbq.collector;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

//此方式是 通过构造element参数来报错值 缺点冗余信息太多 故采用 attribute方式存储
//@XmlAccessorType(XmlAccessType.FIELD)
//@XmlType(propOrder={"fundCode","fundName","fundNickName","unitNet","accumulatedNet","yesterdayUnitNet","yesterdayAccumulatedNet"
//		,"dailyGrowthValue","dailyGrowthRate","subscriptionStatus","redemptionStatus","commissionCharge"})
@XmlRootElement(name = "FundObject") 
public class FundObject
{
	// 基金代号  基金名称  基金名称缩写  单位净值  累计净值   单位净值  累计净值（前一天） 日增长值  日增长率 申购状态  赎回状态    手续费  
	// 申购状态 封闭期 限大额 暂停 开放
	// 赎回状态 封闭期 开放 暂停
	//添加生成XML的注解
	
	
	//@XmlElement(name="fundCode",required=true)
	//@XmlAttribute(name="fundCode",required=true)
	private String fundCode = "" ;
	//@XmlElement(name="fundName",required=true)
	private String fundName = "" ;
	//@XmlElement(name="fundNickName",required=true)
	private String fundNickName = "" ;
	//@XmlElement(name="unitNet",required=true)
	private double unitNet = 0.0 ;
	//@XmlElement(name="accumulatedNet",required=true)
	private double accumulatedNet = 0.0;
	//@XmlElement(name="yesterdayUnitNet",required=true)
	private double yesterdayUnitNet = 0.0 ;
	//@XmlElement(name="yesterdayAccumulatedNet",required=true)
	private double yesterdayAccumulatedNet = 0.0 ;
	//@XmlElement(name="dailyGrowthValue",required=true)
	private double dailyGrowthValue = 0.0 ;
	//@XmlElement(name="dailyGrowthRate",required=true)
	private double dailyGrowthRate = 0.0 ;
	//@XmlElement(name="subscriptionStatus",required=true)
	private int subscriptionStatus = 0 ;
	//@XmlElement(name="redemptionStatus",required=true)
	private int redemptionStatus = 0 ;
	//@XmlElement(name="commissionCharge",required=true)
	private String commissionCharge = "";
	
	private String date = "" ;
	
	

	@Override
	public String toString()
	{
		StringBuffer resultBuff = new StringBuffer();
		resultBuff.append("FundInfo: fundCode = " + this.fundCode + " fundName = " + this.fundName + " fundNickName = " +this.fundNickName );
		resultBuff.append(" unitNet = " + this.getUnitNet() + " accumulatedNet = "+this.getAccumulatedNet() + " yesterdayUnitNet = " + this.getYesterdayUnitNet());
		resultBuff.append(" yesterdayAccumulatedNet = " +this.getYesterdayAccumulatedNet() +" dailyGrowthValue = " + this.getDailyGrowthValue());
		resultBuff.append(" dailyGrowthRate = " + this.getDailyGrowthRate() + " subscriptionStatus = "+ this.getFundStatusStr(this.getSubscriptionStatus())) ;
		resultBuff.append(" redemptionStatus = " + this.getFundStatusStr(this.getRedemptionStatus())  + " commissionCharge = " + this.getCommissionCharge());
		return resultBuff.toString() ;
	}
	
	@XmlAttribute(name="fundCode",required=true)
	public String getFundCode()
	{
		return fundCode;
	}
	public void setFundCode(String fundCode)
	{
		this.fundCode = fundCode;
	}
	
	@XmlAttribute(name="fundName",required=true)
	public String getFundName()
	{
		return fundName;
	}
	
	public void setFundName(String fundName)
	{
		this.fundName = fundName;
	}
	
	@XmlAttribute(name="fundNickName",required=true)
	public String getFundNickName()
	{
		return fundNickName;
	}
	
	public void setFundNickName(String fundNickName)
	{
		this.fundNickName = fundNickName;
	}
	@XmlAttribute(name="unitNet",required=true)
	public double getUnitNet()
	{
		return unitNet;
	}
	
	public void setUnitNet(double unitNet)
	{
		this.unitNet = unitNet;
	}
	
	@XmlAttribute(name="accumulatedNet",required=true)
	public double getAccumulatedNet()
	{
		return accumulatedNet;
	}
	
	public void setAccumulatedNet(double accumulatedNet)
	{
		this.accumulatedNet = accumulatedNet;
	}
	@XmlAttribute(name="yesterdayUnitNet",required=true)
	public double getYesterdayUnitNet()
	{
		return yesterdayUnitNet;
	}
	
	public void setYesterdayUnitNet(double yesterdayUnitNet)
	{
		this.yesterdayUnitNet = yesterdayUnitNet;
	}
	
	@XmlAttribute(name="yesterdayAccumulatedNet",required=true)
	public double getYesterdayAccumulatedNet()
	{
		return yesterdayAccumulatedNet;
	}
	public void setYesterdayAccumulatedNet(double yesterdayAccumulatedNet)
	{
		this.yesterdayAccumulatedNet = yesterdayAccumulatedNet;
	}
	
	@XmlAttribute(name="dailyGrowthValue",required=true)
	public double getDailyGrowthValue()
	{
		return dailyGrowthValue;
	}
	public void setDailyGrowthValue(double dailyGrowthValue)
	{
		this.dailyGrowthValue = dailyGrowthValue;
	}
	
	@XmlAttribute(name="dailyGrowthRate",required=true)
	public double getDailyGrowthRate()
	{
		return dailyGrowthRate;
	}
	public void setDailyGrowthRate(double dailyGrowthRate)
	{
		this.dailyGrowthRate = dailyGrowthRate;
	}
	
	@XmlAttribute(name="subscriptionStatus",required=true)
	public int getSubscriptionStatus()
	{
		return subscriptionStatus;
	}
	
	public void setSubscriptionStatus(int subscriptionStatus)
	{
		this.subscriptionStatus = subscriptionStatus;
	}
	
	@XmlAttribute(name="redemptionStatus",required=true)
	public int getRedemptionStatus()
	{
		return this.redemptionStatus ;
	}
	public void setRedemptionStatus(int redemptionStatus)
	{
		this.redemptionStatus = redemptionStatus;
	}
	
	@XmlAttribute(name="commissionCharge",required=true)
	public String getCommissionCharge()
	{
		return commissionCharge;
	}
	public void setCommissionCharge(String commissionCharge)
	{
		this.commissionCharge = commissionCharge;
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
	
	private String getFundStatusStr(int type)
	{
		String statusStr = null ;
		switch (type)
		{
		case StaticValue.LargePurchaseStatus:
			statusStr = "限大额" ;
			break;
		case StaticValue.OpenStatus:
			statusStr = "开放";
			break ;
		case StaticValue.puaseStatus:
			statusStr = "暂停" ;
			break ;
		case StaticValue.StopStatus:
			statusStr = "封闭" ;
			break ;
		default:
			statusStr = "none status";
			break;
		}
		return statusStr ;
	}


}
