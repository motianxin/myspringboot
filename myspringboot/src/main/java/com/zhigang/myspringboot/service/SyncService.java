package com.zhigang.myspringboot.service;

import com.zhigang.myspringboot.domain.AlarmSync;
import com.zhigang.myspringboot.domain.alarmws.NotifyAlarmSyncReq;

import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Administrator
 * @create 2018/12/20 16:51
 * @since 3.2.2
 */
public interface SyncService {
    void saveAlarmSync(NotifyAlarmSyncReq req);


    AlarmSync getOneById(Long id);

    void deleteAlarmSync(Long id);

    List<AlarmSync> getAllAlarmSync();

}
