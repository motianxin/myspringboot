/**
 * FileName: ResponseModel
 * Author:   zghuang
 * Date:     2019/1/24 16:23
 * Description: 响应对象
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.zhigang.myspringboot.domain;

/**
 * 〈一句话功能简述〉<br> 
 * 〈响应对象〉
 *
 * @author zghuang
 * @create 2019/1/24 16:23
 * @version 3.2.2
 */
public class ResponseModel {

	private int code;

	private String msg;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
