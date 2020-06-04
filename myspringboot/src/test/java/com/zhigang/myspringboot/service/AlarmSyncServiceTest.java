package com.zhigang.myspringboot.service;

import com.zhigang.myspringboot.MyspringbootApplicationTests;
import com.zhigang.myspringboot.domain.AlarmSync;
import com.zhigang.myspringboot.domain.alarmws.NotifyAlarmSyncReq;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class AlarmSyncServiceTest extends MyspringbootApplicationTests {

    @Resource
    private SyncService syncService;

    @Test
    public void saveAlarmSync() {
        NotifyAlarmSyncReq req = new NotifyAlarmSyncReq(47, 88, "hb", "yueme");
        log.info("NotifyAlarmSyncReq is {}", req);
        try {
            syncService.saveAlarmSync(req);
        } catch (Exception e) {
            log.info("exception:", e);
        }
    }

    @Test
    public void getOneById() {
        AlarmSync alarmSync = syncService.getOneById(syncService.getAllAlarmSync().get(0).getId());
        log.info("alarmsync is {}", alarmSync);
    }

    @Test
    public void deleteAlarmSync() {
        syncService.deleteAlarmSync(syncService.getAllAlarmSync().get(0).getId());
        syncService.getAllAlarmSync().forEach(System.out::println);
    }

    @Test
    public void getAllAlarmSync() {
        List<AlarmSync> alarmSyncList = syncService.getAllAlarmSync();
        System.out.println(Arrays.toString(alarmSyncList.toArray(new AlarmSync[]{})));
    }
}