package com.zhigang.myspringboot.repository;

import com.zhigang.myspringboot.domain.AlarmSync;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Administrator
 * @create 2018/12/27 21:59
 * @since 3.2.2
 */
public interface SyncInfoDao extends JpaRepository<AlarmSync, Long> {
}
