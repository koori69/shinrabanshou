package org.izumo.loan.mongo.entity.bean;

public class UserInformation {
    /**
     * 姓名
     */
    private String userName;

    /**
     * 性别
     */
    private String sex;

    /**
     * 年龄
     */
    private String age;

    /**
     * 婚史
     */
    private String marriage;

    /**
     * 电话号码
     */
    private String mobile;

    /**
     * 现住址
     */
    private String presentAddress;

    /**
     * 所属权
     */
    private String ownership;

    /**
     * 户籍地址
     */
    private String permanentAddress;

    /**
     * 身份证号码
     */
    private String idNumber;

    /**
     * 从事行业
     */
    private String career;

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSex() {
        return this.sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAge() {
        return this.age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getMarriage() {
        return this.marriage;
    }

    public void setMarriage(String marriage) {
        this.marriage = marriage;
    }

    public String getMobile() {
        return this.mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPresentAddress() {
        return this.presentAddress;
    }

    public void setPresentAddress(String presentAddress) {
        this.presentAddress = presentAddress;
    }

    public String getOwnership() {
        return this.ownership;
    }

    public void setOwnership(String ownership) {
        this.ownership = ownership;
    }

    public String getPermanentAddress() {
        return this.permanentAddress;
    }

    public void setPermanentAddress(String permanentAddress) {
        this.permanentAddress = permanentAddress;
    }

    public String getIdNumber() {
        return this.idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getCareer() {
        return this.career;
    }

    public void setCareer(String career) {
        this.career = career;
    }

}
