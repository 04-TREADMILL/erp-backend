package com.nju.edu.erp.service;

import com.nju.edu.erp.model.vo.finance.SaleDetailVO;

import java.util.List;

public interface FinanceService {
    List<SaleDetailVO> fetchAllSaleDetail();
}
