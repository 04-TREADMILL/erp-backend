package com.nju.edu.erp.dao;

import com.nju.edu.erp.enums.sheetState.PaymentSheetState;
import com.nju.edu.erp.model.po.PaymentSheetPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface PaymentSheetDao {

    PaymentSheetPO getLatest();

    int save(PaymentSheetPO toSave);

    List<PaymentSheetPO> findAll();

    List<PaymentSheetPO> findAllByState(PaymentSheetState state);

    int updateState(String paymentSheetId, PaymentSheetState state);

    int updateStateV2(String paymentSheetId, PaymentSheetState prevState, PaymentSheetState state);

    PaymentSheetPO findOneById(String paymentSheetId);
}
