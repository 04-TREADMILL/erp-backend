package com.nju.edu.erp.service;

import com.nju.edu.erp.model.vo.finance.ProfitVO;
import com.nju.edu.erp.model.vo.finance.SaleDetailVO;

import java.util.Date;
import java.util.List;

public interface FinanceService {
    List<SaleDetailVO> fetchAllSaleDetail();

    ProfitVO calculateProfit(Date from, Date to);
}
