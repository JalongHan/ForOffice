package com.gidea.foroffice.activity;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * ━━━━━━神兽出没━━━━━━
 * 　　　┏┓　　┏┓
 * 　　┏┛┻━━━┛┻┓
 * 　　┃　　　　　　┃
 * 　　┃　　　━　　┃
 * 　　┃　┳┛　┗┳　┃
 * 　　┃　　　　　　┃
 * 　　┃　　　┻　　　┃
 * 　　┃　　　　　　┃
 * 　　┗━┓　　　┏━┛Code is far away from bug with the animal protecting
 * 　　　　┃　　　┃    神兽保佑,代码无bug
 * 　　　　┃　　　┃
 * 　　　　┃　　　┗━━━┓
 * 　　　　┃　　　　　　┣┓
 * 　　　　┃　　　　　　┏┛
 * 　　　　┗┓┓┏━┳┓┏┛
 * 　　　　　┃┫┫　┃┫┫
 * 　　　　　┗┻┛　┗┻┛
 * 作者:jalong Han
 * 邮箱:hjl999@126.com
 * 时间:2017/3/16
 * 功能:
 */

public class ExcelContent {
    private String fileName;
    public String getFileName(){
        return fileName;
    }
    public void setFileName(String value){
        fileName = value;
    }

    class SheetData{
        private List<ArrayList<String>> cellsData = new ArrayList<ArrayList<String>>();
        public String getCellData(int row,int col){
            if (row < cellsData.size()){
                List<String> rowData = cellsData.get(row);
                if (col < rowData.size()){
                    return rowData.get(col);
                }
            }
            return null;
        }

        public void setRowData(ArrayList<String> value){
            cellsData.add(value);
        }

        public void showData(){
            for(int row = 0;row < cellsData.size();row ++){
                for(int col = 0; col < cellsData.get(row).size(); col ++){
                    Log.i("Value at " + row + "," + col, getCellData(row,col));
                }
            }
        }

        public ArrayList<String> getData(){
            ArrayList<String> data = new ArrayList<String>();

            for(int row = 0;row < cellsData.size();row ++){
                String rowData = "row " + row + ":";
                for(int col = 0; col < cellsData.get(row).size(); col ++){
                    if (col != cellsData.get(row).size() - 1){
                        rowData += getCellData(row,col) + ",";
                    }
                    else{
                        rowData += getCellData(row,col);
                    }
                }

                data.add(rowData);
            }

            return data;
        }
    }

    private List<String> sheetNames = new ArrayList<String>();
    public List<String> getSheetNames(){
        return sheetNames;
    }
    public String getSheetName(int i){
        if (i < sheetNames.size()){
            return sheetNames.get(i);
        }
        else{
            return "";
        }
    }
    public int getSheetsCount(){
        return sheetNames.size();
    }
    public void setSheetNames(List<String> value){
        sheetNames = value;
    }
    public void setSheetName(String value){
        sheetNames.add(value);
    }

    //sheet,row,col data
    List<SheetData> wbData = new ArrayList<SheetData>();
    SheetData getSheetData(int i){
        return wbData.get(i);
    }
    public void setSheetData(List<ArrayList<String>> data){
        SheetData sheetData = new SheetData();
        for (int i = 0;i < data.size(); i ++){
            sheetData.setRowData(data.get(i));
        }

        setSheetData(sheetData);
    }
    public void setSheetData(SheetData value){
        wbData.add(value);
    }

    public String getCellData(int sheet,int row,int col){
        if (sheet < wbData.size()){
            SheetData sheetData = getSheetData(sheet);
            return sheetData.getCellData(row, col);
        }
        else{
            return null;
        }
    }

    public void showContent(){
        Log.i("FileName:",fileName);
        int sheetCount = getSheetsCount();
        Log.i("Sheet Count:", String.valueOf(sheetCount));
        for(int i = 0; i < sheetCount;i ++){
            Log.i("Sheet Name:", getSheetName(i));
            getSheetData(i).showData();
        }
    }

    public ArrayList<String> getContentData(){
        ArrayList<String> data = new ArrayList<String>();

        for(int i = 0;i < wbData.size();i ++){
            data.add(getSheetName(i));
            data.addAll(wbData.get(i).getData());
        }

        return data;
    }
}
