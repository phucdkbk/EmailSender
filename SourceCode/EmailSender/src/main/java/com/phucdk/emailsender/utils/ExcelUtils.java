/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phucdk.emailsender.utils;

import com.phucdk.emailsender.object.Canho;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Administrator
 */
public class ExcelUtils {

    public static List<Canho> readData(String fileName) throws Exception {
        List<Canho> listCanhos = new ArrayList<>();
        try {
            File myFile = new File(fileName);
            FileInputStream fis = new FileInputStream(myFile);
            XSSFWorkbook myWorkBook = new XSSFWorkbook(fis);
            XSSFSheet mySheet = myWorkBook.getSheetAt(1);

            int startRow = Constants.ROW.START_ROW;
            int endRow = getNumberOfRows(mySheet);            

            for (int i = startRow; i < endRow; i++) {
                String strSTT = getCellValueAsString(i, 0, mySheet);
                if (StringUtils.isNumeric(strSTT)) {
                    Canho aCanho = new Canho();
                    Double doubleSTT = Double.parseDouble(strSTT);
                    aCanho.setSTT(doubleSTT.longValue());
                    aCanho.setSoCanho(getCellValueAsString(i, 1, mySheet));
                    aCanho.setTang(getCellValueAsString(i, 2, mySheet));
                    aCanho.setDientichSudung(getCellValueAsString(i, 3, mySheet));
                    aCanho.setDientichXaydung(getCellValueAsString(i, 4, mySheet));
                    aCanho.setHeso(getCellValueAsString(i, 5, mySheet));
                    aCanho.setGiatriHeso(getCellValueAsString(i, 6, mySheet));
                    aCanho.setDongia(getCellValueAsString(i, 7, mySheet));
                    aCanho.setGiabanThongthuy(getCellValueAsString(i, 8, mySheet));
                    aCanho.setGiatriCanhoHoanthien(getCellValueAsString(i, 9, mySheet));
                    
                    aCanho.setTongGiatriHopdong(getCellValueAsString(i, 10, mySheet));
                    aCanho.setTongGiatriPhainopTheotiendo(getCellValueAsString(i, 11, mySheet));
                    aCanho.setSoDanop(getCellValueAsString(i, 12, mySheet));
                    aCanho.setSoConphainopDotnay(getCellValueAsString(i, 1, mySheet));
                    aCanho.setDot1(getCellValueAsString(i, 1, mySheet));
                    aCanho.setHovatenKhachang(getCellValueAsString(i, 1, mySheet));
                    aCanho.setSoChungminh(getCellValueAsString(i, 1, mySheet));
                    
                    
                    aCanho.setNgaycap(getCellValueAsString(i, 1, mySheet));
                    aCanho.setNoicap(getCellValueAsString(i, 1, mySheet));
                    aCanho.setHokhauThuongtru(getCellValueAsString(i, 1, mySheet));
                    aCanho.setDiachiLienhe(getCellValueAsString(i, 1, mySheet));
                    aCanho.setDienthoai(getCellValueAsString(i, 1, mySheet));
                    aCanho.setEmail(getCellValueAsString(i, 1, mySheet));
                    aCanho.setSotienBangchu(getCellValueAsString(i, 1, mySheet));
                    listCanhos.add(aCanho);
                }
            }
        } catch (FileNotFoundException ex) {
            throw new Exception("Khong tim thay file danh sach can ho: " + fileName, ex);
        }
        return listCanhos;
    }

    private static Cell getCell(int row, int column, XSSFSheet sheet) {
        return CellUtil.getRow(row, sheet).getCell(column);
    }

    public static String getStringCellValue(int row, int column, XSSFSheet sheet) {
        Cell cell = getCell(row, column, sheet);
        if (cell != null) {
            return cell.getStringCellValue().trim();
        } else {
            return null;
        }
    }

    public static String getCellValueAsString(int row, int column, XSSFSheet sheet) {
        Cell cell = getCell(row, column, sheet);
        String cellValue = "";
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_NUMERIC:
                cellValue = String.valueOf(cell.getNumericCellValue());
                break;
            case Cell.CELL_TYPE_BOOLEAN:
                cellValue = String.valueOf(cell.getBooleanCellValue());
                break;
            case Cell.CELL_TYPE_STRING:
                cellValue = String.valueOf(cell.getStringCellValue());
                break;
            case Cell.CELL_TYPE_FORMULA:
                cellValue = String.valueOf(cell.getCellFormula());
                break;
        }
        return cellValue;
    }

    private static boolean isEmptyCell(int row, int column, XSSFSheet sheet) {
        String cellValue = getStringCellValue(row, column, sheet);
        boolean isEmpty = true;
        if (cellValue != null && !"".equals(cellValue.trim())) {
            isEmpty = false;
        }
        return isEmpty;
    }

    private static int getNumberOfRows(XSSFSheet sheet) {
        return sheet.getPhysicalNumberOfRows();
    }

}
