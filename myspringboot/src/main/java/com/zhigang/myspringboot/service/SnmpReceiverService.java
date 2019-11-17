package com.zhigang.myspringboot.service;

import org.springframework.stereotype.Service;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Administrator
 * @create 2019/1/9 14:50
 * @since 3.2.2
 */
@Service
public interface SnmpReceiverService {

    /**
     * 开启snmp监听
     *
     * @param ip
     * @param port
     *
     * @return
     *
     * @throws Exception
     */
    String snmpReceiverstatus(String ip, String port) throws Exception;

    String closeReceiver() throws Exception;
}
