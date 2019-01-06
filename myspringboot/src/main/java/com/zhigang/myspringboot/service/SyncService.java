package com.zhigang.myspringboot.service;

import com.zhigang.myspringboot.domain.alarmws.NotifyAlarmSyncReq;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Administrator
 * @create 2018/12/20 16:51
 * @since 3.2.2
 */
@Service
@Transactional
public interface SyncService {
	void saveAlarmSync(NotifyAlarmSyncReq req) throws Exception;
}
