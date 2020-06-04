/**
 * FileName: SnmpReceiverStart
 * Author:   admin
 * Date:     2019/1/9 14:46
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.zhigang.myspringboot.controller;

import com.zhigang.myspringboot.domain.ResponseModel;
import com.zhigang.myspringboot.service.SnmpReceiverService;
import com.zhigang.myspringboot.utils.common.Constans;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author admin
 * @version 3.2.2
 * @create 2019/1/9 14:46
 */
@RestController
@Slf4j
public class SnmpReceiverStart {


    @Resource
    private SnmpReceiverService snmpReceiverService;

    @GetMapping("/startReceiverSnmpTrap")
    public ResponseModel startReceiverSnmpTrap(@RequestParam("ip") String ip, @RequestParam("port") String port) {

        SnmpReceiverStart.log.info("startReceiverSnmpTrap begin, Receiver ip is {}, port is {}", ip, port);
        ResponseModel responseModel = new ResponseModel();

        String result;
        try {
            result = this.snmpReceiverService.snmpReceiverstatus(ip, port);
            responseModel.setCode(Constans.SUCCESS);
        } catch (Exception e) {
            SnmpReceiverStart.log.error("startReceiverSnmpTrap exception.", e);
            result = "startReceiverSnmpTrap exception :" + e.getMessage();
            responseModel.setCode(Constans.FAIL);
        }
        responseModel.setMsg(result);
        return responseModel;
    }


    @GetMapping("/closeReceiverSnmpTrap")
    public ResponseModel closeReceiverSnmpTrap() {
        ResponseModel responseModel = new ResponseModel();
        String result;
        try {
            result = this.snmpReceiverService.closeReceiver();
            responseModel.setCode(Constans.SUCCESS);
        } catch (Exception e) {
            SnmpReceiverStart.log.error("startReceiverSnmpTrap exception.", e);
            result = "closeReceiverSnmpTrap exception:" + e.getMessage();
            responseModel.setCode(Constans.FAIL);
        }
        responseModel.setMsg(result);
        return responseModel;
    }
}
