package org.lbq.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.lbq.collector.StaticValue;

public class TestBean {

	@Test
	public void testBean2xml() {
		@SuppressWarnings("unused")
		String outPutFileName = "bean.xml";
		MyBean myBean = new MyBean();
		myBean.setName("hello");
		myBean.setCode("120022");
		myBean.setAge("23");
		String xml = null;
		try {
			xml = Xml2JavaBeanUtil.convertBean2Xml(myBean);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(xml);
		// XmlStringUtils.putString2XmlFile(xml,outPutFileName);

	}
	
	@Test
	public void testBeanList2Xml() throws IOException
	{
		@SuppressWarnings("unused")
		String outPutFileName = "bean.xml";
		List<Object> beanList = new ArrayList<Object>();
		MyBean myBean1 = new MyBean();
		MyBean myBean2 = new MyBean();
		MyBean myBean3 = new MyBean();
		myBean1.setName("hello");
		myBean1.setCode("120022");
		myBean1.setAge("23");
		
		myBean2.setName("hello");
		myBean2.setCode("120022");
		myBean2.setAge("23");
		
		myBean3.setName("hello");
		myBean3.setCode("120022");
		myBean3.setAge("23");
		
		beanList.add(myBean1);
		beanList.add(myBean2);
		beanList.add(myBean3);
		
		String xml = null;
		xml = Xml2JavaBeanUtil.converBeanList2Xml(beanList);
		System.out.println(xml);
		
	}
	
	@Test
	public void testBeanList2XmlFile() throws IOException
	{
		@SuppressWarnings("unused")
		String outPutFileName = "bean.xml";
		String xmlDataFilePath = PropertiesUtil.getPropertyByKey("xmlDataFilePath", StaticValue.configPath);
		System.out.println(xmlDataFilePath);
		List<Object> beanList = new ArrayList<Object>();
		MyBean myBean1 = new MyBean();
		MyBean myBean2 = new MyBean();
		MyBean myBean3 = new MyBean();
		myBean1.setName("hello");
		myBean1.setCode("120022");
		myBean1.setAge("23");
		
		myBean2.setName("hello");
		myBean2.setCode("120222");
		myBean2.setAge("23");
		
		myBean3.setName("hello");
		myBean3.setCode("120322");
		myBean3.setAge("23");
		
		beanList.add(myBean1);
		beanList.add(myBean2);
		beanList.add(myBean3);
		String xml = null;
		xml = Xml2JavaBeanUtil.converBeanList2Xml(beanList);
		Xml2JavaBeanUtil.putSourceRecord2XmlFile(beanList, xmlDataFilePath, 100,StaticValue.StockType);
		System.out.println(xml);
		
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testConvertXmlFileToObject() throws FileNotFoundException, IOException
	{
		List<Object> beanList = new ArrayList<Object>();
		MyBean myBean1 = new MyBean();
		MyBean[] beans = new MyBean[]{};
		String xmlDataFilePath = PropertiesUtil.getPropertyByKey("xmlDataFilePath", StaticValue.configPath);
		String xmlPath = xmlDataFilePath + "3_1494687006_stock.xml" ;
		beanList =  (List<Object>) Xml2JavaBeanUtil.convertXmlFileToObject(beanList.getClass(), xmlPath);
		System.out.println("beanList'size = " + ((MyBean) beanList.get(0)).getAge());
	}
	
	@Test
	public void testElementB2XmlString() {

		ElementClassB eleClass = new ElementClassB();
		eleClass.setAttrPassword("password");
		eleClass.setAttrUserName("pawn");
		eleClass.setEleCode("00001");
		String xml = null;
		try {
			xml = Xml2JavaBeanUtil.convertBean2Xml(eleClass);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(xml);

	}
	
	@Test
	public void testElementClassXmlString2Bean() 
	{
		String xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Row password=\"password\" userName=\"pawn\" eleCode=\"00001\"></Row>" ;
		ElementClassB myBean = Xml2JavaBeanUtil.convert2JavaBean(xmlString,
				ElementClassB.class);
		System.out.println(myBean.getAttrPassword());
	}

	@Test
	public void testXml2Bean() {

		String xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><ROOT><Code>120022</Code><Name>hello</Name><Age>23</Age></ROOT>";
		MyBean myBean = Xml2JavaBeanUtil.convert2JavaBean(xmlString,
				MyBean.class);
		System.out.println(myBean.toString());
	}

	@Test
	public void testBeans2Xml() {

		MyBeanSet beanSet = new MyBeanSet();
		beanSet.setName("hello");
		beanSet.setAge("23");
		List<Book> books = new ArrayList<Book>();
		Book book1 = new Book();
		book1.setAuthor("world");
		book1.setTime("2016-10-12");
		book1.setBookName("JAVA exprient!");

		Book book2 = new Book();
		book2.setAuthor("jack");
		book2.setTime("2016-11-12");
		book2.setBookName("JAVA se api");

		books.add(book1);
		books.add(book2);
		beanSet.setBooks(books);
		String xml = null;
		try {
			xml = Xml2JavaBeanUtil.convertBean2Xml(beanSet);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(xml);
	}

	@Test
	public void testXml2Beans() {
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "<ROOT>"
				+ "<Name>hello</Name>" + "<Age>23</Age>" + "<ROWS>" + "<LIST>"
				+ "<BookName>JAVA exprient!</BookName>"
				+ "<Time>2016-10-12</Time>" + "<Author>world</Author>"
				+ "</LIST>" + "<LIST>" + "<BookName>JAVA se api</BookName>"
				+ "<Time>2016-11-12</Time>" + "<Author>jack</Author>"
				+ "</LIST>" + "</ROWS>" + "</ROOT>";
		MyBeanSet beanSet = Xml2JavaBeanUtil.convert2JavaBean(xml,
				MyBeanSet.class);
		System.out.println(beanSet);
	}
}
