package com.nju.edu.erp.dao;

import com.nju.edu.erp.enums.sheetState.SalarySheetState;
import com.nju.edu.erp.model.po.SalarySheetPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface SalarySheetDao {

    SalarySheetPO getLatest();

    int save(SalarySheetPO toSave);

    List<SalarySheetPO> findAll();

    List<SalarySheetPO> findAllByState(SalarySheetState state);

    int updateState(String salarySheetId, SalarySheetState state);

    int updateStateV2(String salarySheetId, SalarySheetState prevState, SalarySheetState state);

    SalarySheetPO findOneById(String salarySheetId);
}
