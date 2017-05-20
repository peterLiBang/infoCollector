package org.lbq.utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.google.gson.Gson;

public class Xml2JsonUtils {

	
	public static  String getAttrsByElement(Element ele)
	{
		StringBuffer result = new StringBuffer();
		@SuppressWarnings("unchecked")
		List<Attribute>  list = (List<Attribute>)ele.attributes();
		for(int i = 0 ; i < list.size();i++)
		{
			Attribute attr = list.get(i);
			System.out.println(attr.getValue().toString().trim());
			result.append(attr.getName()+ "\t" + attr.getValue().toString().trim()+"\n");
		}
		return result.toString() ;
	}

	public static String getAttr(String xmlStr) {
		StringBuffer result = new StringBuffer();
		InputStream is;
		try {
			is = new ByteArrayInputStream(xmlStr.getBytes("utf-8"));
			SAXReader sr = new SAXReader();
			Document doc = sr.read(is);
			Element ele = doc.getRootElement();
			result.append(getAttrsByElement(ele));
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result.toString();

	}
	
	public static String xml2JSON(String xml)
	{

		Gson gson = new Gson();
		String result = null;
		try {
			InputStream is = new ByteArrayInputStream(xml.getBytes("utf-8"));
			SAXReader sr = new SAXReader();
			Document doc = sr.read(is);
			Element root = doc.getRootElement();
			if(root==null)
			{
				System.out.println("root is null , document read error!");
			}
			//test Map
			
			
			
			result = gson.toJson(iterateElement(root),Map.class);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
		return result;
		
	}
	
	public static String xml2JSON(File file)
	{

		Gson gson = new Gson();
		String result = null;
		try {
			SAXReader sr = new SAXReader();
			Document doc = sr.read(file);
			Element root = doc.getRootElement();
			result = gson.toJson(iterateElement(root),Map.class);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
		return result;
		
	}
	
	@SuppressWarnings({ "unchecked"})
	private static Map iterateElement(Element element)
	{
		List<Element> nodes = element.elements();
		System.out.println("nodes's size = " + nodes.size());
		Element ele = null ;
		Map<String, List> obj = new HashMap<String, List>();
		List list = null ;
		for(int i = 0 ; i < nodes.size();i++ )
		{
			
			list = new LinkedList();
			ele = nodes.get(i);
			
			if(ele.getTextTrim().equals(""))
			{
				if(!ele.hasContent())
				{
					continue ;
				}
				if(obj.containsKey(ele.getName()))
				{
					list = (List<Map<?, ?>>)obj.get(ele.getName());
				}
				list.add(iterateElement(ele));
				obj.put(ele.getName(), list);
			}
			else if(obj.containsKey(ele.getName()))
			{
				list = (List<String>)obj.get(ele.getName());
				System.out.println("node name is: " + ele.getName());
			}
			list.add(ele.getTextTrim());
			obj.put(ele.getName(),list );
		}
		return obj;
		
		
	}
	
	public static void main(String[] args) {
		@SuppressWarnings("unused")
		Gson gson = new Gson();
		String testXmlStr =  "<MapSet>"  
                + "<MapGroup id='Sheboygan'>" + "<Map>"  
                + "<Type>MapGuideddddddd</Type>"  
                + "<SingleTile>true</SingleTile>" + "<Extension>"  
                + "<ResourceId>ddd</ResourceId>" + "</Extension>" + "</Map>"  
                + "<Map>" + "<Type>ccc</Type>" + "<SingleTile>ggg</SingleTile>"  
                + "<Extension>" + "<ResourceId>aaa</ResourceId>"  
                + "</Extension>" + "</Map>" + "<Extension />" + "</MapGroup>"  
                + "<ddd>" + "33333333" + "</ddd>" + "<ddd>" + "444" + "</ddd>"  
                + "</MapSet>";
		@SuppressWarnings("unused")
		String testXmlStr2 = "<City>"
				+ "<name>zhuhai</name>"
				+ "<value>good</value>"
				+ "<name>wangbo</name>"
				+ "<value>well</value>"
				+ "</City>"; 
		
		String carXml = 
                "<row CreateTime=\"2016-11-09T02:04:06Z\" DATASOURCEID=\"2911786551\" FirstAreaCode=\"440402\" FirstDATASOURCEID=\"79122904600DDA9AF238F\" FirstMachineCode=\"79122904600DDA9AF238F \" WriterTime=\"2016-11-11T07:38:42Z\" "
                + "FirstUnitCode=\"44040210000001\" IMEI = \"355565474501281\" IMSI = \"454637556001925\" Lat=\"22.251280\" Lon=\"113.563330\" SystemType=\"145\" Time=\"2016-11-11T18:33:19Z\" UnitCode=\"44040210000001\" "
                + " AreaCode=\"440402 \" MachineCode=\"79122904600DDA9AF238F\" ManufacturerCode=\"791229046\" Mobile=\"13175078357\" /> ";
                
		@SuppressWarnings("unused")
		String result = xml2JSON(carXml); 
		String result2 = xml2JSON(testXmlStr); 
		//String attributes = getAttr(carXml);
		//����gsonת����ʱ���õ���Map<String,List>����ת����Ľ��Ĭ�����������ʽ �ͻᵼ��gson-->����ʵ����ʱ�����
		//���ʱ�����ʹ��������json���߱���fastJson
		/*result = result.replace("[","");
		result =  result.replace("]","");*/
		//System.out.println(result);
		System.out.println(result2);
	
		//Type listType = new TypeToken<LinkedList<City>>(){}.getType();
		/*City city = gson.fromJson(result, City.class);
		System.out.println("after gson read the city's name=" + city.getName());
		System.out.println("after gson read the city's value=" + city.getValue());*/
		
		
	}
}
