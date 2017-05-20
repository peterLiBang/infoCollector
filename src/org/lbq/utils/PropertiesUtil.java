package org.lbq.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import java.util.Map.Entry;

public class PropertiesUtil
{
	private static Map<String, String> loadProperties(String configName) throws FileNotFoundException, IOException {
		Map<String, String> map = new HashMap<String, String>();
		Properties props = new Properties();
		FileInputStream fis =  new FileInputStream(configName);
		props.load(fis);
		Set<Entry<Object, Object>> objs = props.entrySet();
		for (Entry<Object, Object> obj : objs) {

			Object key = obj.getKey();
			Object value = obj.getValue();
			if (key != null && !key.toString().isEmpty()) {
				if (!map.containsKey(key)) {
					map.put(key.toString().trim(), value.toString().trim());
				}
			}
		}
		if(!map.isEmpty())
		{
			if(fis!=null)
			{
				fis.close();
			}
			return map ;
		}
		else{
			//log
			//logger.warn("In PropertiesUtil there not any property ");
			if(fis!=null)
			{
				fis.close();
			}
			return null;
		}
	}

	public static Map<String, String> getAllProperties(String configName) throws FileNotFoundException, IOException {
		Map<String,String> map = new HashMap<String,String>();
		map = PropertiesUtil.loadProperties(configName);
		return map;
	}
	
	public static String getPropertyByKey(String key,String configName) throws FileNotFoundException, IOException
	{
		String result = null ;
		Map<String,String> resultMap = new HashMap<String,String>();
		resultMap = PropertiesUtil.getAllProperties(configName);
		if(resultMap.containsKey((Object)key))
		{	
			result = String.valueOf(resultMap.get(key));
		}
		if(result!=null)
		{
			
			return result;
		}
		else
		{
			//log
			System.out.println("there no that: "+ key +" property!");
			return null ;
		}
	}
	
	
	public static boolean setProperty(String key,String value,String configName) throws FileNotFoundException, IOException
	{
		boolean containFlag = false ;
		Properties props = new Properties();
		FileInputStream fis =  new FileInputStream(configName);
		props.load(fis);
		Set<Entry<Object, Object>> objs = props.entrySet();
		for(Entry<Object,Object>entry:objs)
		{
			
			if(entry.getKey().toString().equals(key))
			{
				//表示覆盖
				containFlag = true ;
				entry.setValue((Object)value);
				
			}
		}
		//表示新建
		if(!containFlag)
		{
			props.setProperty(key, value);
		}
		if(fis!=null)
		{
			fis.close();
		}
		//写入
		FileOutputStream out =  new FileOutputStream(configName);
		props.store(out, null);
		
		if(out!=null)
		{
			out.close();
		}
		return containFlag ;
	}
	
	@Test
	public void testGetAllProperties()
	{
		try
		{
			String configName = "config.properties" ;
			Set<Entry<String, String>> valObjs = null;
			valObjs = getAllProperties(configName).entrySet();
			for (Entry<String, String> valObj : valObjs) {
				String key = valObj.getKey();
				String value = valObj.getValue();
				System.out.println("key = " + key + "  value = "+value );
			}
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	@Test
	public void testGetPropertyByKey()
	{
		String configName = "config.properties" ;
		try
		{
			
			Assert.assertEquals("hello",PropertiesUtil.getPropertyByKey("test",configName));
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void testSetProperty()
	{
		
		String configName = "Log_Count.properties" ;
		try
		{
			Assert.assertEquals(false,PropertiesUtil.setProperty("testSetProperty", "false", configName));
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	
	
	
	
}
