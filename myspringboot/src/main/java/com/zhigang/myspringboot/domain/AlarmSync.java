/**
 * FileName: AlarmSync
 * Author:   admin
 * Date:     2018/12/19 16:57
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.zhigang.myspringboot.domain;

import com.zhigang.myspringboot.domain.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author admin
 * @version 3.2.2
 * @create 2018/12/19 16:57
 */
@Entity
@Table(name = "alarmsync")
@Getter
@Setter
public class AlarmSync extends BaseEntity {

    private int opration;

    private int businessType;

    private String provinceSX;

    private String alarmSyncSN;

    @Override
    public String toString() {
        return "AlarmSync{" + "id=" + this.id + ", opration=" + this.opration + ", businessType=" + this.businessType + ", provinceSX='" + this.provinceSX + '\'' + ", alarmSyncSN='" + this.alarmSyncSN + '\'' + '}';
    }
}
