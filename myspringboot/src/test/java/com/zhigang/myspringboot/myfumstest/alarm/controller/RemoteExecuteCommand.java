/**
 * FileName: RemoteExecuteCommand
 * Author:   admin
 * Date:     2019/3/11 10:29
 * Description: 远程登录Linux并执行脚本
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.zhigang.myspringboot.myfumstest.alarm.controller;


import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;
import com.zhigang.myspringboot.myfumstest.alarm.model.AlarmMsgXml;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.xml.bind.JAXB;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * 〈远程登录Linux并执行脚本〉
 *
 * @author admin
 * @version 3.2.2
 * @create 2019/3/11 10:29
 */
@Slf4j
public class RemoteExecuteCommand {

    private static final String DEFAULTCHART = "UTF-8";
    private static final int ALARMCODE = 1;
    private static final int CLEAR_ALARMCODE = 2;
    private static final String PATH = "C:\\Users\\Administrator.000\\Desktop\\sendAlarm.xlsx";
    private static final String CONMANDPIX = "sh /opt/fonsview/NE/agent/script/ucarp/sendAlarm.sh ";
    private static final String CONMANDPATH = "C:\\Users\\Administrator.000\\Desktop\\sendAllAlarm.log";
    private static Connection conn;
    private String ip;
    private String userName;
    private String userPwd;

    public RemoteExecuteCommand(String ip, String userName, String userPwd) {
        this.ip = ip;
        this.userName = userName;
        this.userPwd = userPwd;
    }

    public RemoteExecuteCommand() {
    }

    public static String processStdout(InputStream in, String charset) {
        InputStream stdout = new StreamGobbler(in);
        StringBuffer buffer = new StringBuffer();
        ;
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(stdout, charset));
            String line = null;
            while ((line = br.readLine()) != null) {
                buffer.append(line + "\n");
            }
        } catch (UnsupportedEncodingException e) {
            RemoteExecuteCommand.log.error("exception:", e);
        } catch (IOException e) {
            RemoteExecuteCommand.log.error("exception:", e);
        }
        return buffer.toString();
    }

    public static void main(String[] args) {
        //createAllAlarms();

        //clearAllAlarms();

        //writeConmandToLog(CLEAR_ALARMCODE);

        RemoteExecuteCommand remoteExecuteCommand = new RemoteExecuteCommand("10.96.156.241", "root", "hello123");
        try {
            String path = remoteExecuteCommand.execute("sh /opt/fonsview/NE/fums/bin/rsync.sh");
            System.out.println("result :" + path);
            System.out.println(StringUtils.isEmpty(path));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void writeConmandToLog(int code) {

        RemoteExecuteCommand remoteExecuteCommand = new RemoteExecuteCommand();
        List<AlarmMsgXml> alarmMsgXmls = null;
        try {
            alarmMsgXmls = remoteExecuteCommand.getAlarmMsgXmlsFromXsl(code);
        } catch (IOException e) {
            RemoteExecuteCommand.log.info("exception:", e);
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File(RemoteExecuteCommand.CONMANDPATH), true))) {

            for (AlarmMsgXml alarmMsgXml : alarmMsgXmls) {
                writer.write(remoteExecuteCommand.getCmd(alarmMsgXml) + "\n");
            }
        } catch (IOException e) {
            RemoteExecuteCommand.log.info("exception:", e);
        }
    }

    public static void createAllAlarms() {
        RemoteExecuteCommand remoteExecuteCommand = new RemoteExecuteCommand("10.96.155.163", "root", "hello123");
        remoteExecuteCommand.sendAlarm(RemoteExecuteCommand.ALARMCODE);
    }

    public static void clearAllAlarms() {
        RemoteExecuteCommand remoteExecuteCommand = new RemoteExecuteCommand("10.96.155.163", "root", "hello123");
        remoteExecuteCommand.sendAlarm(RemoteExecuteCommand.CLEAR_ALARMCODE);
    }

    public Boolean login() {
        boolean flg = false;
        try {
            RemoteExecuteCommand.conn = new Connection(this.ip);
            //连接
            RemoteExecuteCommand.conn.connect();
            //认证
            flg = RemoteExecuteCommand.conn.authenticateWithPassword(this.userName, this.userPwd);
        } catch (IOException e) {
            RemoteExecuteCommand.log.info("exception:", e);
        }
        return flg;
    }

    public String execute(String cmd) throws IOException {
        String result = "";
        if (login()) {
            Session session = RemoteExecuteCommand.conn.openSession();
            session.execCommand(cmd);
            result = RemoteExecuteCommand.processStdout(session.getStdout(), RemoteExecuteCommand.DEFAULTCHART);
            System.out.println("gegeger = " + result);
            //如果为得到标准输出为空，说明脚本执行出错了
            if (StringUtils.isBlank(result)) {
                result = RemoteExecuteCommand.processStdout(session.getStderr(), RemoteExecuteCommand.DEFAULTCHART);
            }
            RemoteExecuteCommand.conn.close();
            session.close();
        }

        return result;
    }

    public void sendAlarm(int code) {
        List<AlarmMsgXml> alarmMsgXmls = null;
        try {
            alarmMsgXmls = getAlarmMsgXmlsFromXsl(code);
        } catch (IOException e) {
            RemoteExecuteCommand.log.info("exception:", e);
        }

        for (AlarmMsgXml alarmMsgXml : alarmMsgXmls) {
            try {
                System.out.println(execute(getCmd(alarmMsgXml)));
            } catch (IOException e) {
                RemoteExecuteCommand.log.info("exception:", e);
            }
        }

    }

    private String getCmd(AlarmMsgXml alarmMsgXml) {

        StringBuffer stringBuffer = new StringBuffer(RemoteExecuteCommand.CONMANDPIX);
        stringBuffer.append("'");
        StringWriter str = new StringWriter();
        JAXB.marshal(alarmMsgXml, str);
        return stringBuffer.append(str.toString()).append("'").toString().replaceAll("[\\t\\n\\r]", "");
    }

    private List<AlarmMsgXml> getAlarmMsgXmlsFromXsl(int code) throws IOException {
        List<AlarmMsgXml> alarmMsgXmlList = new ArrayList<>();
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(new File(RemoteExecuteCommand.PATH)));
        Workbook wb = new XSSFWorkbook(in);
        Sheet alarmMsg = wb.getSheet("alarmMsg");
        int rows = alarmMsg.getLastRowNum();
        Row row;
        for (int i = code; i <= rows; i = i + 2) {
            row = alarmMsg.getRow(i);
            alarmMsgXmlList.add(createAlarmMsg(row));
        }
        return alarmMsgXmlList;
    }

    private AlarmMsgXml createAlarmMsg(Row row) {
        AlarmMsgXml alarmMsgXml = new AlarmMsgXml();

        alarmMsgXml.setProbableCause(row.getCell(1).getStringCellValue());
        alarmMsgXml.setSpecificReason(row.getCell(2).getStringCellValue());
        alarmMsgXml.setAlarmType(row.getCell(3).getStringCellValue());
        alarmMsgXml.setSeverity(row.getCell(4).getStringCellValue());
        alarmMsgXml.setMessageType("alarm");
        alarmMsgXml.setEntityType("app");
        alarmMsgXml.setMessage("create Alarm");
        alarmMsgXml.setEntityInstance("CMM");
        return alarmMsgXml;
    }


}
