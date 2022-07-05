package com.nju.edu.erp.service;

import com.nju.edu.erp.enums.sheetState.SalarySheetState;
import com.nju.edu.erp.model.vo.finance.SalarySheetVO;

import java.util.List;

public interface SalaryService {

    void makeSalarySheet(SalarySheetVO salarySheetVO);

    List<SalarySheetVO> getSalarySheetByState(SalarySheetState state);

    void approval(String salarySheetId, SalarySheetState state);

    SalarySheetVO getSalarySheetById(String salarySheetId);
}
