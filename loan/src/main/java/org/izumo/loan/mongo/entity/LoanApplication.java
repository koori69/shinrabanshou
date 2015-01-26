package org.izumo.loan.mongo.entity;

import java.util.Date;
import java.util.List;

import org.izumo.loan.mongo.entity.bean.FamilyInformation;
import org.izumo.loan.mongo.entity.bean.Guarantor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
/*(collection = "loan")*/
public class LoanApplication {

    @Id
    private String id;

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
     * 借款额度
     */
    private String loanLimit;

    /**
     * 借款日期
     */
    private String loanDate;

    /**
     * 借款用途
     */
    private String loanPurpose;

    /**
     * 利息周期
     */
    private String interestPeriod;

    /**
     * 从事行业
     */
    private String career;

    /**
     * 抵押物
     */
    private String pawn;

    /**
     * 家庭成员信息
     */
    private List<FamilyInformation> familyInformation;

    /**
     * 担保人
     */
    private List<Guarantor> guarantor;

    /**
     * 约定还款日期
     */
    private String repaymentDate;

    /**
     * 介绍人
     */
    private String introducer;

    /**
     * 经手人
     */
    private String handledBy;

    /**
     * 创建时间
     */
    private Date createTime;

    public LoanApplication() {

    }

    @PersistenceConstructor
    public LoanApplication(String userName, String sex, String age, String marriage, String mobile,
            String presentAddress, String ownership, String permanentAddress, String idNumber, String loanLimit,
            String loanDate, String loanPurpose, String career, List<FamilyInformation> familyInformation,
            List<Guarantor> guarantor) {
        this.userName = userName;
        this.sex = sex;
        this.marriage = marriage;
        this.mobile = mobile;
        this.presentAddress = presentAddress;
        this.ownership = ownership;
        this.permanentAddress = permanentAddress;
        this.idNumber = idNumber;
        this.loanLimit = loanLimit;
        this.loanDate = loanDate;
        this.loanPurpose = loanPurpose;
        this.career = career;
        this.familyInformation = familyInformation;
        this.guarantor = guarantor;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getLoanLimit() {
        return this.loanLimit;
    }

    public void setLoanLimit(String loanLimit) {
        this.loanLimit = loanLimit;
    }

    public String getLoanDate() {
        return this.loanDate;
    }

    public void setLoanDate(String loanDate) {
        this.loanDate = loanDate;
    }

    public String getLoanPurpose() {
        return this.loanPurpose;
    }

    public void setLoanPurpose(String loanPurpose) {
        this.loanPurpose = loanPurpose;
    }

    public String getCareer() {
        return this.career;
    }

    public void setCareer(String career) {
        this.career = career;
    }

    public List<FamilyInformation> getFamilyInformation() {
        return this.familyInformation;
    }

    public void setFamilyInformation(List<FamilyInformation> familyInformation) {
        this.familyInformation = familyInformation;
    }

    public List<Guarantor> getGuarantor() {
        return this.guarantor;
    }

    public void setGuarantor(List<Guarantor> guarantor) {
        this.guarantor = guarantor;
    }

    public String getRepaymentDate() {
        return this.repaymentDate;
    }

    public void setRepaymentDate(String repaymentDate) {
        this.repaymentDate = repaymentDate;
    }

    public String getIntroducer() {
        return this.introducer;
    }

    public void setIntroducer(String introducer) {
        this.introducer = introducer;
    }

    public String getHandledBy() {
        return this.handledBy;
    }

    public void setHandledBy(String handledBy) {
        this.handledBy = handledBy;
    }

    public String getInterestPeriod() {
        return this.interestPeriod;
    }

    public void setInterestPeriod(String interestPeriod) {
        this.interestPeriod = interestPeriod;
    }

    public String getPawn() {
        return this.pawn;
    }

    public void setPawn(String pawn) {
        this.pawn = pawn;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
