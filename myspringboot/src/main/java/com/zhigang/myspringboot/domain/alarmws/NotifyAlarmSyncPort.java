
package com.zhigang.myspringboot.domain.alarmws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebService(name = "NotifyAlarmSyncPort", targetNamespace = "http://www.ctsi.com.cn/webservices/service")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
@XmlSeeAlso({
    ObjectFactory.class
})
public interface NotifyAlarmSyncPort {


    /**
     * 
     * @param notifyAlarmSyncReq
     * @return
     *     returns alarmws.NotifyAlarmSyncRsp
     */
    @WebMethod
    @WebResult(name = "notifyAlarmSyncRsp", targetNamespace = "http://www.ctsi.com.cn/webservices/service", partName = "notifyAlarmSyncRsp")
    public NotifyAlarmSyncRsp notifyAlarmSync(
        @WebParam(name = "notifyAlarmSyncReq", targetNamespace = "http://www.ctsi.com.cn/webservices/service", partName = "notifyAlarmSyncReq")
				NotifyAlarmSyncReq notifyAlarmSyncReq);

}
