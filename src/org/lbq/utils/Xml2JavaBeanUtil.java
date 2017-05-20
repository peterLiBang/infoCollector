package org.lbq.utils;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.Test;
import org.lbq.collector.StaticValue;


public class Xml2JavaBeanUtil
{

	// 将sourceObj对象集合转为xml
	public static String converBeanList2Xml(List<Object> objList)
	{
		String result = null;
		try
		{
			if (null == objList || objList.size() <= 0)
			{
				return null;
			}
			JAXBContext context = JAXBContext.newInstance(objList.get(0).getClass());
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
			marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			XMLOutputFactory xmlOutputFactory = XMLOutputFactory.newInstance();
			XMLStreamWriter xmlStreamWriter = xmlOutputFactory.createXMLStreamWriter(baos,
			        (String) marshaller.getProperty(Marshaller.JAXB_ENCODING));
			xmlStreamWriter.writeStartDocument((String) marshaller.getProperty(Marshaller.JAXB_ENCODING), "1.0");
			baos.write("<Objects>".getBytes()); //添加根节点
			for (Object obj : objList)
			{
				marshaller.marshal(obj, xmlStreamWriter);
			}
			baos.write("</Objects>".getBytes());
			xmlStreamWriter.writeEndDocument();
			if (xmlStreamWriter != null)
			{
				xmlStreamWriter.close();
			}
			result = baos.toString("UTF-8");
			//释放baos
			if(baos!=null)
			{
				baos.close();
			}
		} catch (JAXBException e)
		{
			e.printStackTrace();
			return null;
		} catch (XMLStreamException e)
		{
			e.printStackTrace();
			return null;
		} catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
			return null;
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		return result;
	}

	public static void putSourceRecord2XmlFile(List<Object> sourceObjList, String xmlDataFilePath,
	        int maxStockRecordNums, int sourceType) throws IOException
	{
		List<Object> tmpList = new ArrayList<Object>();
		int curRecordNums = 0;
		if (sourceObjList.size() > maxStockRecordNums)
		{
			while (sourceObjList.size() >= maxStockRecordNums)
			{
				for (int i = 0; i < maxStockRecordNums; i++)
				{
					tmpList.add(sourceObjList.get(i));

				}
				// 这里有个陷阱 移除的时候最好从后往前移除
				for (int j = maxStockRecordNums - 1; j >= 0; j--)
				{
					sourceObjList.remove(j);
				}

				curRecordNums += maxStockRecordNums;
				putSourceDataList2XmlFile(tmpList, xmlDataFilePath, curRecordNums, sourceType);
				// clear that list
				tmpList.clear();

			}
			if (sourceObjList.size() > 0)
			{
				putSourceDataList2XmlFile(sourceObjList, xmlDataFilePath, -1, sourceType);
			}
		} else
		{
			putSourceDataList2XmlFile(sourceObjList, xmlDataFilePath, -1, sourceType);
		}

	}

	private static void putSourceDataList2XmlFile(List<Object> sourceList, String xmlDataFilePath, int recordNums,
	        int type)
	{

		String baseFileName = "";
		// "fund.xml" ;
		long millis = DateUtil.getTotalSec(new Date()); // 获取当前毫妙数
		String fileName = "";
		String resultXmlStr;
		if (recordNums < 0)
		{
			recordNums = sourceList.size();
		}
		if (type == StaticValue.FundType)
		{

			baseFileName = "fund.xml";
		}
		if (type == StaticValue.StockType)
		{
			baseFileName = "stock.xml";
		}
		fileName = recordNums + "_" + millis + "_" + baseFileName;
		if (null != fileName && !fileName.isEmpty() && null != xmlDataFilePath && !xmlDataFilePath.isEmpty())
		{

			resultXmlStr = Xml2JavaBeanUtil.converBeanList2Xml(sourceList);
			XmlStringUtils.putString2XmlFile(resultXmlStr, fileName, xmlDataFilePath);
		}
	}

	public static String convertBean2Xml(Object obj) throws IOException
	{
		String result = null;
		try
		{
			JAXBContext context = JAXBContext.newInstance(obj.getClass());
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
			marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			XMLOutputFactory xmlOutputFactory = XMLOutputFactory.newInstance();
			XMLStreamWriter xmlStreamWriter = xmlOutputFactory.createXMLStreamWriter(baos,
			        (String) marshaller.getProperty(Marshaller.JAXB_ENCODING));
			xmlStreamWriter.writeStartDocument((String) marshaller.getProperty(Marshaller.JAXB_ENCODING), "1.0");
			marshaller.marshal(obj, xmlStreamWriter);
			xmlStreamWriter.writeEndDocument();
			xmlStreamWriter.close();
			result = baos.toString("UTF-8");

		} catch (JAXBException e)
		{
			e.printStackTrace();
			return null;
		} catch (XMLStreamException e)
		{
			e.printStackTrace();
			return null;
		} catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
			return null;
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public static <T> T convert2JavaBean(String xml, Class<T> c)
	{
		T t = null;
		try
		{
			JAXBContext context = JAXBContext.newInstance(c);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			t = (T) unmarshaller.unmarshal(new StringReader(xml));
		} catch (JAXBException e)
		{
			e.printStackTrace();
			return t;
		}
		return t;
	}

	public static Object convertXmlFileToObject(Class clazz, String xmlPath)
	{
		Object xmlObject = null;
		try
		{
			JAXBContext context = JAXBContext.newInstance(clazz);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			FileReader fr = null;
			try
			{
				fr = new FileReader(xmlPath);
			} catch (FileNotFoundException e)
			{
				e.printStackTrace();
			}
			xmlObject = unmarshaller.unmarshal(fr);
		} catch (JAXBException e)
		{
			e.printStackTrace();
		}
		return xmlObject;
	}

	@Test
	public void testParseXmlUsingDom4j()
	{
		String xmlDataFilePath = "";
		try
		{
			xmlDataFilePath = PropertiesUtil.getPropertyByKey("xmlDataFilePath", StaticValue.configPath);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		String xmlPath = xmlDataFilePath + "3_1494736539_stock.xml";
		System.out.println(xmlPath);
		Xml2JavaBeanUtil.parseXmlUsingDom4j(xmlPath);
	}

	public static void parseXmlUsingDom4j(String xmlPath)
	{
		try
		{
			SAXReader reader = new SAXReader();
			InputStream in = new FileInputStream(xmlPath);
			System.out.println(in.toString());
			Document doc = reader.read(in);
			Element root = doc.getRootElement();
			readNode(root, "");
		} catch (DocumentException e)
		{
			e.printStackTrace();
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}

	}

	private static void readNode(Element root, String prefix)
	{
		if (null == root)
		{
			System.out.println("root node is null ");
			return;
		}
		@SuppressWarnings("unchecked")
		List<Attribute> attrList = root.attributes(); 
		if (attrList != null && attrList.size() > 0)
		{
			for (Attribute attr : attrList)
			{
				System.out.println( "attr's name = " + attr.getName() +" value = " + attr.getValue() + " ");
			}
		}
		// 获取其子节点
		@SuppressWarnings("unchecked")
		List<Element> childNodes = root.elements();
		//System.out.println("childNodes's size  = " + childNodes.size());
		for (Element e : childNodes)
		{
			readNode(e, prefix);
		}
	}

}
