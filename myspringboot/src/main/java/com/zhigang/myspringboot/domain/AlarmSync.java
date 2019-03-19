/**
 * FileName: AlarmSync
 * Author:   zghuang
 * Date:     2018/12/19 16:57
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.zhigang.myspringboot.domain;

import com.zhigang.myspringboot.domain.entity.BaseEntity;

import javax.persistence.*;
/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author zghuang
 * @create 2018/12/19 16:57
 * @version 3.2.2
 */
@Entity
@Table(name = "alarmsync")
public class AlarmSync extends BaseEntity {

	private int opration;

	private int businessType;

	private String provinceSX;

	private String alarmSyncSN;

	public int getOpration() {
		return opration;
	}

	public void setOpration(int opration) {
		this.opration = opration;
	}

	public int getBusinessType() {
		return businessType;
	}

	public void setBusinessType(int businessType) {
		this.businessType = businessType;
	}

	public String getProvinceSX() {
		return provinceSX;
	}

	public void setProvinceSX(String provinceSX) {
		this.provinceSX = provinceSX;
	}

	public String getAlarmSyncSN() {
		return alarmSyncSN;
	}

	public void setAlarmSyncSN(String alarmSyncSN) {
		this.alarmSyncSN = alarmSyncSN;
	}

	@Override
	public String toString() {
		return "AlarmSync{" +
				"id=" + id +
				", opration=" + opration +
				", businessType=" + businessType +
				", provinceSX='" + provinceSX + '\'' +
				", alarmSyncSN='" + alarmSyncSN + '\'' +
				'}';
	}
}
