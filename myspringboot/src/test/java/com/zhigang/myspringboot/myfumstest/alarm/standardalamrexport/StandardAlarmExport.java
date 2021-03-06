/**
 * FileName: StandardAlarmExport
 * Author:   Administrator
 * Date:     2019/7/5 16:21
 * Description: sql connect
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.zhigang.myspringboot.myfumstest.alarm.standardalamrexport;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * 〈sql connect〉
 *
 * @author Administrator
 * @version 3.2.2
 * @create 2019/7/5 16:21
 */
public class StandardAlarmExport {

    public static final String FILE_NAME = "StandardAlarms";

    public static final String SUFFIX = ".xlsx";

    private static final String USERNAME = "root";

    private static final String PWD = "Mysql123!";

    private static final String DBNAME = "fumsdb";

    public static final String SQL = "SELECT\n" + "\tCONCAT(C.typeno,'') AS '映射规则序号',\n" + "\t(\n" + "\t\tCASE\n" +
        "\t\tWHEN C.alarmlevel = 1 THEN\n" + "\t\t\t'严重'\n" + "\t\tWHEN C.alarmlevel = 2 THEN\n" + "\t\t\t'主要'\n" +
        "\t\tWHEN C.alarmlevel = 3 THEN\n" + "\t\t\t'一般'\n" + "\t\tWHEN C.alarmlevel = 4 THEN\n" + "\t\t\t'警告'\n" +
        "\t\tELSE\n" + "\t\t\t'未知'\n" + "\t\tEND\n" + "\t) AS '厂家告警级别',\n" + "\t(\n" + "\t\tCASE\n" +
        "\t\tWHEN C.alarmlevel = 1 THEN\n" + "\t\t\t'一级'\n" + "\t\tWHEN C.alarmlevel = 2 THEN\n" + "\t\t\t'二级'\n" +
        "\t\tWHEN C.alarmlevel = 3 THEN\n" + "\t\t\t'三级'\n" + "\t\tWHEN C.alarmlevel = 4 THEN\n" + "\t\t\t'四级'\n" +
        "\t\tELSE\n" + "\t\t\t'未知'\n" + "\t\tEND\n" + "\t) AS '网管告警级别',\n" + "\t(\n" + "\t\tCASE\n" +
        "\t\tWHEN C.alarmlevel = 1 THEN\n" + "\t\t\t'critical'\n" + "\t\tWHEN C.alarmlevel = 2 THEN\n" +
        "\t\t\t'major'\n" + "\t\tWHEN C.alarmlevel = 3 THEN\n" + "\t\t\t'minor'\n" +
        "\t\tWHEN C.alarmlevel = 4 THEN\n" + "\t\t\t'warning'\n" + "\t\tELSE\n" + "\t\t\t'unknown'\n" + "\t\tEND\n" +
        "\t) AS '网元告警级别',\n" + "\tCONCAT(C.cname,'') AS '告警标题',\n" + "\tCONCAT(C.ename,'') AS '厂家告警ID(key)',\n" +
        "\tCONCAT(\n" + "\t\t'1.3.6.1.4.1.1943.1.2.1.12.8.',\n" + "\t\tRIGHT (C.alarmCmccId, 6)\n" +
        "\t) AS '厂家告警OID',\n" + "\tCONCAT(C.alarmCmccId,'') AS '网管告警ID',\n" +
        "\tCONCAT(C.alarmExplanation,'') AS '告警解释',\n" + "\t(\n" + "\t\tCASE\n" + "\t\tWHEN C.neAffected = 1 THEN\n" +
        "\t\t\t'设备性能下降'\n" + "\t\tWHEN C.neAffected = 2 THEN\n" + "\t\t\t'无影响'\n" + "\t\tWHEN C.neAffected = 3 THEN\n" +
        "\t\t\t'可能设备局部故障'\n" + "\t\tELSE\n" + "\t\t\t'未知影响'\n" + "\t\tEND\n" + "\t) AS '设备影响',\n" + "\t(\n" +
        "\t\tCASE\n" + "\t\tWHEN C.businessAffected = 1 THEN\n" + "\t\t\t'可能业务受影响'\n" +
        "\t\tWHEN C.businessAffected = 2 THEN\n" + "\t\t\t'无影响'\n" + "\t\tWHEN C.businessAffected = 3 THEN\n" +
        "\t\t\t'可能业务全阻'\n" + "\t\tELSE\n" + "\t\t\t'未知影响'\n" + "\t\tEND\n" + "\t) AS '业务影响',\n" + "\t(\n" +
        "\t\tCASE\n" + "\t\tWHEN C.reportCmcc = 1 THEN\n" + "\t\t\t'是'\n" + "\t\tELSE\n" + "\t\t\t'是'\n" + "\t\tEND\n" +
        "\t) AS '是否上报集团',\n" + "\tCONCAT(T.alarmClassCn,'') AS '告警逻辑分类',\n" +
        "\tCONCAT(T.alarmSubClassCn,'') AS '告警逻辑子分类',\n" + "\tCONCAT(C.cname,'') AS '告警标准名'\n" + "FROM\n" + "\t" +
        StandardAlarmExport.DBNAME + ".alarm_probable_cause AS C,\n" + "\t" + StandardAlarmExport.DBNAME +
        ".alarm_type AS T\n" + "WHERE\n" + "\tC.alarmlevel <> 5\n" + "AND C.alarmtype = T.id;";

