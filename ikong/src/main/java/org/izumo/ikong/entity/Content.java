package org.izumo.ikong.entity;

public class Content {

    private Integer Id;

    private Integer T = 0;

    private String C;

    public Integer getId() {
        return this.Id;
    }

    public void setId(Integer id) {
        this.Id = id;
    }

    public Integer getT() {
        return this.T;
    }

    public void setT(Integer t) {
        this.T = t;
    }

    public String getC() {
        return this.C;
    }

    public void setC(String c) {
        this.C = c;
    }
}
