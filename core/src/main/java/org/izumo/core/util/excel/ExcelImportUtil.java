package org.izumo.core.util.excel;

import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCell;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;

public abstract class ExcelImportUtil {
    public void readExcel(String path, String fileName, String fileType) {
        if (fileType.equals("xls")) {
            this.readXls(path + fileName + "." + fileType);
        } else if (fileType.equals("xlsx")) {
            this.readXlsx(path + fileName + "." + fileType);
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
            return String.valueOf((int) hssfCell.getNumericCellValue());
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