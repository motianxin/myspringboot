/**
 * FileName: Node
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
public class Node {


	private List<Node> subNodes;

	private String key;

	private String value;

	public Node() {
	}

	public Node(String key) {
		this.key = key;
	}

	public Node(String key, String value) {
		this.key = key;
		this.value = value;
	}

	public Node(List<Node> subNodes, String key, String value) {
		this.subNodes = subNodes;
		this.key = key;
		this.value = value;
	}

	public Node(List<Node> subNodes, String key) {
		this.subNodes = subNodes;
		this.key = key;
	}

	public List<Node> getSubNodes() {
		return subNodes;
	}

	public void setSubNodes(List<Node> subNodes) {
		this.subNodes = subNodes;
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
