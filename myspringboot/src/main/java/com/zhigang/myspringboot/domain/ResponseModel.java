/**
 * FileName: ResponseModel
 * Author:   admin
 * Date:     2019/1/24 16:23
 * Description: 响应对象
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.zhigang.myspringboot.domain;

import lombok.Data;

/**
 * 〈一句话功能简述〉<br>
 * 〈响应对象〉
 *
 * @author admin
 * @version 3.2.2
 * @create 2019/1/24 16:23
 */
@Data
public class ResponseModel {
    private int code;

    private String msg;
}
