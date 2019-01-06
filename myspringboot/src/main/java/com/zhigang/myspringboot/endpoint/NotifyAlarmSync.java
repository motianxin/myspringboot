/**
 * FileName: NotifyAlarmSync
 * Author:   zghuang
 * Date:     2018/12/19 16:39
 * Description: 告警同步模拟
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.zhigang.myspringboot.endpoint;

import com.zhigang.myspringboot.domain.alarmws.NotifyAlarmSyncReq;
import com.zhigang.myspringboot.domain.alarmws.NotifyAlarmSyncRsp;
import com.zhigang.myspringboot.service.SyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 〈一句话功能简述〉<br> 
 * 〈告警同步模拟〉
 *
 * @author zghuang
 * @create 2018/12/19 16:39
 * @version 3.2.2
 */
@Endpoint
public class NotifyAlarmSync {

	@Autowired
	private SyncService syncService;

	@PayloadRoot(namespace = "http://www.ctsi.com.cn/webservices/service", localPart = "notifyAlarmSyncReq")
	@ResponsePayload
	public NotifyAlarmSyncRsp alarmSync(@RequestPayload NotifyAlarmSyncReq req){

		try {
			syncService.saveAlarmSync(req);
		} catch (Exception e) {
			e.printStackTrace();
		}


		return new NotifyAlarmSyncRsp(4343,"retert",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()));
	}



}
