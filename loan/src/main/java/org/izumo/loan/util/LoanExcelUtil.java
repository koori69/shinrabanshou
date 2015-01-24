package org.izumo.loan.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.izumo.core.util.excel.ExcelImportUtil;
import org.izumo.loan.mongo.entity.LoanApplication;
import org.izumo.loan.mongo.entity.bean.FamilyInformation;
import org.izumo.loan.mongo.entity.bean.Guarantor;
import org.izumo.loan.mongo.entity.bean.UserInformation;
import org.izumo.loan.util.bean.RelationEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoanExcelUtil extends ExcelImportUtil {

    private LoanApplication obj = null;

    private final List<Guarantor> guarantors = new ArrayList<Guarantor>();

    private final Map<String, FamilyInformation> loanFamily = new LinkedHashMap<String, FamilyInformation>();

    private final Map<String, FamilyInformation> guarFamily = new LinkedHashMap<String, FamilyInformation>();

    private final Map<String, FamilyInformation> guarFamily2 = new LinkedHashMap<String, FamilyInformation>();

    private static final Logger logger = LoggerFactory.getLogger(LoanExcelUtil.class);

    @Override
    public void readXls(String path) {
        InputStream is;
        try {
            is = new FileInputStream(path);
            HSSFWorkbook hssfWorkbook;
            try {
                hssfWorkbook = new HSSFWorkbook(is);
                this.obj = new LoanApplication();

                // 循环工作表Sheet
                for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); ++numSheet) {
                    HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
                    if (hssfSheet == null) {
                        continue;
                    }

                    // 循环行Row
                    for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); ++rowNum) {

                        HSSFRow hssfRow = hssfSheet.getRow(rowNum);
                        if (hssfRow == null) {
                            continue;
                        }

                        // 循环列Cell
                        for (int cellNum = 1; cellNum < hssfRow.getLastCellNum(); ++cellNum) {
                            HSSFCell hssfCell = hssfRow.getCell(cellNum);
                            if (hssfCell == null) {
                                continue;
                            }
                            if (hssfCell.getStringCellValue() != null && !hssfCell.getStringCellValue().isEmpty()) {
                                this.setObj(rowNum, cellNum, this.getValue(hssfCell));
                            }
                        }
                    }
                }
                this.setValues();
            } catch (IOException e) {
                logger.error("HSSFWorkbook err:", e);
            }
        } catch (FileNotFoundException e) {
            logger.error("InputStream err:", e);
        }
    }

    @Override
    public void readXlsx(String path) {
        InputStream stream;
        try {
            stream = new FileInputStream(path);
            this.obj = new LoanApplication();
            XSSFWorkbook xssfWorkbook;
            try {
                xssfWorkbook = new XSSFWorkbook(stream);

                // 循环工作表Sheet
                for (int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); ++numSheet) {
                    XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
                    if (xssfSheet == null) {
                        continue;
                    }

                    // 循环行Row
                    for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
                        LoanApplication obj = new LoanApplication();
                        XSSFRow xssfRow = xssfSheet.getRow(rowNum);
                        if (xssfRow == null) {
                            continue;
                        }

                        // 循环列Cell
                        for (int cellNum = 1; cellNum < xssfRow.getLastCellNum(); ++cellNum) {
                            XSSFCell xssfCell = xssfRow.getCell(cellNum);
                            if (xssfCell == null) {
                                continue;
                            }
                            if (xssfCell.getStringCellValue() != null && !xssfCell.getStringCellValue().isEmpty()) {
                                this.setObj(rowNum, cellNum, this.getValue(xssfCell));
                            }
                        }
                    }
                }
                this.setValues();
            } catch (IOException e) {
                logger.error("XSSFWorkbook err:", e);
            }
        } catch (FileNotFoundException e) {
            logger.error("InputStream err:", e);
        }
    }

    private void setValues() {
        List<FamilyInformation> familyInformations = null;
        if (!this.loanFamily.isEmpty()) {
            familyInformations = new ArrayList<FamilyInformation>();
            for (String key : this.loanFamily.keySet()) {
                familyInformations.add(this.loanFamily.get(key));
            }
            this.obj.setFamilyInformation(familyInformations);
        }
        if (this.guarantors.size() > 0) {
            for (int i = 0; i < this.guarantors.size(); ++i) {
                if (0 == i && !this.guarFamily.isEmpty()) {
                    List<FamilyInformation> guaramtorFamilyInformations = new ArrayList<FamilyInformation>();
                    for (String key : this.guarFamily.keySet()) {
                        guaramtorFamilyInformations.add(this.guarFamily.get(key));
                    }
                    this.guarantors.get(i).setGuarantorFamilyInformation(guaramtorFamilyInformations);
                }
                if (1 == i && !this.guarFamily2.isEmpty()) {
                    List<FamilyInformation> guaramtorFamilyInformations = new ArrayList<FamilyInformation>();
                    for (String key : this.guarFamily2.keySet()) {
                        guaramtorFamilyInformations.add(this.guarFamily.get(key));
                    }
                    this.guarantors.get(i).setGuarantorFamilyInformation(guaramtorFamilyInformations);
                }
            }
            this.obj.setGuarantor(this.guarantors);
        }
    }

    private void setObj(int i, int j, String value) {
        FamilyInformation family = null;
        Guarantor guarantor = null;
        UserInformation userInformation = null;
        boolean flag = false;
        switch (i) {
        case 1: {
            switch (j) {
            case 2:
                this.obj.setUserName(value);
                break;
            case 4:
                this.obj.setSex(value);
                break;
            case 6:
                this.obj.setAge(value);
                break;
            case 8:
                this.obj.setMarriage(value);
                break;
            case 10:
                this.obj.setMobile(value);
                break;
            default:
                break;
            }
        }
            break;
        case 2: {
            switch (j) {
            case 2:
                this.obj.setPresentAddress(value);
                break;
            case 10:
                this.obj.setOwnership(value);
                break;
            default:
                break;
            }
        }
            break;
        case 3: {
            switch (j) {
            case 2:
                this.obj.setPermanentAddress(value);
                break;
            case 10:
                this.obj.setIdNumber(value);
                break;
            default:
                break;
            }
        }
            break;
        case 4: {
            switch (j) {
            case 2:
                this.obj.setLoanLimit(value);
                break;
            case 10:
                this.obj.setLoanDate(value);
                break;
            default:
                break;
            }
        }
            break;
        case 5: {
            switch (j) {
            case 2:
                this.obj.setLoanPurpose(value);
                break;
            case 10:
                this.obj.setInterestPeriod(value);
                break;
            default:
                break;
            }
        }
            break;
        case 6:
            if (j == 2) {
                this.obj.setCareer(value);
            }
            break;
        case 8: {
            if (this.loanFamily.containsKey(RelationEnum.FATHER.getName())) {
                family = this.loanFamily.get(RelationEnum.FATHER.getName());
            } else {
                family = new FamilyInformation();
                family.setRelationship(RelationEnum.FATHER.getName());
            }
            switch (j) {
            case 2:
                family.setUserName(value);
                break;
            case 3:
                family.setSex(value);
                break;
            case 4:
                family.setAge(value);
                break;
            case 5:
                family.setDetails(value);
                break;
            default:
                break;
            }
            this.loanFamily.put(RelationEnum.FATHER.getName(), family);
        }
            break;
        case 9: {
            if (this.loanFamily.containsKey(RelationEnum.MOTHER.getName())) {
                family = this.loanFamily.get(RelationEnum.MOTHER.getName());
            } else {
                family = new FamilyInformation();
                family.setRelationship(RelationEnum.MOTHER.getName());
            }
            switch (j) {
            case 2:
                family.setUserName(value);
                break;
            case 3:
                family.setSex(value);
                break;
            case 4:
                family.setAge(value);
                break;
            case 5:
                family.setDetails(value);
                break;
            default:
                break;
            }
            this.loanFamily.put(RelationEnum.MOTHER.getName(), family);
        }
            break;
        case 10: {
            if (this.loanFamily.containsKey(RelationEnum.MATE.getName())) {
                family = this.loanFamily.get(RelationEnum.MATE.getName());
            } else {
                family = new FamilyInformation();
                family.setRelationship(RelationEnum.MATE.getName());
            }
            switch (j) {
            case 2:
                family.setUserName(value);
                break;
            case 3:
                family.setSex(value);
                break;
            case 4:
                family.setAge(value);
                break;
            case 5:
                family.setDetails(value);
                break;
            default:
                break;
            }
            this.loanFamily.put(RelationEnum.MATE.getName(), family);
        }
            break;
        case 11: {
            if (this.loanFamily.containsKey(RelationEnum.CHILD.getName())) {
                family = this.loanFamily.get(RelationEnum.CHILD.getName());
            } else {
                family = new FamilyInformation();
                family.setRelationship(RelationEnum.CHILD.getName());
            }
            switch (j) {
            case 2:
                family.setUserName(value);
                break;
            case 3:
                family.setSex(value);
                break;
            case 4:
                family.setAge(value);
                break;
            case 5:
                family.setDetails(value);
                break;
            default:
                break;
            }
            this.loanFamily.put(RelationEnum.CHILD.getName(), family);
        }
            break;
        case 13:
            guarantor = null;
            userInformation = null;
            if (this.guarantors.size() > 0) {
                flag = true;
                guarantor = this.guarantors.get(0);
                if (null == guarantor.getUserInformation()) {
                    userInformation = new UserInformation();
                } else {
                    userInformation = guarantor.getUserInformation();
                }
            } else {
                guarantor = new Guarantor();
                userInformation = new UserInformation();
            }
            {
                switch (j) {
                case 2:
                    userInformation.setUserName(value);
                    break;
                case 4:
                    userInformation.setSex(value);
                    break;
                case 6:
                    userInformation.setAge(value);
                    break;
                case 8:
                    userInformation.setMarriage(value);
                    break;
                case 10:
                    userInformation.setMobile(value);
                    break;
                default:
                    break;
                }
            }
            guarantor.setUserInformation(userInformation);
            if (flag) {
                this.guarantors.set(0, guarantor);
            } else {
                this.guarantors.add(guarantor);
            }
            break;
        case 14:
            guarantor = null;
            userInformation = null;
            if (this.guarantors.size() > 0) {
                flag = true;
                guarantor = this.guarantors.get(0);
                if (null == guarantor.getUserInformation()) {
                    userInformation = new UserInformation();
                } else {
                    userInformation = guarantor.getUserInformation();
                }
            } else {
                guarantor = new Guarantor();
                userInformation = new UserInformation();
            }
            {
                switch (j) {
                case 2:
                    userInformation.setPresentAddress(value);
                    break;
                case 4:
                    userInformation.setOwnership(value);
                    break;
                default:
                    break;
                }
            }
            guarantor.setUserInformation(userInformation);
            if (flag) {
                this.guarantors.set(0, guarantor);
            } else {
                this.guarantors.add(guarantor);
            }
            break;
        case 15:
            guarantor = null;
            userInformation = null;
            if (this.guarantors.size() > 0) {
                flag = true;
                guarantor = this.guarantors.get(0);
                if (null == guarantor.getUserInformation()) {
                    userInformation = new UserInformation();
                } else {
                    userInformation = guarantor.getUserInformation();
                }
            } else {
                guarantor = new Guarantor();
                userInformation = new UserInformation();
            }
            {
                switch (j) {
                case 2:
                    userInformation.setIdNumber(value);
                    break;
                default:
                    break;
                }
            }
            guarantor.setUserInformation(userInformation);
            if (flag) {
                this.guarantors.set(0, guarantor);
            } else {
                this.guarantors.add(guarantor);
            }
            break;
        case 16:
            guarantor = null;
            userInformation = null;
            if (this.guarantors.size() > 0) {
                flag = true;
                guarantor = this.guarantors.get(0);
                if (null == guarantor.getUserInformation()) {
                    userInformation = new UserInformation();
                } else {
                    userInformation = guarantor.getUserInformation();
                }
            } else {
                guarantor = new Guarantor();
                userInformation = new UserInformation();
            }
            {
                switch (j) {
                case 2:
                    userInformation.setCareer(value);
                    break;
                default:
                    break;
                }
            }
            guarantor.setUserInformation(userInformation);
            if (flag) {
                this.guarantors.set(0, guarantor);
            } else {
                this.guarantors.add(guarantor);
            }
            break;
        case 18: {
            if (this.guarFamily.containsKey(RelationEnum.FATHER.getName())) {
                family = this.guarFamily.get(RelationEnum.FATHER.getName());
            } else {
                family = new FamilyInformation();
                family.setRelationship(RelationEnum.FATHER.getName());
            }
            switch (j) {
            case 2:
                family.setUserName(value);
                break;
            case 3:
                family.setSex(value);
                break;
            case 4:
                family.setAge(value);
                break;
            case 5:
                family.setDetails(value);
                break;
            default:
                break;
            }
            this.guarFamily.put(RelationEnum.FATHER.getName(), family);
        }
            break;
        case 19: {
            if (this.guarFamily.containsKey(RelationEnum.MOTHER.getName())) {
                family = this.guarFamily.get(RelationEnum.MOTHER.getName());
            } else {
                family = new FamilyInformation();
                family.setRelationship(RelationEnum.MOTHER.getName());
            }
            switch (j) {
            case 2:
                family.setUserName(value);
                break;
            case 3:
                family.setSex(value);
                break;
            case 4:
                family.setAge(value);
                break;
            case 5:
                family.setDetails(value);
                break;
            default:
                break;
            }
            this.guarFamily.put(RelationEnum.MOTHER.getName(), family);
        }
            break;
        case 20: {
            if (this.guarFamily.containsKey(RelationEnum.MATE.getName())) {
                family = this.guarFamily.get(RelationEnum.MATE.getName());
            } else {
                family = new FamilyInformation();
                family.setRelationship(RelationEnum.MATE.getName());
            }
            switch (j) {
            case 2:
                family.setUserName(value);
                break;
            case 3:
                family.setSex(value);
                break;
            case 4:
                family.setAge(value);
                break;
            case 5:
                family.setDetails(value);
                break;
            default:
                break;
            }
            this.guarFamily.put(RelationEnum.MATE.getName(), family);
        }
            break;
        case 21: {
            if (this.guarFamily.containsKey(RelationEnum.CHILD.getName())) {
                family = this.guarFamily.get(RelationEnum.CHILD.getName());
            } else {
                family = new FamilyInformation();
                family.setRelationship(RelationEnum.CHILD.getName());
            }
            switch (j) {
            case 2:
                family.setUserName(value);
                break;
            case 3:
                family.setSex(value);
                break;
            case 4:
                family.setAge(value);
                break;
            case 5:
                family.setDetails(value);
                break;
            default:
                break;
            }
            this.guarFamily.put(RelationEnum.CHILD.getName(), family);
        }
            break;
        case 22:
            guarantor = null;
            userInformation = null;
            if (this.guarantors.size() > 1) {
                flag = true;
                guarantor = this.guarantors.get(1);
                if (null == guarantor.getUserInformation()) {
                    userInformation = new UserInformation();
                } else {
                    userInformation = guarantor.getUserInformation();
                }
            } else {
                guarantor = new Guarantor();
                userInformation = new UserInformation();
            }
            {
                switch (j) {
                case 2:
                    userInformation.setUserName(value);
                    break;
                case 4:
                    userInformation.setSex(value);
                    break;
                case 6:
                    userInformation.setAge(value);
                    break;
                case 8:
                    userInformation.setMarriage(value);
                    break;
                case 10:
                    userInformation.setMobile(value);
                    break;
                default:
                    break;
                }
            }
            guarantor.setUserInformation(userInformation);
            if (flag) {
                this.guarantors.set(1, guarantor);
            } else {
                this.guarantors.add(guarantor);
            }
            break;
        case 23:
            guarantor = null;
            userInformation = null;
            if (this.guarantors.size() > 1) {
                flag = true;
                guarantor = this.guarantors.get(1);
                if (null == guarantor.getUserInformation()) {
                    userInformation = new UserInformation();
                } else {
                    userInformation = guarantor.getUserInformation();
                }
            } else {
                guarantor = new Guarantor();
                userInformation = new UserInformation();
            }
            {
                switch (j) {
                case 2:
                    userInformation.setPresentAddress(value);
                    break;
                case 4:
                    userInformation.setOwnership(value);
                    break;
                default:
                    break;
                }
            }
            guarantor.setUserInformation(userInformation);
            if (flag) {
                this.guarantors.set(1, guarantor);
            } else {
                this.guarantors.add(guarantor);
            }
            break;
        case 24:
            guarantor = null;
            userInformation = null;
            if (this.guarantors.size() > 1) {
                flag = true;
                guarantor = this.guarantors.get(1);
                if (null == guarantor.getUserInformation()) {
                    userInformation = new UserInformation();
                } else {
                    userInformation = guarantor.getUserInformation();
                }
            } else {
                guarantor = new Guarantor();
                userInformation = new UserInformation();
            }
            {
                switch (j) {
                case 2:
                    userInformation.setIdNumber(value);
                    break;
                default:
                    break;
                }
            }
            guarantor.setUserInformation(userInformation);
            if (flag) {
                this.guarantors.set(1, guarantor);
            } else {
                this.guarantors.add(guarantor);
            }
            break;
        case 25:
            guarantor = null;
            userInformation = null;
            if (this.guarantors.size() > 1) {
                flag = true;
                guarantor = this.guarantors.get(1);
                if (null == guarantor.getUserInformation()) {
                    userInformation = new UserInformation();
                } else {
                    userInformation = guarantor.getUserInformation();
                }
            } else {
                guarantor = new Guarantor();
                userInformation = new UserInformation();
            }
            {
                switch (j) {
                case 2:
                    userInformation.setCareer(value);
                    break;
                default:
                    break;
                }
            }
            guarantor.setUserInformation(userInformation);
            if (flag) {
                this.guarantors.set(1, guarantor);
            } else {
                this.guarantors.add(guarantor);
            }
            break;
        case 27: {
            if (this.guarFamily2.containsKey(RelationEnum.FATHER.getName())) {
                family = this.guarFamily2.get(RelationEnum.FATHER.getName());
            } else {
                family = new FamilyInformation();
                family.setRelationship(RelationEnum.FATHER.getName());
            }
            switch (j) {
            case 2:
                family.setUserName(value);
                break;
            case 3:
                family.setSex(value);
                break;
            case 4:
                family.setAge(value);
                break;
            case 5:
                family.setDetails(value);
                break;
            default:
                break;
            }
            this.guarFamily2.put(RelationEnum.FATHER.getName(), family);
        }
            break;
        case 28: {
            if (this.guarFamily2.containsKey(RelationEnum.MOTHER.getName())) {
                family = this.guarFamily2.get(RelationEnum.MOTHER.getName());
            } else {
                family = new FamilyInformation();
                family.setRelationship(RelationEnum.MOTHER.getName());
            }
            switch (j) {
            case 2:
                family.setUserName(value);
                break;
            case 3:
                family.setSex(value);
                break;
            case 4:
                family.setAge(value);
                break;
            case 5:
                family.setDetails(value);
                break;
            default:
                break;
            }
            this.guarFamily2.put(RelationEnum.MOTHER.getName(), family);
        }
            break;
        case 29: {
            if (this.guarFamily2.containsKey(RelationEnum.MATE.getName())) {
                family = this.guarFamily2.get(RelationEnum.MATE.getName());
            } else {
                family = new FamilyInformation();
                family.setRelationship(RelationEnum.MATE.getName());
            }
            switch (j) {
            case 2:
                family.setUserName(value);
                break;
            case 3:
                family.setSex(value);
                break;
            case 4:
                family.setAge(value);
                break;
            case 5:
                family.setDetails(value);
                break;
            default:
                break;
            }
            this.guarFamily2.put(RelationEnum.MATE.getName(), family);
        }
            break;
        case 30: {
            if (this.guarFamily2.containsKey(RelationEnum.CHILD.getName())) {
                family = this.guarFamily2.get(RelationEnum.CHILD.getName());
            } else {
                family = new FamilyInformation();
                family.setRelationship(RelationEnum.CHILD.getName());
            }
            switch (j) {
            case 2:
                family.setUserName(value);
                break;
            case 3:
                family.setSex(value);
                break;
            case 4:
                family.setAge(value);
                break;
            case 5:
                family.setDetails(value);
                break;
            default:
                break;
            }
            this.guarFamily2.put(RelationEnum.CHILD.getName(), family);
        }
            break;
        case 31:
            if (j == 2) {
                this.obj.setRepaymentDate(value);
            }
            break;
        case 32:
            if (j == 2) {
                this.obj.setIntroducer(value);
            }
            break;
        case 33:
            if (j == 2) {
                this.obj.setHandledBy(value);
            }
            break;
        default:
            break;
        }

    }

    public LoanApplication getObj() {
        return this.obj;
    }

    public void setObj(LoanApplication obj) {
        this.obj = obj;
    }
}
