/**
 * FileName: SnmpReceiverServiceImpl
 * Author:   admin
 * Date:     2019/1/9 14:50
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.zhigang.myspringboot.service;

import com.zhigang.myspringboot.domain.SnmpModel;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author admin
 * @version 3.2.2
 * @create 2019/1/9 14:50
 */
@Service
public class SnmpReceiverServiceImpl implements SnmpReceiverService {


    private static boolean start = false;

    private SnmpTrapMultiThreadReceiver receiver;

    @Resource
    private SnmpModel snmpModel;

    @Override
    public String snmpReceiverstatus(String ip, String port) throws Exception {
        String result = "snmp listener is already started!";
        String snmpip = ip == null ? this.snmpModel.getIp() : ip;
        String snmpport = port == null ? this.snmpModel.getPort() : port;
        if (!SnmpReceiverServiceImpl.start) {
            this.receiver = new SnmpTrapMultiThreadReceiver(snmpip, snmpport);
            this.receiver.run();
            SnmpReceiverServiceImpl.start = true;
            result = "snmp listener start succeed!";
        }
        return result;
    }

    @Override
    public String closeReceiver() throws Exception {
        String result = "snmp listener is already stoped";
        if (SnmpReceiverServiceImpl.start || this.receiver != null) {
            this.receiver.closeSnmp();
            SnmpReceiverServiceImpl.start = false;
            this.receiver = null;
            result = "snmp listener stop succeed";
        }
        return result;
    }
}
