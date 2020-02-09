package com.fh.util;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.apache.poi.xssf.usermodel.*;

public class ExcelUtil {






    public static void excelDownload(XSSFWorkbook wirthExcelWB, HttpServletRequest request,HttpServletResponse response, String fileName) {
        OutputStream out = null;
        try {
        	
        	//解决下载文件名中文乱码问题
        	if(request.getHeader("User-agent").toLowerCase().indexOf("firefox")!=-1){
        		fileName = new String(fileName.getBytes("utf-8"),"iso-8859-1");
        	}else{
        		fileName = URLEncoder.encode(fileName,"utf-8");
        	}
        	
            out = response.getOutputStream();
            // 让浏览器识别是什么类型的文件
            response.reset(); // 重点突出
            response.setCharacterEncoding("UTF-8"); // 重点突出
            response.setContentType("application/x-msdownload");// 不同类型的文件对应不同的MIME类型
                                                                // // 重点突出
            // inline在浏览器中直接显示，不提示用户下载
            // attachment弹出对话框，提示用户进行下载保存本地
            // 默认为inline方式
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            wirthExcelWB.write(out);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != out) {
                try {
                    out.close();
                    out = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

   public static XSSFWorkbook generateWorkBook(List li,Class c,String[] headerArr,String[] fieldArr,String sheetName) throws NoSuchFieldException, IllegalAccessException {
        XSSFWorkbook workbook=new XSSFWorkbook();
       XSSFSheet sheet = workbook.createSheet(sheetName);
       XSSFRow row = sheet.createRow(0);
       for (int i = 0; i <headerArr.length ; i++) {
           XSSFCell cell = row.createCell(i);
           cell.setCellValue(headerArr[i]);
       }
       XSSFCellStyle cellStyle = workbook.createCellStyle();
       XSSFDataFormat dataFormat = workbook.createDataFormat();
       cellStyle.setDataFormat(dataFormat.getFormat("yyyy-MM-dd HH:mm:ss"));
       for (int i = 0; i < li.size(); i++) {
           XSSFRow row1 = sheet.createRow(i + 1);
            Object o=li.get(i);
           for (int j = 0; j <fieldArr.length ; j++) {
               XSSFCell cell = row1.createCell(j);
               Field declaredField = c.getDeclaredField(fieldArr[j]);
               Class type = declaredField.getType();
               declaredField.setAccessible(true);
               Object value = declaredField.get(o);
               if(type==int.class||type==Integer.class){
                   cell.setCellValue((Integer)value);
               }else if(type==double.class||type==Double.class){
                   cell.setCellValue((Double)value);
               }else if(type==String.class){
                   cell.setCellValue((String)value);
               }else if(type== Date.class){
                   cell.setCellValue((Date)value);
                   cell.setCellStyle(cellStyle);
               }else{
                   cell.setCellValue((String)value);
               }

           }

       }






        return workbook;
   }


   public static List importexcel(InputStream inputStream,Class c,String[] filedArr) throws IOException, NoSuchFieldException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
              List li=new ArrayList();
             XSSFWorkbook workbook=new XSSFWorkbook(inputStream);
       int numberOfSheets = workbook.getNumberOfSheets();
       for (int i = 0; i < numberOfSheets ; i++) {
           XSSFSheet sheet = workbook.getSheetAt(i);
           int firstRowNum = sheet.getFirstRowNum();
           int lastRowNum = sheet.getLastRowNum();
           for (int j = firstRowNum+1; j <= lastRowNum ; j++) {
               XSSFRow row = sheet.getRow(j);
               Object o = c.getConstructor().newInstance();
               for (int k = 0; k <filedArr.length ; k++) {
                   XSSFCell cell = row.getCell(k);
                   Field declaredField = c.getDeclaredField(filedArr[k]);
                   declaredField.setAccessible(true);
                   Class type = declaredField.getType();
                   if(type==int.class||type==Integer.class){
                       declaredField.set(o,(int)cell.getNumericCellValue());
                   }else if(type==double.class||type==Double.class){
                      declaredField.set(o,cell.getNumericCellValue());
                   }else if(type==String.class){
                     declaredField.set(o,cell.getStringCellValue());
                   }else if(type== Date.class){
                       declaredField.set(o,cell.getDateCellValue());
                   }
               }
                 li.add(o);

           }


       }

       return li;
   }












}
