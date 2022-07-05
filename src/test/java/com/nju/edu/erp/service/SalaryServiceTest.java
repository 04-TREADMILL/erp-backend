package com.nju.edu.erp.service;

import com.nju.edu.erp.enums.sheetState.SalarySheetState;
import com.nju.edu.erp.model.vo.finance.AccountVO;
import com.nju.edu.erp.model.vo.finance.SalarySheetVO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@SpringBootTest
public class SalaryServiceTest {
    @Autowired
    SalaryService salaryService;

    @Autowired
    AccountService accountService;

    @Autowired
    EmployeeService employeeService;

    @Test
    @Transactional
    @Rollback
    public void makePaymentSheet() {
        SalarySheetVO salarySheetVO = SalarySheetVO.builder()
                .account("core")
                .employeeId(17)
                .name("lyc")
                .build();

        salaryService.makeSalarySheet(salarySheetVO);
    }

    @Test
    @Transactional
    @Rollback
    public void approval_success() {
        AccountVO accountVO = accountService.queryAccount("core");
        BigDecimal prevAmount = accountVO.getAmount();

        salaryService.approval("GZD-20220705-00000", SalarySheetState.SUCCESS);
        SalarySheetVO sheet = salaryService.getSalarySheetById("GZD-20220705-00000");
        Assertions.assertEquals(SalarySheetState.SUCCESS, sheet.getState());

        accountVO = accountService.queryAccount("core");
        BigDecimal currAmount = accountVO.getAmount();

        Assertions.assertEquals(currAmount.add(BigDecimal.valueOf(14400)), prevAmount);
    }

    @Test
    @Transactional
    @Rollback
    public void approval_failure() {
        salaryService.approval("GZD-20220705-00000", SalarySheetState.FAILURE);
        SalarySheetVO sheet = salaryService.getSalarySheetById("GZD-20220705-00000");
        Assertions.assertEquals(SalarySheetState.FAILURE, sheet.getState());
    }
}
