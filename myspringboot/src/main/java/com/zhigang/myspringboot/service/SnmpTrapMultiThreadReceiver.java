/**
 * FileName: SnmpTrapMultiThreadReceiver
 * Author:   admin
 * Date:     2018/12/11 9:25
 * Description: SnmpTrapMultiThreadReceiver
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.zhigang.myspringboot.service;

import com.zhigang.myspringboot.utils.strutils.StringTools;
import lombok.extern.slf4j.Slf4j;
import org.snmp4j.CommandResponder;
import org.snmp4j.CommandResponderEvent;
import org.snmp4j.MessageDispatcherImpl;
import org.snmp4j.Snmp;
import org.snmp4j.TransportMapping;
import org.snmp4j.mp.MPv1;
import org.snmp4j.mp.MPv2c;
import org.snmp4j.mp.MPv3;
import org.snmp4j.security.SecurityModels;
import org.snmp4j.security.SecurityProtocols;
import org.snmp4j.security.USM;
import org.snmp4j.smi.Address;
import org.snmp4j.smi.GenericAddress;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.TcpAddress;
import org.snmp4j.smi.UdpAddress;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultTcpTransportMapping;
import org.snmp4j.transport.DefaultUdpTransportMapping;
import org.snmp4j.util.MultiThreadedMessageDispatcher;
import org.snmp4j.util.ThreadPool;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Vector;

/**
 * 〈一句话功能简述〉<br>
 * 〈SnmpTrapMultiThreadReceiver〉
 *
 * @author admin
 * @version 3.2.2
 * @create 2018/12/11 9:25
 */
@Slf4j
public class SnmpTrapMultiThreadReceiver implements CommandResponder {


    private String ip;

    private String port;

    private MultiThreadedMessageDispatcher dispatcher;
    private Snmp snmp = null;
    private Address listenAddress;
    private ThreadPool threadPool;

    public SnmpTrapMultiThreadReceiver(String ip, String port) {
        this.ip = ip;
        this.port = port;
    }

    private void init() throws UnknownHostException, IOException {
        SnmpTrapMultiThreadReceiver.log.info("begin init snmp listener.");
        String address = "udp:" + this.ip + "/" + this.port;
        SnmpTrapMultiThreadReceiver.log.info("address is [{}]", address);
        this.threadPool = ThreadPool.create("TrapPool", 2);
        this.dispatcher = new MultiThreadedMessageDispatcher(this.threadPool, new MessageDispatcherImpl());
        this.listenAddress = GenericAddress.parse(address);
        TransportMapping transport;
        if (this.listenAddress instanceof UdpAddress) {
            transport = new DefaultUdpTransportMapping((UdpAddress) this.listenAddress);
        } else {
            transport = new DefaultTcpTransportMapping((TcpAddress) this.listenAddress);
        }
        this.snmp = new Snmp(this.dispatcher, transport);
        this.snmp.getMessageDispatcher().addMessageProcessingModel(new MPv1());
        this.snmp.getMessageDispatcher().addMessageProcessingModel(new MPv2c());
        this.snmp.getMessageDispatcher().addMessageProcessingModel(new MPv3());
        USM usm = new USM(SecurityProtocols.getInstance(), new OctetString(MPv3.createLocalEngineID()), 0);
        SecurityModels.getInstance().addSecurityModel(usm);
        this.snmp.listen();
    }

    public void run() throws Exception {
        SnmpTrapMultiThreadReceiver.log.info("----&gt; Trap Receiver run ... &lt;----");
        init();
        this.snmp.addCommandResponder(this);
        SnmpTrapMultiThreadReceiver.log.info("----&gt; 开始监听端口 [{}]，等待Trap message  &lt;----", this.port);
    }

    @Override
    public void processPdu(CommandResponderEvent event) {
        SnmpTrapMultiThreadReceiver.log.info("----&gt; 开始解析ResponderEvent: &lt;----");
        if (event == null || event.getPDU() == null) {
            SnmpTrapMultiThreadReceiver.log.info("[Warn] ResponderEvent or PDU is null");
            return;
        }
        Vector<VariableBinding> vbVect = event.getPDU().getVariableBindings();

        String value = "null";
        for (VariableBinding vb : vbVect) {
            value = StringTools.hexToChinese(vb.getVariable().toString());
            SnmpTrapMultiThreadReceiver.log.info(vb.getOid() + " = " + value);
        }

        SnmpTrapMultiThreadReceiver.log.info("----&gt;  本次ResponderEvent 解析结束 &lt;----");
    }

    public void closeSnmp() throws Exception {
        SnmpTrapMultiThreadReceiver.log.info("close snmp listener.");
        if (this.snmp != null) {
            this.snmp.close();
        }
    }


}
