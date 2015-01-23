package org.izumo.core.util.excel.bean;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class ExcelDefineObject {

    private Field field;

    private String name;

    private String datetype;

    private final Map<String, String> valueMap = new HashMap<String, String>();

    public void initValues(String values) {
        if (null == values || values.length() < 1) {
            return;
        }
        String[] valuearray = values.split(",");
        String[] keyvalue = null;
        if (null != valuearray && valuearray.length > 0) {
            for (String value : valuearray) {
                keyvalue = value.split(":");
                this.valueMap.put(keyvalue[0], keyvalue[1]);
            }
        }
    }

    public String getValue(String key) {
        if (this.valueMap.size() < 1) {
            return key;
        }

        String value = this.valueMap.get(key);
        if (null != value) {
            return value;
        } else {
            if (null != this.valueMap.get("x")) {
                return this.valueMap.get("x");
            }
        }
        return key;
    }

    public Field getField() {
        return this.field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDatetype() {
        return this.datetype;
    }

    public void setDatetype(String datetype) {
        this.datetype = datetype;
    }
}
