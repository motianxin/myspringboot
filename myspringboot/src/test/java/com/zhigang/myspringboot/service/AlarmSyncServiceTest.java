package com.zhigang.myspringboot.service;

import com.zhigang.myspringboot.MyspringbootApplicationTests;
import com.zhigang.myspringboot.domain.AlarmSync;
import com.zhigang.myspringboot.domain.alarmws.NotifyAlarmSyncReq;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class AlarmSyncServiceTest extends MyspringbootApplicationTests {

	@Autowired
	private SyncService syncService;

	@Test
	public void saveAlarmSync() {
		NotifyAlarmSyncReq req = new NotifyAlarmSyncReq(47, 88, "hb", "yueme");

		try {
			syncService.saveAlarmSync(req);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void getOneById() {
		AlarmSync alarmSync = syncService.getOneById(syncService.getAllAlarmSync().get(0).getId());
		System.out.println(alarmSync);
	}

	@Test
	public void deleteAlarmSync() {
		syncService.deleteAlarmSync(syncService.getAllAlarmSync().get(0).getId());
		syncService.getAllAlarmSync().forEach(System.out :: println);
	}

	@Test
	public void getAllAlarmSync() {
		List<AlarmSync> alarmSyncList = syncService.getAllAlarmSync();
		alarmSyncList.forEach(System.out :: println);
	}
}