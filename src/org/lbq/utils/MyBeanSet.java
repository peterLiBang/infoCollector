package org.lbq.utils;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="ROOT")
@XmlType(propOrder={"name","age","books"})
public class MyBeanSet {

	@XmlElement(name="Name",required=true)
	private String name ;
	@XmlElement(name="Age",required=true)
	private String age ;
	//@XmlElementWrapper注解表示生成一个包装xml表示形式的包装器元素
	//用于生成一个包装集合的包装器xml元素
	@XmlElementWrapper(name="ROWS")
	@XmlElement(name="LIST",required=true)
	private List<Book> books ;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public List<Book> getBooks() {
		return books;
	}
	public void setBooks(List<Book> books) {
		this.books = books;
	}
	
	public String toString()
	{
		String result = "" ;
		result = this.age+","+this.name+","+"集合中的数据:\n";
		for(Book book:books)
		{
			result += book.getAuthor()+","+book.getTime()+","+book.getBookName()+"\n";
		}
		return result ;
	}
	
}
