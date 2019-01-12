/**
 * FileName: HelloController
 * Author:   zghuang
 * Date:     2018/12/19 13:42
 * Description: hello
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.zhigang.myspringboot.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 〈一句话功能简述〉<br>
 * 〈hello〉
 *
 * @author zghuang
 * @version 3.2.2
 * @create 2018/12/19 13:42
 */
@RestController
public class HelloController {

	private static final Logger LOGGER = LoggerFactory.getLogger(HelloController.class);

	@GetMapping("/hello")
	public String saySomething() {

		LOGGER.info("saySomething get in.");

		return "hello boy!";
	}


}
