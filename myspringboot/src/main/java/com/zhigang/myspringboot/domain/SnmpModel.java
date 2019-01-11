/**
 * FileName: SnmpModel
 * Author:   zghuang
 * Date:     2019/1/10 20:01
 * Description: SNMPmodel
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.zhigang.myspringboot.domain;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 〈一句话功能简述〉<br> 
 * 〈SNMPmodel〉
 *
 * @author zghuang
 * @create 2019/1/10 20:01
 * @version 3.2.2
 */
@Component
public class SnmpModel {

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	@Value("${snmp.ip}")
	private String ip;

	@Value("${snmp.port}")
	private String port;


}
