package com.weblite.webmanasystem.constant;

import java.util.List;
import java.util.Map;

/**
 * @author ：Beatrice
 * @date ：Created in 2020/2/19 14:04
 * @Description:
 */
public class ExcelWriteSheetFormat {

    //sheet名称
    private String sheetName;

    //sheet内容（list）
    private List<List<Object>> dataList;

    //sheet内容（map）
    private List<Map<String, Object>> dataMapList;

    //头部信息（字段：字段名）
    private Map<String, String> headerMap;

    @Override
    public String toString() {
        return "ExcelWriteSheetFormat{" +
                "sheetName='" + sheetName + '\'' +
                ", dataList=" + dataList +
                ", dataMapList=" + dataMapList +
                ", headerMap=" + headerMap +
                '}';
    }

    public Map<String, String> getHeaderMap() {
        return headerMap;
    }

    public void setHeaderMap(Map<String, String> headerMap) {
        this.headerMap = headerMap;
    }

    public List<Map<String, Object>> getDataMapList() {
        return dataMapList;
    }

    public void setDataMapList(List<Map<String, Object>> dataMapList) {
        this.dataMapList = dataMapList;
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public List<List<Object>> getDataList() {
        return dataList;
    }

    public void setDataList(List<List<Object>> dataList) {
        this.dataList = dataList;
    }
}