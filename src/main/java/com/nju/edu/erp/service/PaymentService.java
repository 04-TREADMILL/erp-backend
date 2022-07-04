package com.nju.edu.erp.service;

import com.nju.edu.erp.enums.sheetState.PaymentSheetState;
import com.nju.edu.erp.model.vo.UserVO;
import com.nju.edu.erp.model.vo.finance.PaymentSheetVO;

import java.util.List;

public interface PaymentService {

    void makePaymentSheet(UserVO userVO, PaymentSheetVO paymentSheetVO);

    List<PaymentSheetVO> getPaymentSheetByState(PaymentSheetState state);

    void approval(String paymentSheetId, PaymentSheetState state);

    PaymentSheetVO getPaymentSheetById(String paymentSheetId);
}
