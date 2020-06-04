/**
 * FileName: NotifyAlarmSync
 * Author:   admin
 * Date:     2018/12/19 16:39
 * Description: 告警同步模拟
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.zhigang.myspringboot.endpoint;

import com.zhigang.myspringboot.domain.alarmws.NotifyAlarmSyncReq;
import com.zhigang.myspringboot.domain.alarmws.NotifyAlarmSyncRsp;
import com.zhigang.myspringboot.service.SyncService;
import com.zhigang.myspringboot.utils.common.Constans;
import com.zhigang.myspringboot.utils.strutils.StringTools;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 〈一句话功能简述〉<br>
 * 〈告警同步模拟〉
 *
 * @author admin
 * @version 3.2.2
 * @create 2018/12/19 16:39
 */
@Endpoint
@Slf4j
public class NotifyAlarmSync {

    @Resource
    private SyncService syncService;

    @PayloadRoot(namespace = "http://www.ctsi.com.cn/webservices/service", localPart = "notifyAlarmSyncReq")
    @ResponsePayload
    public NotifyAlarmSyncRsp alarmSync(@RequestPayload NotifyAlarmSyncReq req) {

        try {
            this.syncService.saveAlarmSync(req);
        } catch (Exception e) {
            NotifyAlarmSync.log.error("alarmSync error", e.getMessage(), e);
        }

        return new NotifyAlarmSyncRsp(4343, "retert", StringTools.dateFormat(Constans.DATE_PATTERN, new Date()));
    }
}
