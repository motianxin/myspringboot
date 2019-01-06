package com.zhigang.myspringboot.repository;

import com.zhigang.myspringboot.domain.AlarmSync;
import org.springframework.stereotype.Repository;

/**
 * 〈一句话功能简述〉<br>
 * 〈数据库访问接口〉
 *
 * @author Administrator
 * @create 2018/12/20 16:40
 * @since 3.2.2
 */
@Repository
public interface AlarmSyncRepos{
		void saveAlarmSync(AlarmSync sync);
}
