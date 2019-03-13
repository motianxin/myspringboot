/**
 * FileName: AlarmMsgXml
 * Author:   zghuang
 * Date:     2019/3/11 11:40
 * Description: agent接收udp告警消息模型
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.zhigang.myspringboot.myfumstest.alarm.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * 〈agent接收udp告警消息模型〉
 *
 * @author zghuang
 * @version 3.2.2
 * @create 2019/3/11 11:40
 */
@XmlRootElement(name = "MessageBody")
@XmlType(propOrder = {"messageType", "entityInstance", "probableCause", "specificReason", "alarmType", "entityType", "severity", "message"})
public class AlarmMsgXml {

    private String messageType;

    private String entityInstance;

    private String probableCause;

    private String specificReason;

    private String alarmType;

    private String entityType;

    private String severity;

    private String message;

    @XmlElement(name = "MessageType")
    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    @XmlElement(name = "EntityInstance")
    public String getEntityInstance() {
        return entityInstance;
    }

    public void setEntityInstance(String entityInstance) {
        this.entityInstance = entityInstance;
    }
    @XmlElement(name = "ProbableCause")
    public String getProbableCause() {
        return probableCause;
    }

    public void setProbableCause(String probableCause) {
        this.probableCause = probableCause;
    }
    @XmlElement(name = "SpecificReason")
    public String getSpecificReason() {
        return specificReason;
    }

    public void setSpecificReason(String specificReason) {
        this.specificReason = specificReason;
    }
    @XmlElement(name = "AlarmType")
    public String getAlarmType() {
        return alarmType;
    }

    public void setAlarmType(String alarmType) {
        this.alarmType = alarmType;
    }
    @XmlElement(name = "EntityType")
    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }
    @XmlElement(name = "Severity")
    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }
    @XmlElement(name = "Message")
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
