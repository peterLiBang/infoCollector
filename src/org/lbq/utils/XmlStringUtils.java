package org.lbq.utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class XmlStringUtils
{
	
	public static void putString2XmlFile(String xml ,String fileName,String filePath)
	{
		String resultPath = filePath + fileName ;
		putString2XmlFile(xml,resultPath);
	}
	
	public static void putString2XmlFile(String xml,String fileName)
	{
		PrintWriter out = null ;
		try
		{
			out = new PrintWriter(new BufferedWriter(new FileWriter(fileName)));
			out.write(xml, 0, xml.length());
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		finally {
			out.flush();
			out.close();
		}
	}

}
