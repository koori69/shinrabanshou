package org.izumo.loan.mongo.service;

import java.util.List;

import org.bson.types.ObjectId;
import org.izumo.loan.mongo.dao.LoanApplicationDao;
import org.izumo.loan.mongo.entity.LoanApplication;
import org.izumo.loan.util.LoanExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoanApplicationService {

    @Autowired
    private LoanApplicationDao dao;

    public List<LoanApplication> findAll() {
        return this.dao.findAll();
    }

    public LoanApplication save(LoanApplication book) {
        return this.dao.save(book);
    }

    public void deleteEntity(LoanApplication book) {
        this.dao.delete(book);
    }

    public void deleteById(ObjectId id) {
        this.dao.delete(id);
    }

    public void deleteByList(List<LoanApplication> list) {
        this.dao.delete(list);
    }

    public boolean saveByExcel(String filePath) {
        LoanExcelUtil excelUtil = new LoanExcelUtil();
        excelUtil.readExcel(filePath);
        LoanApplication loanApplication = excelUtil.getObj();
        if (null == loanApplication) {
            return false;
        }
        this.dao.save(loanApplication);
        if (null == loanApplication.getId() || loanApplication.getId().isEmpty()) {
            return false;
        }
        return true;
    }
}