    private static final String HOST = "localhost";

    private static final int PORT = 3307;

    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";

    private static final String DB_URL =
        new StringBuilder("jdbc:mysql://").append(StandardAlarmExport.HOST).append(":").append(StandardAlarmExport.PORT)
            .append("/").append(StandardAlarmExport.DBNAME)
            .append("?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&serverTimezone=GMT%2B8")
            .toString();

    private static final String PATH = "C:/Users/Documents";

    /**
     * 表头行样式
     */
    private static CellStyle headStyle;

    /**
     * 表头行字体
     */
    private static Font headFont;

    /**
     * 内容行样式
     */
    private static CellStyle contentStyle;

    /**
     * 内容行字体
     */
    private static Font contentFont;

    public static void exportAlarms() {
        Connection conn = null;
        Statement stmt = null;
        FileOutputStream fileOut = null;
        try {
            // 注册 JDBC 驱动
            Class.forName(StandardAlarmExport.JDBC_DRIVER);
            // 打开链接
            System.out.println("连接数据库...");
            conn = DriverManager
                .getConnection(StandardAlarmExport.DB_URL, StandardAlarmExport.USERNAME, StandardAlarmExport.PWD);
            // 执行查询
            System.out.println(" 实例化Statement对象...");
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(StandardAlarmExport.SQL);
            ResultSetMetaData resultSetMetaData = rs.getMetaData();
            int columnCount = resultSetMetaData.getColumnCount();
            String[] columns = new String[columnCount];
            for (int i = 1; i <= columnCount; i++) {
                columns[i - 1] = resultSetMetaData.getColumnName(i);
            }
            System.out.println(Arrays.toString(columns));
            XSSFWorkbook workbook = StandardAlarmExport.init();
            XSSFSheet sheet = workbook.createSheet(StandardAlarmExport.FILE_NAME);
            StandardAlarmExport.createTableHeadRow(sheet.createRow(0), columns);
            int rowNum = 1;
            XSSFRow row = null;
            XSSFCell cell = null;
            // 展开结果集数据库
            while (rs.next()) {
                row = sheet.createRow(rowNum++);
                for (int i = 1; i <= columnCount; i++) {
                    System.out.print(rs.getObject(i) + ", ");
                    cell = row.createCell(i - 1);
                    cell.setCellValue(rs.getObject(i) + "");
                }
                System.out.println();
            }
            StandardAlarmExport.adjustColumnSize(sheet, columnCount);
            File file = new File(StandardAlarmExport.PATH);
            if (!file.exists()) {
                file.mkdirs();
            }
            String fileName =
                StandardAlarmExport.FILE_NAME + "_" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) +
                    StandardAlarmExport.SUFFIX;
            fileOut = new FileOutputStream(StandardAlarmExport.PATH + File.separator + fileName);
            workbook.write(fileOut);
            Desktop.getDesktop().open(file);
            System.out.println("export end!");
        } catch (SQLException se) {
            // 处理 JDBC 错误
            se.printStackTrace();
        } catch (Exception e) {
            // 处理 Class.forName 错误
            e.printStackTrace();
        } finally {
            // 关闭资源
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException se2) {
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
            try {
                if (fileOut != null) {
                    fileOut.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 初始化表格对象
     *
     * @return
     */
    public static XSSFWorkbook init() {
        XSSFWorkbook wb = new XSSFWorkbook();

        StandardAlarmExport.headStyle = wb.createCellStyle();
        StandardAlarmExport.headFont = wb.createFont();
        StandardAlarmExport.contentStyle = wb.createCellStyle();
        StandardAlarmExport.contentFont = wb.createFont();

        StandardAlarmExport.initHeadFont();
        StandardAlarmExport.initHeadCellStyle();

        StandardAlarmExport.initContentFont();
        StandardAlarmExport.initContentCellStyle();

        return wb;
    }

    private static void initContentFont() {
        StandardAlarmExport.contentFont.setFontName("微软雅黑Ligth");
        StandardAlarmExport.contentFont.setFontHeightInPoints((short) 11);
        StandardAlarmExport.contentFont.setBold(false);
        StandardAlarmExport.contentFont.setCharSet(Font.DEFAULT_CHARSET);
        StandardAlarmExport.contentFont.setColor(IndexedColors.BLACK.getIndex());
    }

    private static void initHeadFont() {
        StandardAlarmExport.headFont.setFontName("微软雅黑");
        StandardAlarmExport.headFont.setFontHeightInPoints((short) 11);
        StandardAlarmExport.headFont.setBold(true);
        StandardAlarmExport.headFont.setCharSet(Font.DEFAULT_CHARSET);
        StandardAlarmExport.headFont.setColor(IndexedColors.BLACK.getIndex());
    }


    private static void initContentCellStyle() {
        StandardAlarmExport.contentStyle.setAlignment(HorizontalAlignment.CENTER_SELECTION);
        StandardAlarmExport.contentStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        StandardAlarmExport.contentStyle.setFont(StandardAlarmExport.contentFont);
        StandardAlarmExport.contentStyle.setBorderTop(BorderStyle.THIN);
        StandardAlarmExport.contentStyle.setBorderBottom(BorderStyle.THIN);
        StandardAlarmExport.contentStyle.setBorderLeft(BorderStyle.THIN);
        StandardAlarmExport.contentStyle.setBorderRight(BorderStyle.THIN);
        StandardAlarmExport.contentStyle.setTopBorderColor(IndexedColors.ORANGE.getIndex());
        StandardAlarmExport.contentStyle.setBottomBorderColor(IndexedColors.ORANGE.getIndex());
        StandardAlarmExport.contentStyle.setLeftBorderColor(IndexedColors.ORANGE.getIndex());
        StandardAlarmExport.contentStyle.setRightBorderColor(IndexedColors.ORANGE.getIndex());
        StandardAlarmExport.contentStyle.setWrapText(true);
    }

    private static void initHeadCellStyle() {
        StandardAlarmExport.headStyle.setAlignment(HorizontalAlignment.CENTER_SELECTION);
        StandardAlarmExport.headStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        StandardAlarmExport.headStyle.setFont(StandardAlarmExport.headFont);
        StandardAlarmExport.headStyle.setFillForegroundColor(IndexedColors.BLUE_GREY.getIndex());
        StandardAlarmExport.headStyle.setBorderTop(BorderStyle.MEDIUM);
        StandardAlarmExport.headStyle.setBorderBottom(BorderStyle.THIN);
        StandardAlarmExport.headStyle.setBorderLeft(BorderStyle.THIN);
        StandardAlarmExport.headStyle.setBorderRight(BorderStyle.THIN);
        StandardAlarmExport.headStyle.setTopBorderColor(IndexedColors.ORANGE.getIndex());
        StandardAlarmExport.headStyle.setBottomBorderColor(IndexedColors.ORANGE.getIndex());
        StandardAlarmExport.headStyle.setLeftBorderColor(IndexedColors.ORANGE.getIndex());
        StandardAlarmExport.headStyle.setRightBorderColor(IndexedColors.ORANGE.getIndex());
    }


    /**
     * 调整列宽
     *
     * @param sheet
     * @param columnNum
     */
    public static void adjustColumnSize(XSSFSheet sheet, int columnNum) {
        for (int i = 0; i < columnNum; i++) {
            sheet.autoSizeColumn(i);
            sheet.setColumnWidth(i, sheet.getColumnWidth(i) * 13 / 8);
        }
    }

    public static void createTableHeadRow(XSSFRow row, String[] colNames) {

        // 列头名称
        XSSFCell cell = null;
        for (int i = 0; i < colNames.length; i++) {
            cell = row.createCell(i);
            cell.setCellStyle(StandardAlarmExport.headStyle);
            cell.setCellValue(colNames[i]);
        }
    }

    public static void main(String[] args) {
        StandardAlarmExport.exportAlarms();
        // System.out.println(SQL);
    }
}
