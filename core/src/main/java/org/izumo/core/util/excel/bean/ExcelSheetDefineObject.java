package org.izumo.core.util.excel.bean;

import java.lang.reflect.Field;

public class ExcelSheetDefineObject {

    private Field datafield;

    private Field namefield;

    public Field getDatafield() {
        return this.datafield;
    }

    public void setDatafield(Field datafield) {
        this.datafield = datafield;
    }

    public Field getNamefield() {
        return this.namefield;
    }

    public void setNamefield(Field namefield) {
        this.namefield = namefield;
    }
}
