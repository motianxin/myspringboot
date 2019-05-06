/**
 * FileName: GlobalDefaultExceptionHandler
 * Author:   zghuang
 * Date:     2018/12/27 21:40
 * Description: 全局异常捕获
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.zhigang.myspringboot.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br> 
 * 〈全局异常捕获〉
 *
 * @author zghuang
 * @create 2018/12/27 21:40
 * @version 3.2.2
 */
@ControllerAdvice(annotations = RestController.class)
public class GlobalDefaultExceptionHandler {
	/**
	 *
	 * @param e
	 * @return
	 */
	@ExceptionHandler(Exception.class)
	@ResponseBody
	public Map<String,Object> defaultExceptionHandler(Exception e) {

		Map<String,Object> map = new HashMap<String,Object>();
		map.put("code", 500);
		map.put("msg", e.getMessage());
		return map;
	}

}
