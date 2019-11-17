/**
 * FileName: AlarmSyncService
 * Author:   admin
 * Date:     2018/12/19 16:56
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.zhigang.myspringboot.service;

import com.zhigang.myspringboot.domain.AlarmSync;
import com.zhigang.myspringboot.domain.alarmws.NotifyAlarmSyncReq;
import com.zhigang.myspringboot.repository.AlarmSyncRepos;
import com.zhigang.myspringboot.repository.SyncInfoDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author admin
 * @version 3.2.2
 * @create 2018/12/19 16:56
 */
@Service
@Transactional
@Slf4j
public class AlarmSyncService implements SyncService {
    @Autowired
    private AlarmSyncRepos alarmSyncRepos;

    @Autowired
    private SyncInfoDao infoDao;

    @Override
    public void saveAlarmSync(NotifyAlarmSyncReq req) {

        AlarmSyncService.log.info("begin save model, model is [{}]:", req);
        AlarmSync sync = new AlarmSync();
        sync.setAlarmSyncSN(req.getAlarmSyncSN());
        sync.setBusinessType(req.getBusinessType());
        sync.setOpration(req.getAlarmSyncOoperation());
        sync.setProvinceSX(req.getProvinceSX());
        this.alarmSyncRepos.saveAlarmSync(sync);
        AlarmSyncService.log.info("save end.");

    }

    @Override
    public AlarmSync getOneById(Long id) {
        return this.infoDao.getOne(id);
    }

    @Override
    public void deleteAlarmSync(Long id) {
        this.infoDao.delete(getOneById(id));
    }

    @Override
    public List<AlarmSync> getAllAlarmSync() {
        return this.infoDao.findAll();
    }


}
