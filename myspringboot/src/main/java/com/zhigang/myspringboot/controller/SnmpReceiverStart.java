/**
 * FileName: SnmpReceiverStart
 * Author:   zghuang
 * Date:     2019/1/9 14:46
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.zhigang.myspringboot.controller;

import com.zhigang.myspringboot.service.SnmpReceiverService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author zghuang
 * @version 3.2.2
 * @create 2019/1/9 14:46
 */
@RestController
public class SnmpReceiverStart {

	private static final Logger LOGGER = LoggerFactory.getLogger(SnmpReceiverStart.class);

	@Autowired
	private SnmpReceiverService snmpReceiverService;

	@GetMapping("/startReceiverSnmpTrap")
	public String startReceiverSnmpTrap() {

		String result;
		try {
			result = snmpReceiverService.snmpReceiverstatus();
		} catch (Exception e) {
			LOGGER.error("startReceiverSnmpTrap exception.", e);
			result = "startReceiverSnmpTrap exception :" + e.getMessage();
		}

		return result;

	}


	@GetMapping("/closeReceiverSnmpTrap")
	public String closeReceiverSnmpTrap() {

		String result;
		try {
			result = snmpReceiverService.closeReceiver();
		} catch (Exception e) {
			LOGGER.error("startReceiverSnmpTrap exception.", e);
			result = "closeReceiverSnmpTrap exception:" + e.getMessage();
		}

		return result;

	}

}
