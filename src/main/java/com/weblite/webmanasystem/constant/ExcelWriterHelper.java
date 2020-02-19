package com.weblite.webmanasystem.constant;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author ：Beatrice
 * @date ：Created in 2020/2/19 14:08
 * @Description:
 */
public class ExcelWriterHelper {

    /**
     * 写入方法
     *
     * @param sheetFormatList
     * @param filePath
     * @throws IOException
     */
    public void write(List<ExcelWriteSheetFormat> sheetFormatList, String filePath) throws IOException {
        if (sheetFormatList == null) {
            return;
        }
        //创建工作表
        XSSFWorkbook workbook = new XSSFWorkbook();

        //遍历写入sheet
        int sheetIndex = 0;
        String sheetName = "";
        for (ExcelWriteSheetFormat sheetFormat : sheetFormatList) {
            sheetIndex++;
            sheetName = Common.isNullOrEmpty(sheetFormat.getSheetName()) ? String.valueOf(sheetIndex) : sheetFormat.getSheetName();
            //创建sheet
            XSSFSheet xssfSheet = workbook.createSheet(sheetName);
            int rowIndex = 0;
            for (List<Object> rowList : sheetFormat.getDataList()) {
                //创建行
                Row row = xssfSheet.createRow(rowIndex++);
                int cellIndex = 0;
                for (Object cellObject : rowList) {
                    //创建单元格
                    Cell cell = row.createCell(cellIndex++);
                    if (cellObject instanceof Date) {
                        cell.setCellValue((Date) cellObject);
                    } else if (cellObject instanceof Double) {
                        cell.setCellValue((Double) cellObject);
                    } else if (cellObject instanceof Boolean) {
                        cell.setCellValue((Boolean) cellObject);
                    } else if (cellObject instanceof String) {
                        cell.setCellValue((String) cellObject);
                    } else if (cellObject instanceof Number) {
                        cell.setCellValue(Double.valueOf(String.valueOf(cellObject)));
                    }
                }
            }
        }

        //写入文件
        FileOutputStream fileOutputStream = new FileOutputStream(filePath);
        workbook.write(fileOutputStream);
        fileOutputStream.close();
    }

    /**
     * 输出Excel文件到浏览器
     *
     * @param sheetFormatList 工作表集合
     * @param fileName        输出文件名称
     * @param response        响应头
     */
    public void exportByMap(List<ExcelWriteSheetFormat> sheetFormatList, String fileName, HttpServletResponse response) {
        if (sheetFormatList == null) {
            return;
        }
        //创建工作表
        SXSSFWorkbook workbook = new SXSSFWorkbook(1000);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            //遍历写入sheet
            int sheetIndex = 0;
            String sheetName = "";
            for (ExcelWriteSheetFormat sheetFormat : sheetFormatList) {
                Map<String, String> headerMap = sheetFormat.getHeaderMap();
                sheetIndex++;
                sheetName = Common.isNullOrEmpty(sheetFormat.getSheetName()) ? String.valueOf(sheetIndex) : sheetFormat.getSheetName();
                //创建sheet
                Sheet xssfSheet = workbook.createSheet(sheetName);
                //设置默认宽度
                xssfSheet.setDefaultColumnWidth(15);
                //行索引
                int rowIndex = 0;
                //头信息
                if (headerMap == null) {
                    return;
                }
                //冻结表头
                xssfSheet.createFreezePane(0, 1, headerMap.size(), 1);
                //表头样式
                CellStyle cellStyle = getHeaderCellStyle(workbook);
                //创建行
                Row headerRow = xssfSheet.createRow(rowIndex++);
                //设置默认高度
                headerRow.setHeight((short) 350);
                //设置样式
                headerRow.setRowStyle(cellStyle);
                //表头列号
                int headerCellIndex = 0;
                for (String string : headerMap.keySet()) {
                    Cell cell = headerRow.createCell(headerCellIndex++);
                    cell.setCellValue(headerMap.get(string));
                    cell.setCellStyle(cellStyle);
                }
                //数据
                for (Map<String, Object> mapList : sheetFormat.getDataMapList()) {
                    //创建行
                    Row row = xssfSheet.createRow(rowIndex++);
                    int cellIndex = 0;
                    for (String string : headerMap.keySet()) {
                        //创建单元格
                        Cell cell = row.createCell(cellIndex++);
                        if (mapList.containsKey(string)) {
                            Object cellObject = mapList.get(string);
                            if (cellObject instanceof Date) {
                                cell.setCellValue(simpleDateFormat.format(cellObject));
                            } else if (cellObject instanceof Double) {
                                cell.setCellValue((Double) cellObject);
                            } else if (cellObject instanceof Boolean) {
                                cell.setCellValue((Boolean) cellObject);
                            } else if (cellObject instanceof String) {
                                cell.setCellValue((String) cellObject);
                            } else if (cellObject instanceof Number) {
                                cell.setCellValue(Double.valueOf(String.valueOf(cellObject)));
                            } else {
                                cell.setCellValue("");
                            }
                        } else {
                            cell.setCellValue("");
                        }
                    }
                }
            }

            //输出到浏览器
            response.setHeader("content-Type", "application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName + ".xlsx", "utf-8"));
            workbook.write(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public final static void exportByMap(HSSFWorkbook hssfWorkbook, String fileName, final HttpServletResponse response){
        try {
            response.setHeader("content-Type", "application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName + ".xlsx", "utf-8"));
            hssfWorkbook.write(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                hssfWorkbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * 获取表头样式
     *
     * @param workbook 工作表
     * @return
     */
    private CellStyle getHeaderCellStyle(Workbook workbook) {
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setRightBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        cellStyle.setBottomBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        return cellStyle;
    }
}