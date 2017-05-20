package org.lbq.utils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class GetStockIDUtils {
	// 读取文件指定行。
	static String readAppointedLineNumber(File sourceFile, int lineNumber)
			throws IOException {
		FileReader in = new FileReader(sourceFile);
		LineNumberReader reader = new LineNumberReader(in);
		String s = "";
		if (lineNumber <= 0 || lineNumber > getTotalLines(sourceFile)) {
			System.out.println("不在文件的行数范围(1至总行数)之内。");
			System.exit(0);
		}
		int lines = 0;
		while (s != null) {
			lines++;
			s = reader.readLine();
			if ((lines - lineNumber) == 0) {
				// System.out.println(s);
				reader.close();
				in.close();
				return s;
			}
		}
		reader.close();
		in.close();
		return null;
	}

	// 文件内容的总行数。
	static int getTotalLines(File file) throws IOException {
		FileReader in = new FileReader(file);
		LineNumberReader reader = new LineNumberReader(in);
		String s = reader.readLine();
		int lines = 0;
		while (s != null) {
			lines++;
			s = reader.readLine();
		}
		reader.close();
		in.close();
		return lines;
	}
//返回所有记录
	public static List<String> getAllLines(String filePath)
			throws IOException {
		if(null == filePath || filePath.isEmpty()) 
		{
			System.out.println("file's path is cann't be empty or null!");
			return null ;
		}
		File file = new File(filePath);
		List<String> resultList = new ArrayList<String>();
		FileReader in = new FileReader(file);
		LineNumberReader reader = new LineNumberReader(in);
		String s = reader.readLine();
		while ((s = reader.readLine())!= null) {
			resultList.add(s);
		}
		reader.close();
		in.close();
		return resultList;
	}

	/**
	 * 读取文件指定行。
	 */
	@Test
	public void testGetFile() throws IOException {
		// 指定读取的行号
		int lineNumber = 2;
		// 读取文件
		File sourceFile = new File("stockCode.txt");
		// 读取指定的行
		String result = readAppointedLineNumber(sourceFile, lineNumber);
		System.out.println(result);
		// 获取文件的内容的总行数
		System.out.println(getTotalLines(sourceFile));
	}
	
	@Test
	public void testGetAllStocks() 
	{
		String filePath = "stockCode.txt" ;
		List<String> stockCodeList = new ArrayList<String>();
		int index = 0 ;
		try 
		{
			stockCodeList = GetStockIDUtils.getAllLines(filePath);
			if(stockCodeList.size()>0) 
			{
				for(String stockCode:stockCodeList)
				{
					index ++ ;
					System.out.println("line" +index + ": "+stockCode);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
