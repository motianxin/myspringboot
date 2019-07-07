/**
 * FileName: ReposImpl
 * Author:   admin
 * Date:     2018/12/20 17:19
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.zhigang.myspringboot.repository;

import com.zhigang.myspringboot.domain.AlarmSync;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author admin
 * @version 3.2.2
 * @create 2018/12/20 17:19
 */
@Repository
public class ReposImpl implements AlarmSyncRepos {


    @PersistenceContext
    protected EntityManager em;

    @Override
    public void saveAlarmSync(AlarmSync sync) {
        if (sync != null) {
            em.persist(sync);
        }
    }


}
