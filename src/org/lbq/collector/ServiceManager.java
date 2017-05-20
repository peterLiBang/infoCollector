package org.lbq.collector;

import java.util.ArrayList;
import java.util.List;

public class ServiceManager
{
	
	private  List<ITask> taskList = new ArrayList<ITask>();
	
	public void addTaskListener(ITask task) 
	{
		this.taskList.add(task);
	}
	
	public  void removeListener(ITask task) 
	{
		this.taskList.remove(task);
	}
	
	public List<ITask> getTaskList()
	{
		return this.taskList;
		
	}
	public static void main(String[] args)
	{
		ServiceManager serviceManager = new ServiceManager();
		//间隔周期 一天
		serviceManager.addTaskListener(new FundCollectorTask());
		//间隔周期 一分钟
		//serviceManager.addTaskListener(new StockCollectorTask());
		for(ITask task:serviceManager.getTaskList()) 
		{
			task.start();
		}
	}
}
