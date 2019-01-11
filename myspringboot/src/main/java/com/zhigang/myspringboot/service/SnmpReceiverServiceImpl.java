/**
 * FileName: SnmpReceiverServiceImpl
 * Author:   zghuang
 * Date:     2019/1/9 14:50
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.zhigang.myspringboot.service;

import com.zhigang.myspringboot.domain.SnmpModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author zghuang
 * @version 3.2.2
 * @create 2019/1/9 14:50
 */
@Service
public class SnmpReceiverServiceImpl implements SnmpReceiverService {


	private static boolean start = false;

	private SnmpTrapMultiThreadReceiver receiver;

	@Autowired
	private SnmpModel snmpModel;

	@Override
	public String snmpReceiverstatus() throws Exception {
		String result = "snmp listener is already started!";
		if (!start) {
			receiver = new SnmpTrapMultiThreadReceiver(snmpModel.getIp(), snmpModel.getPort());
			receiver.run();
			start = true;
			result = "snmp listener start succeed!";
		}
		return result;
	}

	@Override
	public String closeReceiver() throws Exception{
		String result = "snmp listener is already stoped";
		if (start || receiver != null) {
			receiver.closeSnmp();
			start = false;
			receiver = null;
			result = "snmp listener stop succeed";
		}
		return result;
	}
}
