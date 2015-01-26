package org.izumo.core.util.excel;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;

public abstract class ExcelImportUtil {
    public void readExcel(String filePath) {
        if (null == filePath || filePath.isEmpty()) {
            return;
        }

        if (filePath.endsWith("xlsx")) {
            this.readXlsx(filePath);
        } else if (filePath.endsWith("xls")) {
            this.readXls(filePath);
        } else {
            System.out.println("您输入的excel格式不正确");
        }
    }

    /** 
     * 读取xls文件
     * <功能详细描述>
     * @param path
     * @throws IOException
     */
    public abstract void readXls(String path);

    @SuppressWarnings("static-access")
    protected String getValue(HSSFCell hssfCell) {
        if (hssfCell.getCellType() == hssfCell.CELL_TYPE_BOOLEAN) {
            return String.valueOf(hssfCell.getBooleanCellValue());
        } else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_NUMERIC) {
            if (HSSFDateUtil.isCellDateFormatted(hssfCell)) {
                Date d = hssfCell.getDateCellValue();
                DateFormat formater = new SimpleDateFormat("yyyy/MM/dd");
                return formater.format(d);
            } else {
                return String.valueOf((long) hssfCell.getNumericCellValue());
            }
        } else {
            return String.valueOf(hssfCell.getStringCellValue());
        }
    }

    /** 
     * 读取xlsx文件
     * <功能详细描述>
     * @param path
     * @throws IOException
     * @throws InvalidFormatException
     */
    public abstract void readXlsx(String path);

    @SuppressWarnings("static-access")
    protected String getValue(XSSFCell xssfCell) {
        if (xssfCell.getCellType() == xssfCell.CELL_TYPE_BOOLEAN) {
            return String.valueOf(xssfCell.getBooleanCellValue());
        } else if (xssfCell.getCellType() == xssfCell.CELL_TYPE_NUMERIC) {
            return String.valueOf((int) xssfCell.getNumericCellValue());
        } else {
            return String.valueOf(xssfCell.getStringCellValue());
        }
    }

}