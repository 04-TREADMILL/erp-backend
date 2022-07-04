package com.nju.edu.erp.service;

import com.nju.edu.erp.enums.Role;
import com.nju.edu.erp.enums.sheetState.ReceiptSheetState;
import com.nju.edu.erp.model.po.CustomerPO;
import com.nju.edu.erp.model.vo.UserVO;
import com.nju.edu.erp.model.vo.finance.AccountVO;
import com.nju.edu.erp.model.vo.finance.ReceiptSheetVO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@SpringBootTest
public class ReceiptSheetTest {

    @Autowired
    ReceiptService receiptService;

    @Autowired
    CustomerService customerService;

    @Autowired
    AccountService accountService;

    @Test
    @Transactional
    @Rollback
    public void makeReceiptSheet() {
        UserVO userVO = UserVO.builder()
                .name("jyy")
                .role(Role.FINANCIAL_STAFF)
                .build();

        ReceiptSheetVO receiptSheetVO = ReceiptSheetVO.builder()
                .account("core")
                .seller(2)
                .operator("jyy")
                .totalAmount(BigDecimal.ONE)
                .build();

        receiptService.makeReceiptSheet(userVO, receiptSheetVO);
    }

    @Test
    @Transactional
    @Rollback
    public void getReceiptSheetByState() {
        List<ReceiptSheetVO> receiptSheetVOList = receiptService.getReceiptSheetByState(null);
        Assertions.assertEquals(2, receiptSheetVOList.size());
    }

    @Test
    @Transactional
    @Rollback
    public void approval_exceptions_1() {
        try {
            receiptService.approval("SKD-20220704-00000", ReceiptSheetState.PENDING);
        } catch (Exception ignore) {
        } finally {
            ReceiptSheetVO sheet = receiptService.getReceiptSheetById("SKD-20220704-00000");
            Assertions.assertEquals(ReceiptSheetState.PENDING, sheet.getState());
        }
    }

    @Test
    @Transactional
    @Rollback
    public void approval_exceptions_2() {
        try {
            receiptService.approval("SKD-20220704-00001", ReceiptSheetState.FAILURE);
        } catch (Exception ignore) {
        } finally {
            ReceiptSheetVO sheet = receiptService.getReceiptSheetById("SKD-20220704-00001");
            Assertions.assertEquals(ReceiptSheetState.SUCCESS, sheet.getState());
        }
    }

    @Test
    @Transactional
    @Rollback
    public void approval_success() {
        CustomerPO customerPO = customerService.findCustomerById(2);
        BigDecimal prevReceivable = customerPO.getReceivable();
        AccountVO accountVO = accountService.queryAccount("core");
        BigDecimal prevAmount = accountVO.getAmount();

        receiptService.approval("SKD-20220704-00000", ReceiptSheetState.SUCCESS);
        ReceiptSheetVO sheet = receiptService.getReceiptSheetById("SKD-20220704-00000");
        Assertions.assertEquals(ReceiptSheetState.SUCCESS, sheet.getState());

        customerPO = customerService.findCustomerById(2);
        BigDecimal currReceivable = customerPO.getReceivable();
        accountVO = accountService.queryAccount("core");
        BigDecimal currAmount = accountVO.getAmount();

        Assertions.assertEquals(currAmount.subtract(BigDecimal.ONE), prevAmount);
        Assertions.assertEquals(currReceivable.subtract(BigDecimal.ONE), prevReceivable);
    }

    @Test
    @Transactional
    @Rollback
    public void approval_failure() {
        receiptService.approval("SKD-20220704-00000", ReceiptSheetState.FAILURE);
        ReceiptSheetVO sheet = receiptService.getReceiptSheetById("SKD-20220704-00000");
        Assertions.assertEquals(ReceiptSheetState.FAILURE, sheet.getState());
    }
}
