/**
 * FileName: ExportUtil
 * Author:   admin
 * Date:     2019/3/19 9:37
 * Description: 导出工具类
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.zhigang.myspringboot.utils;

import com.zhigang.myspringboot.domain.ExportBaseVo;
import com.zhigang.myspringboot.utils.strutils.StringTools;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 〈导出工具类〉
 *
 * @author admin
 * @version 3.2.2
 * @create 2019/3/19 9:37
 */
public class ExportUtil {
    private final static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private final static String FILE_NAME_FORMAT = "yyyyMMddHHmmss";
    private static final String SUFFIX = ".xls";
    /**
     * 标题行样式
     */
    private static CellStyle titleStyle;
    /**
     * 标题行字体
     */
    private static Font titleFont;
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

    /**
     * @Author admin
     * @Description 表格导出公共方法
     * @Param [vos, tableHeader, sheetName]
     * @Return org.apache.poi.ss.usermodel.Workbook
     * @date 2019/3/19 10:27
     **/
    public static <T extends ExportBaseVo> Workbook getWbByVos(List<T> vos, String[] tableHeader, String sheetName) {
        HSSFWorkbook wb = ExportUtil.init();
        HSSFSheet sheet = wb.createSheet(sheetName);
        ExportUtil.createTableHeadRow(sheet.createRow(0), tableHeader);
        ExportUtil.createTableDataRows(sheet, vos, tableHeader);
        ExportUtil.adjustColumnSize(sheet, tableHeader.length);
        return wb;
    }


    private static <T extends ExportBaseVo> void createTableDataRows(HSSFSheet sheet, List<T> vos,
                                                                     String[] tableHeader) {
        if (vos != null) {
            HSSFRow row;
            HSSFCell cell;
            Map<String, String> rowMap;
            int columnLength = tableHeader.length;
            int i = 1;
            for (T vo : vos) {
                if (vo != null) {
                    row = sheet.createRow(i++);
                    rowMap = vo.createMap(tableHeader);
                    for (int j = 0; j < columnLength; j++) {
                        cell = row.createCell(j);
                        cell.setCellValue(StringTools.formatNullStr(rowMap.get(tableHeader[j])));
                    }
                }
            }
        }
    }

    /**
     * 初始化表格对象
     *
     * @return
     */
    public static HSSFWorkbook init() {
        HSSFWorkbook wb = new HSSFWorkbook();

        ExportUtil.titleFont = wb.createFont();
        ExportUtil.titleStyle = wb.createCellStyle();
        ExportUtil.headStyle = wb.createCellStyle();
        ExportUtil.headFont = wb.createFont();
        ExportUtil.contentStyle = wb.createCellStyle();
        ExportUtil.contentFont = wb.createFont();

        ExportUtil.initTitleCellStyle();
        ExportUtil.initTitleFont();
        ExportUtil.initHeadCellStyle();
        ExportUtil.initHeadFont();
        ExportUtil.initContentCellStyle();
        ExportUtil.initContentFont();
        return wb;
    }

    private static void initContentFont() {
        ExportUtil.contentFont.setFontName("微软雅黑");
        ExportUtil.contentFont.setFontHeightInPoints((short) 10);
        ExportUtil.contentFont.setBold(false);
        ExportUtil.contentFont.setCharSet(Font.DEFAULT_CHARSET);
        ExportUtil.contentFont.setColor(IndexedColors.BLACK.getIndex());
    }

    private static void initHeadFont() {
        ExportUtil.headFont.setFontName("微软雅黑");
        ExportUtil.headFont.setFontHeightInPoints((short) 10);
        ExportUtil.headFont.setBold(true);
        ExportUtil.headFont.setCharSet(Font.DEFAULT_CHARSET);
        ExportUtil.headFont.setColor(IndexedColors.BLACK.getIndex());
    }

    private static void initTitleFont() {
        ExportUtil.titleFont.setFontName("微软雅黑");
        ExportUtil.titleFont.setFontHeightInPoints((short) 20);
        ExportUtil.titleFont.setBold(true);
        ExportUtil.titleFont.setCharSet(Font.DEFAULT_CHARSET);
        ExportUtil.titleFont.setColor(IndexedColors.BLACK.getIndex());
    }

