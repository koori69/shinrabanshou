package org.izumo.core.util.excel;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.izumo.core.util.excel.bean.ExcelDefineObject;
import org.izumo.core.util.excel.bean.ExcelSheetDefineObject;
import org.izumo.core.util.excel.bean.ExcelSubDefineObject;
import org.izumo.core.web.annotation.ExcelColumn;
import org.izumo.core.web.annotation.ExcelColumns;
import org.izumo.core.web.annotation.ExcelObjectColumn;
import org.izumo.core.web.annotation.ExcelObjectColumns;
import org.izumo.core.web.annotation.ExcelSheet;
import org.izumo.core.web.annotation.ExcelSheets;

public class ExcelExportUtil {

    public static void exportExcel(List datas, String[] selcols, String action, HttpServletResponse httpResponse,
            String filename) {
        if (null == datas || datas.size() < 1) {
            return;
        }

        OutputStream os = null;
        try {
            HSSFWorkbook workbook = new HSSFWorkbook();

            httpResponse.reset();
            httpResponse.addHeader("Content-Disposition",
                    "attachment;filename=" + new String(filename.getBytes("UTF-8"), "ISO8859-1"));
            os = httpResponse.getOutputStream();

            makeExcelSheet(workbook, "main", datas, selcols, action);

            workbook.write(os);
            os.flush();
        } catch (Exception e) {

        } finally {
            if (null != os) {
                try {
                    os.close();
                } catch (IOException e) {

                }
            }
        }
    }

    private static void makeExcelSheet(HSSFWorkbook workbook, String sheetname, List datas, String[] selcols,
            String action) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException,
            SecurityException {
        if (null == datas || datas.size() < 1) {
            return;
        }

        HSSFSheet sheet = workbook.createSheet(sheetname);
        Object obj = datas.get(0);
        List<ExcelDefineObject> fieldlist = getExcelFieldList(obj, selcols, action);
        List<ExcelSubDefineObject> subfieldlist = getExcelSubFieldList(obj, selcols, action);
        List<ExcelSheetDefineObject> sheetlist = getExcelSheetList(obj, action);

        HSSFRow row = null;
        int rowsite = 0;

        row = sheet.createRow(rowsite);
        rowsite++;
        makeExcelHead(row, fieldlist);

        int colsite = 0;
        Object subobj = null;
        List subdata = null;
        String sheetkey = null;
        Field field = null;
        int sheetnamesite = 1;
        for (Object lineobj : datas) {
            if (rowsite > 65535) {
                sheet = workbook.createSheet(sheetname + sheetnamesite);
                rowsite = 0;

                row = sheet.createRow(rowsite);
                rowsite++;
                makeExcelHead(row, fieldlist);
            }

            row = sheet.createRow(rowsite);
            rowsite++;
            colsite = 0;
            colsite = makeExcelLine(row, colsite, lineobj, fieldlist);
            if (null != subfieldlist && subfieldlist.size() > 0) {
                for (ExcelSubDefineObject subde : subfieldlist) {
                    field = subde.getField();
                    field.setAccessible(true);
                    subobj = field.get(lineobj);
                    colsite = makeExcelLine(row, colsite, subobj, subde.getDefinelist());
                }
            }

            if (null != sheetlist && sheetlist.size() > 0) {
                for (ExcelSheetDefineObject esdo : sheetlist) {
                    field = esdo.getDatafield();
                    field.setAccessible(true);
                    subobj = field.get(lineobj);
                    if (null != subobj) {
                        subdata = (List) subobj;
                    }
                    field = esdo.getNamefield();
                    field.setAccessible(true);
                    subobj = field.get(lineobj);
                    sheetkey = convertObj(subobj, null);

                    makeExcelSheet(workbook, sheetkey, subdata, selcols, action);
                }
            }
        }
    }

    private static List<ExcelDefineObject> getExcelFieldList(Object obj, String[] selcols, String action) {
        List<ExcelDefineObject> fieldlist = new ArrayList<ExcelDefineObject>();
        Field[] fields = obj.getClass().getDeclaredFields();
        if (null != fields && fields.length > 0) {
            ExcelColumn elanno = null;
            ExcelColumn[] elannos = null;
            ExcelColumns elsanno = null;
            ExcelDefineObject edo = null;

            Map<String, String> selcolmap = new HashMap<String, String>();
            if (null != selcols && selcols.length > 0) {
                for (String selcol : selcols) {
                    selcolmap.put(selcol, selcol);
                }
            }

            String isexist = null;

            for (Field field : fields) {
                elanno = field.getAnnotation(ExcelColumn.class);
                if (null != elanno && (elanno.action().equals(action) || "".equals(elanno.action()))) {
                    isexist = selcolmap.get(elanno.name());
                    if (null != isexist) {
                        edo = makeExcelDefineObject(elanno, field);
                        fieldlist.add(edo);
                    }
                }
                elsanno = field.getAnnotation(ExcelColumns.class);
                if (null != elsanno) {
                    elannos = elsanno.excelColumns();
                    for (ExcelColumn anno : elannos) {
                        if (null != anno && (anno.action().equals(action) || "".equals(anno.action()))) {
                            edo = makeExcelDefineObject(anno, field);
                            fieldlist.add(edo);
                        }
                    }
                }
            }
        }
        return fieldlist;
    }

