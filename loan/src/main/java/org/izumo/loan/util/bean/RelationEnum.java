package org.izumo.loan.util.bean;

public enum RelationEnum {
    FATHER(1, "父亲"),

    MOTHER(2, "母亲"),

    MATE(3, "配偶"),

    CHILD(4, "子女");

    private int code;
    private String name;

    private RelationEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static String getName(int code) {
        for (RelationEnum em : values()) {
            if (em.getCode() == code) {
                return em.getName();
            }
        }
        return null;
    }
}
