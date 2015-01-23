package org.izumo.core.util.excel.bean;

import java.lang.reflect.Field;
import java.util.List;

public class ExcelSubDefineObject {

    private Field field;

    private List<ExcelDefineObject> definelist;

    public Field getField() {
        return this.field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public List<ExcelDefineObject> getDefinelist() {
        return this.definelist;
    }

    public void setDefinelist(List<ExcelDefineObject> definelist) {
        this.definelist = definelist;
    }
}