    private static List<ExcelSubDefineObject> getExcelSubFieldList(Object obj, String[] selcols, String action)
            throws IllegalArgumentException, IllegalAccessException {
        List<ExcelSubDefineObject> fieldlist = new ArrayList<ExcelSubDefineObject>();
        Field[] fields = obj.getClass().getDeclaredFields();
        if (null != fields && fields.length > 0) {
            ExcelObjectColumn elanno = null;
            ExcelObjectColumn[] elannos = null;
            ExcelObjectColumns elsanno = null;
            ExcelSubDefineObject edo = null;
            List<ExcelDefineObject> edolist = null;
            Object subobj = null;
            for (Field field : fields) {
                elanno = field.getAnnotation(ExcelObjectColumn.class);
                if (null != elanno && (elanno.action().equals(action) || "".equals(elanno.action()))) {
                    edo = new ExcelSubDefineObject();
                    edo.setField(field);
                    subobj = field.get(obj);
                    if (null != subobj) {
                        edolist = getExcelFieldList(subobj, selcols, action);
                        edo.setDefinelist(edolist);
                    }
                    fieldlist.add(edo);
                }
                elsanno = field.getAnnotation(ExcelObjectColumns.class);
                if (null != elsanno) {
                    elannos = elsanno.excelObjectColumns();
                    for (ExcelObjectColumn anno : elannos) {
                        if (null != anno && (anno.action().equals(action) || "".equals(anno.action()))) {
                            edo = new ExcelSubDefineObject();
                            edo.setField(field);
                            subobj = field.get(obj);
                            if (null != subobj) {
                                edolist = getExcelFieldList(subobj, selcols, action);
                                edo.setDefinelist(edolist);
                            }
                        }
                    }
                }
            }
        }
        return fieldlist;
    }

    private static List<ExcelSheetDefineObject> getExcelSheetList(Object obj, String action)
            throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
        List<ExcelSheetDefineObject> fieldlist = new ArrayList<ExcelSheetDefineObject>();
        Field[] fields = obj.getClass().getDeclaredFields();
        if (null != fields && fields.length > 0) {
            ExcelSheet elanno = null;
            ExcelSheet[] elannos = null;
            ExcelSheets elsanno = null;
            ExcelSheetDefineObject esdo = null;
            String namefield = null;
            Field sheetfield = null;
            for (Field field : fields) {
                elanno = field.getAnnotation(ExcelSheet.class);
                if (null != elanno && (elanno.action().equals(action) || "".equals(elanno.action()))) {
                    esdo = new ExcelSheetDefineObject();
                    esdo.setDatafield(field);

                    namefield = elanno.namefield();
                    sheetfield = obj.getClass().getDeclaredField(namefield);
                    esdo.setNamefield(sheetfield);
                    fieldlist.add(esdo);
                }
                elsanno = field.getAnnotation(ExcelSheets.class);
                if (null != elsanno) {
                    elannos = elsanno.excelSheets();
                    for (ExcelSheet anno : elannos) {
                        if (null != anno && (anno.action().equals(action) || "".equals(anno.action()))) {
                            esdo = new ExcelSheetDefineObject();
                            esdo.setDatafield(field);

                            namefield = anno.namefield();
                            sheetfield = obj.getClass().getDeclaredField(namefield);
                            esdo.setNamefield(sheetfield);
                            fieldlist.add(esdo);
                        }
                    }
                }
            }
        }
        return fieldlist;
    }

    private static ExcelDefineObject makeExcelDefineObject(ExcelColumn elanno, Field field) {
        ExcelDefineObject edo = new ExcelDefineObject();
        edo.setField(field);
        edo.setName(elanno.name());
        edo.setDatetype(elanno.datetype());
        edo.initValues(elanno.statevalue());
        return edo;
    }

    private static void makeExcelHead(HSSFRow row, List<ExcelDefineObject> fieldlist) {
        HSSFCell cell = null;
        int colsite = 0;
        for (ExcelDefineObject edo : fieldlist) {
            cell = row.createCell(colsite);
            colsite++;
            cell.setCellValue(edo.getName());
        }
    }

    private static int makeExcelLine(HSSFRow row, int start, Object obj, List<ExcelDefineObject> fieldlist)
            throws IllegalArgumentException, IllegalAccessException {
        Object col = null;
        String str = null;
        HSSFCell cell = null;
        int colsite = start;
        Field field = null;
        for (ExcelDefineObject edo : fieldlist) {
            field = edo.getField();
            field.setAccessible(true);
            col = field.get(obj);
            str = convertObj(col, edo);
            str = edo.getValue(str);

            cell = row.createCell(colsite);
            colsite++;
            cell.setCellValue(str);
        }
        return colsite;
    }

    private static String convertObj(Object obj, ExcelDefineObject edo) {
        if (null == obj) {
            return "";
        }
        if (obj instanceof String) {
            return (String) obj;
        } else if (obj instanceof Date) {
            SimpleDateFormat sf = new SimpleDateFormat(edo.getDatetype());
            return sf.format((Date) obj);
        } else {
            return String.valueOf(obj);
        }
    }
}
