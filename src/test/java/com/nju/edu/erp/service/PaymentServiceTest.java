package com.nju.edu.erp.service;

import com.nju.edu.erp.enums.Role;
import com.nju.edu.erp.enums.sheetState.PaymentSheetState;
import com.nju.edu.erp.model.po.CustomerPO;
import com.nju.edu.erp.model.vo.UserVO;
import com.nju.edu.erp.model.vo.finance.AccountVO;
import com.nju.edu.erp.model.vo.finance.PaymentSheetVO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@SpringBootTest
public class PaymentServiceTest {
    @Autowired
    PaymentService paymentService;

    @Autowired
    CustomerService customerService;

    @Autowired
    AccountService accountService;

    @Test
    @Transactional
    @Rollback
    public void makePaymentSheet() {
        UserVO userVO = UserVO.builder()
                .name("jyy")
                .role(Role.FINANCIAL_STAFF)
                .build();

        PaymentSheetVO paymentSheetVO = PaymentSheetVO.builder()
                .account("core")
                .supplier(1)
                .operator("jyy")
                .totalAmount(BigDecimal.ONE)
                .build();

        paymentService.makePaymentSheet(userVO, paymentSheetVO);
    }

    @Test
    @Transactional
    @Rollback
    public void getPaymentSheetByState() {
        List<PaymentSheetVO> paymentSheetVOList = paymentService.getPaymentSheetByState(null);
        Assertions.assertEquals(2, paymentSheetVOList.size());
    }

    @Test
    @Transactional
    @Rollback
    public void approval_exceptions_1() {
        try {
            paymentService.approval("FKD-20220704-00000", PaymentSheetState.PENDING);
        } catch (Exception ignore) {
        } finally {
            PaymentSheetVO sheet = paymentService.getPaymentSheetById("FKD-20220704-00000");
            Assertions.assertEquals(PaymentSheetState.PENDING, sheet.getState());
        }
    }

    @Test
    @Transactional
    @Rollback
    public void approval_exceptions_2() {
        try {
            paymentService.approval("FKD-20220704-00001", PaymentSheetState.FAILURE);
        } catch (Exception ignore) {
        } finally {
            PaymentSheetVO sheet = paymentService.getPaymentSheetById("FKD-20220704-00001");
            Assertions.assertEquals(PaymentSheetState.SUCCESS, sheet.getState());
        }
    }

    @Test
    @Transactional
    @Rollback
    public void approval_success() {
        CustomerPO customerPO = customerService.findCustomerById(1);
        BigDecimal prevPayable = customerPO.getPayable();
        AccountVO accountVO = accountService.queryAccount("core");
        BigDecimal prevAmount = accountVO.getAmount();

        paymentService.approval("FKD-20220704-00000", PaymentSheetState.SUCCESS);
        PaymentSheetVO sheet = paymentService.getPaymentSheetById("FKD-20220704-00000");
        Assertions.assertEquals(PaymentSheetState.SUCCESS, sheet.getState());

        customerPO = customerService.findCustomerById(1);
        BigDecimal currPayable = customerPO.getPayable();
        accountVO = accountService.queryAccount("core");
        BigDecimal currAmount = accountVO.getAmount();

        Assertions.assertEquals(currAmount.add(BigDecimal.ONE), prevAmount);
        Assertions.assertEquals(currPayable.add(BigDecimal.ONE), prevPayable);
    }

    @Test
    @Transactional
    @Rollback
    public void approval_failure() {
        paymentService.approval("FKD-20220704-00000", PaymentSheetState.FAILURE);
        PaymentSheetVO sheet = paymentService.getPaymentSheetById("FKD-20220704-00000");
        Assertions.assertEquals(PaymentSheetState.FAILURE, sheet.getState());
    }
}
