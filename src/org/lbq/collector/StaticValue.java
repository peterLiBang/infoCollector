package org.lbq.collector;
import java.io.File;
public class StaticValue
{
	public static final String configPath = new File("").getAbsolutePath()+File.separator+"config.properties" ;
	public static final String stockListFilePath = new File("").getAbsolutePath()+File.separator + "stockCode.txt";
	public static final int InitPage = 1 ;
	public static final int LargePurchaseStatus = 1 ; //限大额
	public static final int OpenStatus = 2 ; //开放状态
	public static final int StopStatus = 3 ; //封闭状态
	public static final int puaseStatus = 4 ; //暂停状态
	public static final int StockFieldsNums = 33 ; //stock的字段数
	public static final int StockType = 1 ;
	public static final int FundType = 2 ;
	
}
