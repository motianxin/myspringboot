/**
 * FileName: SnmpTrapMultiThreadReceiver
 * Author:   zghuang
 * Date:     2018/12/11 9:25
 * Description: SnmpTrapMultiThreadReceiver
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.zhigang.myspringboot.service;

import com.zhigang.myspringboot.utils.strutils.StringTools;
import lombok.extern.slf4j.Slf4j;
import org.snmp4j.*;
import org.snmp4j.mp.MPv1;
import org.snmp4j.mp.MPv2c;
import org.snmp4j.mp.MPv3;
import org.snmp4j.security.SecurityModels;
import org.snmp4j.security.SecurityProtocols;
import org.snmp4j.security.USM;
import org.snmp4j.smi.*;
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
 * @author zghuang
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
		log.info("begin init snmp listener.");
		String address = "udp:" + ip + "/" + port;
		log.info("address is [{}]", address);
		threadPool = ThreadPool.create("TrapPool", 2);
		dispatcher = new MultiThreadedMessageDispatcher(threadPool,
				new MessageDispatcherImpl());
		listenAddress = GenericAddress.parse(address);
		TransportMapping transport;
		if (listenAddress instanceof UdpAddress) {
			transport = new DefaultUdpTransportMapping(
					(UdpAddress) listenAddress);
		} else {
			transport = new DefaultTcpTransportMapping(
					(TcpAddress) listenAddress);
		}
		snmp = new Snmp(dispatcher, transport);
		snmp.getMessageDispatcher().addMessageProcessingModel(new MPv1());
		snmp.getMessageDispatcher().addMessageProcessingModel(new MPv2c());
		snmp.getMessageDispatcher().addMessageProcessingModel(new MPv3());
		USM usm = new USM(SecurityProtocols.getInstance(), new OctetString(
				MPv3.createLocalEngineID()), 0);
		SecurityModels.getInstance().addSecurityModel(usm);
		snmp.listen();
	}

	public void run() throws Exception {
		log.info("----&gt; Trap Receiver run ... &lt;----");
		init();
		snmp.addCommandResponder(this);
		log.info("----&gt; 开始监听端口 [{}]，等待Trap message  &lt;----", port);
	}

	@Override
	public void processPdu(CommandResponderEvent event) {
		log.info("----&gt; 开始解析ResponderEvent: &lt;----");
		if (event == null || event.getPDU() == null) {
			log.info("[Warn] ResponderEvent or PDU is null");
			return;
		}
		Vector<VariableBinding> vbVect = event.getPDU().getVariableBindings();

		String value = "null";
		for (VariableBinding vb : vbVect) {
			value = StringTools.hexToChinese(vb.getVariable().toString());
			log.info(vb.getOid() + " = " + value);
		}

		log.info("----&gt;  本次ResponderEvent 解析结束 &lt;----");
	}

	public void closeSnmp() throws Exception {
		log.info("close snmp listener.");
		if (snmp != null) {
			snmp.close();
		}
	}



}
