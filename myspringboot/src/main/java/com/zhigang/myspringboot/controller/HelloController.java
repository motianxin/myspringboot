/**
 * FileName: HelloController
 * Author:   zghuang
 * Date:     2018/12/19 13:42
 * Description: hello
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.zhigang.myspringboot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 〈一句话功能简述〉<br> 
 * 〈hello〉
 *
 * @author zghuang
 * @create 2018/12/19 13:42
 * @version 3.2.2
 */
@RestController
public class HelloController {

	@GetMapping("/hello")
	public String saySomething (){



		return "hello boy!";
	}


}
