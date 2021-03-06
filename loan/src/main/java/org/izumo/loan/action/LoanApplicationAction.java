package org.izumo.loan.action;

import org.izumo.loan.mongo.service.LoanApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/loan-application")
public class LoanApplicationAction {

    @Autowired
    private LoanApplicationService service;

    @RequestMapping("save-by-excel")
    public boolean read(String filePath) {
        return this.service.saveByExcel(filePath);
    }

    @RequestMapping("test")
    public String test() {
        return "Hi MongoDB";
    }
}
