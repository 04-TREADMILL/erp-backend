package com.nju.edu.erp.service;

import com.nju.edu.erp.enums.sheetState.ReceiptSheetState;
import com.nju.edu.erp.model.vo.UserVO;
import com.nju.edu.erp.model.vo.finance.ReceiptSheetVO;

import java.util.List;

public interface ReceiptService {
    void makeReceiptSheet(UserVO userVO, ReceiptSheetVO receiptSheetVO);

    List<ReceiptSheetVO> getReceiptSheetByState(ReceiptSheetState state);

    void approval(String receiptSheetId, ReceiptSheetState state);

    ReceiptSheetVO getReceiptSheetById(String receiptSheetId);
}
