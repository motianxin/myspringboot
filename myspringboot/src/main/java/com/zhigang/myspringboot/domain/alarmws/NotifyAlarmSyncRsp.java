package com.zhigang.myspringboot.domain.alarmws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>anonymous complex type的 Java 类。
 *
 * <p>以下模式片段指定包含在此类中的预期内容。
 *
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="resultCode" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="resultMsg" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="timeStamp" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"resultCode", "resultMsg", "timeStamp"})
@XmlRootElement(name = "notifyAlarmSyncRsp", namespace = "http://www.ctsi.com.cn/webservices/service")
public class NotifyAlarmSyncRsp {

    protected int resultCode;
    @XmlElement(required = true, nillable = true)
    protected String resultMsg;
    @XmlElement(required = true)
    protected String timeStamp;

    public NotifyAlarmSyncRsp(int resultCode, String resultMsg, String timeStamp) {
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
        this.timeStamp = timeStamp;
    }

    public NotifyAlarmSyncRsp() {
    }

    /**
     * 获取resultCode属性的值。
     */
    public int getResultCode() {
        return this.resultCode;
    }

    /**
     * 设置resultCode属性的值。
     */
    public void setResultCode(int value) {
        this.resultCode = value;
    }

    /**
     * 获取resultMsg属性的值。
     *
     * @return possible object is
     * {@link String }
     */
    public String getResultMsg() {
        return this.resultMsg;
    }

    /**
     * 设置resultMsg属性的值。
     *
     * @param value allowed object is
     * {@link String }
     */
    public void setResultMsg(String value) {
        this.resultMsg = value;
    }

    /**
     * 获取timeStamp属性的值。
     *
     * @return possible object is
     * {@link String }
     */
    public String getTimeStamp() {
        return this.timeStamp;
    }

    /**
     * 设置timeStamp属性的值。
     *
     * @param value allowed object is
     * {@link String }
     */
    public void setTimeStamp(String value) {
        this.timeStamp = value;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("NotifyAlarmSyncRsp{");
        sb.append("resultCode=").append(this.resultCode);
        sb.append(", resultMsg='").append(this.resultMsg).append('\'');
        sb.append(", timeStamp='").append(this.timeStamp).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
