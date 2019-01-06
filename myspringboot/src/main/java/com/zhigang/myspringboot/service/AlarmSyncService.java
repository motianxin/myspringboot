/**
 * FileName: AlarmSyncService
 * Author:   zghuang
 * Date:     2018/12/19 16:56
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.zhigang.myspringboot.service;

import com.zhigang.myspringboot.domain.AlarmSync;
import com.zhigang.myspringboot.domain.alarmws.NotifyAlarmSyncReq;
import com.zhigang.myspringboot.repository.AlarmSyncRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author zghuang
 * @create 2018/12/19 16:56
 * @version 3.2.2
 */
@Service
@Transactional
public class AlarmSyncService implements SyncService{
	@Autowired
	private AlarmSyncRepos alarmSyncRepos;

	public void saveAlarmSync(NotifyAlarmSyncReq req) throws Exception{

		System.out.println("begin save model, model is :");
		System.out.println(req);
		AlarmSync sync  = new AlarmSync();
		sync.setAlarmSyncSN(req.getAlarmSyncSN());
		sync.setBusinessType(req.getBusinessType());
		sync.setOpration(req.getAlarmSyncOoperation());
		sync.setProvinceSX(req.getProvinceSX());
		alarmSyncRepos.saveAlarmSync(sync);
		System.out.println("save end.");

	}

}