    private static void initContentCellStyle() {
        ExportUtil.contentStyle.setAlignment(HorizontalAlignment.CENTER_SELECTION);
        ExportUtil.contentStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        ExportUtil.contentStyle.setFont(ExportUtil.contentFont);
        ExportUtil.contentStyle.setBorderTop(BorderStyle.THIN);
        ExportUtil.contentStyle.setBorderBottom(BorderStyle.THIN);
        ExportUtil.contentStyle.setBorderLeft(BorderStyle.THIN);
        ExportUtil.contentStyle.setBorderRight(BorderStyle.THIN);
        ExportUtil.contentStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
        ExportUtil.contentStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        ExportUtil.contentStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        ExportUtil.contentStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
        ExportUtil.contentStyle.setWrapText(true);
    }

    private static void initHeadCellStyle() {
        ExportUtil.headStyle.setAlignment(HorizontalAlignment.CENTER_SELECTION);
        ExportUtil.headStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        ExportUtil.headStyle.setFont(ExportUtil.headFont);
        ExportUtil.headStyle.setFillBackgroundColor(IndexedColors.YELLOW.getIndex());
        ExportUtil.headStyle.setBorderTop(BorderStyle.MEDIUM);
        ExportUtil.headStyle.setBorderBottom(BorderStyle.THIN);
        ExportUtil.headStyle.setBorderLeft(BorderStyle.THIN);
        ExportUtil.headStyle.setBorderRight(BorderStyle.THIN);
        ExportUtil.headStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
        ExportUtil.headStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        ExportUtil.headStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        ExportUtil.headStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
    }

    private static void initTitleCellStyle() {
        ExportUtil.titleStyle.setAlignment(HorizontalAlignment.CENTER_SELECTION);
        ExportUtil.titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        ExportUtil.titleStyle.setFont(ExportUtil.titleFont);
        ExportUtil.titleStyle.setFillBackgroundColor(IndexedColors.BLACK.getIndex());
    }

    /**
     * 调整列宽
     * sheet.AutoSizeColumn(i)不能解决中文列宽
     *
     * @param sheet
     * @param columnNum
     */
    public static void adjustColumnSize(HSSFSheet sheet, int columnNum) {
        for (int i = 0; i < columnNum; i++) {
            sheet.autoSizeColumn(i);
            sheet.setColumnWidth(i, sheet.getColumnWidth(i) * 17 / 10);
        }
    }

    public static void createTableHeadRow(HSSFRow row, String[] colNames) {
        // 表头
        row.setHeight((short) 400);
        // 列头名称
        HSSFCell cell = null;
        for (int i = 0; i < colNames.length; i++) {
            cell = row.createCell(i);
            cell.setCellStyle(ExportUtil.headStyle);
            cell.setCellValue(colNames[i]);
        }
    }

    public static String getFielNameSuf() {
        SimpleDateFormat format = new SimpleDateFormat(ExportUtil.FILE_NAME_FORMAT);
        return format.format(new Date()) + ExportUtil.SUFFIX;
    }

    public static String getTime(Timestamp alarmOccurTime) {
        SimpleDateFormat format = new SimpleDateFormat(ExportUtil.DATE_FORMAT);
        return format.format(alarmOccurTime);
    }

    public static String formatData(Date createTime) {
        SimpleDateFormat format = new SimpleDateFormat(ExportUtil.DATE_FORMAT);
        return format.format(createTime);
    }

    public static void wbIntoResponse(Workbook wb, HttpServletResponse response, String fileName) throws Exception {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        wb.write(os);
        byte[] content = os.toByteArray();
        InputStream is = new ByteArrayInputStream(content);
        ExportUtil.fileNameIntoResp(response, fileName);
        ServletOutputStream out = response.getOutputStream();
        BufferedInputStream bis = new BufferedInputStream(is);
        BufferedOutputStream bos = new BufferedOutputStream(out);
        byte[] buff = new byte[2048];
        int bytesRead;
        // Simple read/write loop.
        while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
            bos.write(buff, 0, bytesRead);
        }
        out.flush();
        bis.close();
        bos.close();
        out.close();
        os.close();
    }

    public static void fileNameIntoResp(HttpServletResponse response, String fileName) throws UnsupportedEncodingException {
        response.reset();
        response.setContentType("multipart/form-data;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes(), "UTF-8"));
        response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
        response.setCharacterEncoding("utf-8");
    }

    public static void exportError(HttpServletResponse response) {
        response.reset();
        response.setStatus(500);
    }
}
