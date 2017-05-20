package org.lbq.utils;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
//@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="ROOT")
//@XmlType(propOrder={"code","name","age"}) //����xml��ʽ��ݵ���ʾ˳������Ҫ�ͱ�������һ��
public class MyBean {

	//@XmlElement(name="code",required=true)
	private String code ;
	//@XmlElement(name="Name",required=true)
	private String name ;
	//@XmlElement(name="Age",required=true)
	private String age ;
	
	
	@XmlAttribute(name="code",required=true)
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	@XmlAttribute(name="name",required=true)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@XmlAttribute(name="age",required=true)
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	
	public String toString()
	{
		return this.getCode() + "," + this.getName() + "," + this.getAge();
	}
	
}
