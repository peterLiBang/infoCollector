package org.lbq.utils;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

//@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Row")
public class ElementClassB {

	private String attrUserName;
	private String attrPassword;
	private String eleCode;
	
	@XmlAttribute(name = "userName")
	public String getAttrUserName() {
		return attrUserName;
	}
	public void setAttrUserName(String attrUserName) {
		this.attrUserName = attrUserName;
	}
	@XmlAttribute(name = "password")
	public String getAttrPassword() {
		return attrPassword;
	}
	public void setAttrPassword(String attrPassword) {
		this.attrPassword = attrPassword;
	}
	
	@XmlAttribute(name = "eleCode")
	public String getEleCode() {
		return eleCode;
	}
	public void setEleCode(String eleCode) {
		this.eleCode = eleCode;
	}
	


}
