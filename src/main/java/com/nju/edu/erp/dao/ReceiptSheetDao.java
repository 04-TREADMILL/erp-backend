package com.nju.edu.erp.dao;

import com.nju.edu.erp.enums.sheetState.ReceiptSheetState;
import com.nju.edu.erp.model.po.ReceiptSheetPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ReceiptSheetDao {

    int save(ReceiptSheetPO toSave);

    List<ReceiptSheetPO> findAll();

    List<ReceiptSheetPO> findAllByState(ReceiptSheetState state);

    int updateState(String receiptSheetId, ReceiptSheetState state);

    int updateStateV2(String receiptSheetId, ReceiptSheetState prevState, ReceiptSheetState state);

    ReceiptSheetPO findOneById(String receiptSheetId);
}

