/**
 * FileName: SyncServiceTest
 * Author:   zghuang
 * Date:     2019/3/15 15:58
 * Description: SyncServiceTest
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.zhigang.myspringboot.service;

import com.zhigang.myspringboot.MyspringbootApplicationTests;
import com.zhigang.myspringboot.domain.AlarmSync;
import com.zhigang.myspringboot.domain.alarmws.NotifyAlarmSyncReq;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 〈SyncServiceTest〉
 *
 * @author zghuang
 * @version 3.2.2
 * @create 2019/3/15 15:58
 */
public class SyncServiceTest extends MyspringbootApplicationTests {


    @Autowired
    private SyncService syncService;

    @Test
    public void testSave() {
        NotifyAlarmSyncReq req = new NotifyAlarmSyncReq(45, 656, "adfgdfg", "sdfgsdfgs");

        try {
            syncService.saveAlarmSync(req);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetAllAlarmSync() {
        List<AlarmSync> alarmSyncList = syncService.getAllAlarmSync();
        alarmSyncList.forEach(System.out :: println);
    }


}
