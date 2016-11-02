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
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Administrator
 */
public class ExcelUtils {

    private static final Logger log = Logger.getLogger(ExcelUtils.class);

    public static List<Canho> readData(String fileName) throws Exception {

        List<Canho> listCanhos = new ArrayList<>();
        try {
            File myFile = new File(fileName);
            FileInputStream fis = new FileInputStream(myFile);
            XSSFWorkbook myWorkBook = new XSSFWorkbook(fis);

            int startRow = Constants.ROW.START_ROW;
            int endRow = getNumberOfRows(myWorkBook);
            
            
            DecimalFormat formatter = new DecimalFormat("#,###");

            for (int i = startRow; i < endRow; i++) {
                String strSTT = getCellValueAsString(i, 0, myWorkBook);
                if (StringUtils.isNumeric(strSTT)) {
                    Canho aCanho = new Canho();
                    Double doubleSTT = Double.parseDouble(strSTT);
                    aCanho.setSTT(doubleSTT.longValue());
                    aCanho.setSoCanho(getCellValueAsString(i, 1, myWorkBook));
                    if(aCanho.getSoCanho().contains(".")){
                        aCanho.setSoCanho(aCanho.getSoCanho().substring(0, aCanho.getSoCanho().indexOf(".")));
                    }
                    aCanho.setTang(getCellValueAsString(i, 2, myWorkBook));
                    aCanho.setDientichSudung(getCellValueAsString(i, 3, myWorkBook));
                    aCanho.setDientichXaydung(getCellValueAsString(i, 4, myWorkBook));
                    aCanho.setHeso(getCellValueAsString(i, 5, myWorkBook));
                    aCanho.setGiatriHeso(getCellValueAsString(i, 6, myWorkBook));
                    aCanho.setDongia(getCellValueAsString(i, 7, myWorkBook));
                    aCanho.setGiabanThongthuy(getCellValueAsString(i, 8, myWorkBook));
                    aCanho.setGiabanThongthuyNumber((Double) getCellValue(i, 8, myWorkBook));
                    aCanho.setGiatriCanhoHoanthien(getCellValueAsString(i, 9, myWorkBook));
                    aCanho.setGiatriCanhoHoanthienNumber((Double) getCellValue(i, 9, myWorkBook));

                    aCanho.setTongGiatriHopdong(getCellValueAsString(i, 10, myWorkBook));
                    aCanho.setTongGiatriHopdongNumber((Double) getCellValue(i, 10, myWorkBook));
                    aCanho.setTongGiatriPhainopTheotiendo(getCellValueAsString(i, 11, myWorkBook));
                    aCanho.setTongGiatriPhainopTheotiendoNumber((Double) getCellValue(i, 11, myWorkBook));
                    aCanho.setSoDanop(getCellValueAsString(i, 12, myWorkBook));
                    aCanho.setSoDanopNumber((Double) getCellValue(i, 12, myWorkBook));
                    aCanho.setSoConphainopDotnay(getCellValueAsString(i, 13, myWorkBook));
                    aCanho.setSoConphainopDotnayNumber((Double) getCellValue(i, 13, myWorkBook));
                    aCanho.setDot1(getCellValueAsString(i, 14, myWorkBook));
                    aCanho.setHovatenKhachang(getCellValueAsString(i, 16, myWorkBook));
                    aCanho.setSoChungminh(getCellValueAsString(i, 17, myWorkBook));

                    aCanho.setNgaycap(getCellValueAsString(i, 18, myWorkBook));
                    aCanho.setNoicap(getCellValueAsString(i, 19, myWorkBook));
                    aCanho.setHokhauThuongtru(getCellValueAsString(i, 20, myWorkBook));
                    aCanho.setDiachiLienhe(getCellValueAsString(i, 21, myWorkBook));
                    aCanho.setDienthoai(getCellValueAsString(i, 22, myWorkBook));
                    aCanho.setEmail(getCellValueAsString(i, 23, myWorkBook));
                    aCanho.setSotienBangchu(getCellValueAsString(i, 24, myWorkBook));
                    
                    
                    aCanho.setGiabanThongthuy(formatter.format(aCanho.getGiabanThongthuyNumber()));
                    aCanho.setGiatriCanhoHoanthien(formatter.format(aCanho.getGiatriCanhoHoanthienNumber()));
                    aCanho.setTongGiatriHopdong(formatter.format(aCanho.getTongGiatriHopdongNumber()));
                    aCanho.setTongGiatriPhainopTheotiendo(formatter.format(aCanho.getTongGiatriPhainopTheotiendoNumber()));
                    aCanho.setSoConphainopDotnay(formatter.format(aCanho.getSoConphainopDotnayNumber()));
                    aCanho.setSotienBangchu(ReadNumber.numberToString(aCanho.getSoConphainopDotnayNumber()));
                    //aCanho.setSotienBangchu(ReadNumber.numberToString(aCanho.getSoConphainopDotnayNumber().longValue()));
                    
                    
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

    public static String getCellValueAsString(int row, int column, XSSFWorkbook myWorkBook) {
        XSSFSheet mySheet = myWorkBook.getSheetAt(1);
        Cell cell = getCell(row, column, mySheet);
        String strCellValue = "";
        FormulaEvaluator evaluator = myWorkBook.getCreationHelper().createFormulaEvaluator();
        if (cell != null) {
            CellValue cellValue = null;
            try {
                cellValue = evaluator.evaluate(cell);
            } catch (Exception ex) {
                log.error("Error when evaluate cell value", ex);
            }

            if (cellValue != null) {
                switch (cellValue.getCellType()) {
                    case Cell.CELL_TYPE_NUMERIC:
                        strCellValue = String.valueOf(cellValue.getNumberValue());
                        break;
                    case Cell.CELL_TYPE_BOOLEAN:
                        strCellValue = String.valueOf(cellValue.getBooleanValue());
                        break;
                    case Cell.CELL_TYPE_STRING:
                        strCellValue = String.valueOf(cellValue.getStringValue());
                        break;
                    case Cell.CELL_TYPE_FORMULA:
                        //strCellValue = String.valueOf(cellValue.get());
                        break;
                }
            }

        }
        return strCellValue;
    }

    public static Object getCellValue(int row, int column, XSSFWorkbook myWorkBook) {
        XSSFSheet mySheet = myWorkBook.getSheetAt(1);
        Cell cell = getCell(row, column, mySheet);
        Object cellValueObject = "";
        FormulaEvaluator evaluator = myWorkBook.getCreationHelper().createFormulaEvaluator();
        if (cell != null) {
            CellValue cellValue = null;
            try {
                cellValue = evaluator.evaluate(cell);
            } catch (Exception ex) {
                log.error("Error when evaluate cell value", ex);
            }

            if (cellValue != null) {
                switch (cellValue.getCellType()) {
                    case Cell.CELL_TYPE_NUMERIC:
                        cellValueObject = cellValue.getNumberValue();
                        break;
                    case Cell.CELL_TYPE_BOOLEAN:
                        cellValueObject = cellValue.getBooleanValue();
                        break;
                    case Cell.CELL_TYPE_STRING:
                        cellValueObject = cellValue.getStringValue();
                        break;
                    case Cell.CELL_TYPE_FORMULA:
                        //strCellValue = cellValue.getErrorValue();
                        break;
                }
            }

        }
        return cellValueObject;
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

    private static int getNumberOfRows(XSSFWorkbook myWorkBook) {
        XSSFSheet sheet = myWorkBook.getSheetAt(1);
        return sheet.getPhysicalNumberOfRows();
    }

}
