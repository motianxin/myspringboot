/**
 * FileName: XmlNode
 * Author:   zghuang
 * Date:     2019/3/14 19:21
 * Description: xml数据类型封装对象
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.zhigang.myspringboot.mapandxml;

import java.util.List;

/**
 * 〈xml数据类型封装对象〉
 *
 * @author zghuang
 * @create 2019/3/14 19:21
 * @version 3.2.2
 */
public class XmlNode {


	private List<XmlNode> subXmlNodes;

	private String key;

	private String value;

	public XmlNode() {
	}

	public XmlNode(String key) {
		this.key = key;
	}

	public XmlNode(String key, String value) {
		this.key = key;
		this.value = value;
	}

	public XmlNode(List<XmlNode> subXmlNodes, String key, String value) {
		this.subXmlNodes = subXmlNodes;
		this.key = key;
		this.value = value;
	}

	public XmlNode(List<XmlNode> subXmlNodes, String key) {
		this.subXmlNodes = subXmlNodes;
		this.key = key;
	}

	public List<XmlNode> getSubXmlNodes() {
		return subXmlNodes;
	}

	public void setSubXmlNodes(List<XmlNode> subXmlNodes) {
		this.subXmlNodes = subXmlNodes;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
