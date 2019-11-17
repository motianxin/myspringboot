/**
 * FileName: ResponseModel
 * Author:   admin
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
 * @author admin
 * @version 3.2.2
 * @create 2019/1/24 16:23
 */
public class ResponseModel {

    private int code;

    private String msg;

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
