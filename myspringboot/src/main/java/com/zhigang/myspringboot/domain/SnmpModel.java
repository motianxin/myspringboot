/**
 * FileName: SnmpModel
 * Author:   admin
 * Date:     2019/1/10 20:01
 * Description: SNMPmodel
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.zhigang.myspringboot.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 〈一句话功能简述〉<br> 
 * 〈SNMPmodel〉
 *
 * @author admin
 * @create 2019/1/10 20:01
 * @version 3.2.2
 */
@Component
@Getter
@Setter
public class SnmpModel {

	@Value("${snmp.ip}")
	private String ip;

	@Value("${snmp.port}")
	private String port;


}
