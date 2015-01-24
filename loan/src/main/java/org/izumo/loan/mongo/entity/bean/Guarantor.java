package org.izumo.loan.mongo.entity.bean;

import java.util.List;

public class Guarantor {

    /**
     * 担保人基本情况
     */
    private UserInformation userInformation;

    /**
     * 担保人家庭成员信息
     */
    private List<FamilyInformation> guarantorFamilyInformation;

    public UserInformation getUserInformation() {
        return this.userInformation;
    }

    public void setUserInformation(UserInformation userInformation) {
        this.userInformation = userInformation;
    }

    public List<FamilyInformation> getGuarantorFamilyInformation() {
        return this.guarantorFamilyInformation;
    }

    public void setGuarantorFamilyInformation(List<FamilyInformation> guarantorFamilyInformation) {
        this.guarantorFamilyInformation = guarantorFamilyInformation;
    }

}
