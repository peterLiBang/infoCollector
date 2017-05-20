package org.lbq.collector;

public class FundNetWorthPageInfoObj
{
	
	private int pages = 0 ; 
	private int currentPage = 0 ;
	private int nextPage = 0 ;
	private int records = 0 ;
	private String showDay = "" ;
	private boolean firstPageFlag = false ;
	private boolean endPageFlag = false ;
	//除current page外的变量只需在第一次赋值即可
	
	public int getPages()
	{
		return pages;
	}
	public void setPages(int pages)
	{
		this.pages = pages;
	}
	public int getCurrentPage()
	{
		return currentPage;
	}
	public void setCurrentPage(int currentPage)
	{
		this.currentPage = currentPage;
	}
	public int getRecords()
	{
		return records;
	}
	public void setRecords(int records)
	{
		this.records = records;
	}
	public String getShowDay()
	{
		return showDay;
	}
	public void setShowDay(String showDay)
	{
		this.showDay = showDay;
	}
	
	public boolean isFirstPage()
	{
		return this.firstPageFlag;
	}
	public void setFirstPageFlag(boolean firstPageFlag)
	{
		this.firstPageFlag = firstPageFlag;
	}
	public boolean isEndPage()
	{
		return this.endPageFlag;
	}
	public void setEndPageFlag(boolean endPageFlag)
	{
		this.endPageFlag = endPageFlag;
	}
	public int getNextPage()
	{
		return nextPage;
	}
	public void setNextPage(int nextPage)
	{
		this.nextPage = nextPage;
	}
	
}
