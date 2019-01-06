
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
 *         &lt;element name="alarmSyncOoperation" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="businessType" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="provinceSX" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="alarmSyncSN" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "alarmSyncOoperation",
    "businessType",
    "provinceSX",
    "alarmSyncSN"
})
@XmlRootElement(name = "notifyAlarmSyncReq", namespace = "http://www.ctsi.com.cn/webservices/service")
public class NotifyAlarmSyncReq {

    protected int alarmSyncOoperation;
    protected int businessType;
    @XmlElement(required = true)
    protected String provinceSX;
    @XmlElement(required = true)
    protected String alarmSyncSN;

	public NotifyAlarmSyncReq() {
	}

	public NotifyAlarmSyncReq(int alarmSyncOoperation, int businessType, String provinceSX, String alarmSyncSN) {
		this.alarmSyncOoperation = alarmSyncOoperation;
		this.businessType = businessType;
		this.provinceSX = provinceSX;
		this.alarmSyncSN = alarmSyncSN;
	}

	/**
     * 获取alarmSyncOoperation属性的值。
     * 
     */
    public int getAlarmSyncOoperation() {
        return alarmSyncOoperation;
    }

    /**
     * 设置alarmSyncOoperation属性的值。
     * 
     */
    public void setAlarmSyncOoperation(int value) {
        this.alarmSyncOoperation = value;
    }

    /**
     * 获取businessType属性的值。
     * 
     */
    public int getBusinessType() {
        return businessType;
    }

    /**
     * 设置businessType属性的值。
     * 
     */
    public void setBusinessType(int value) {
        this.businessType = value;
    }

    /**
     * 获取provinceSX属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProvinceSX() {
        return provinceSX;
    }

    /**
     * 设置provinceSX属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProvinceSX(String value) {
        this.provinceSX = value;
    }

    /**
     * 获取alarmSyncSN属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAlarmSyncSN() {
        return alarmSyncSN;
    }

    /**
     * 设置alarmSyncSN属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAlarmSyncSN(String value) {
        this.alarmSyncSN = value;
    }

	@Override
	public String toString() {
		return "NotifyAlarmSyncReq{" +
				"alarmSyncOoperation=" + alarmSyncOoperation +
				", businessType=" + businessType +
				", provinceSX='" + provinceSX + '\'' +
				", alarmSyncSN='" + alarmSyncSN + '\'' +
				'}';
	}
}
