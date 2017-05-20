package org.lbq.utils;

import org.junit.Test;

import com.google.common.base.Stopwatch;



public class TimerWatch {

	
	public static void countTime()
	{
		int index = 0 ; 
		Stopwatch watch = new Stopwatch(); // 
		watch.start() ;
		for(int i = 0 ; i < 10000 ; i++)
		{
			for(int j = 0 ; j < 10000 ; j++)
			{
				index ++ ;
			}
		}
		System.out.println(index);
		watch.stop();
		System.out.println(watch.elapsedMillis()+"ms");
	}
	@Test
	public void testTime()
	{
		countTime();
	}
}
