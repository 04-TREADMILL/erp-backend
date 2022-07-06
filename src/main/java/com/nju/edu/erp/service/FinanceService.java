package com.nju.edu.erp.service;

import com.nju.edu.erp.model.vo.finance.SaleDetailVO;

import java.util.Date;
import java.util.List;

public interface FinanceService {
    List<SaleDetailVO> fetchAllSaleDetail();

    List<SaleDetailVO> filterSaleDetailByDate(List<SaleDetailVO> list, Date from, Date to);

    List<SaleDetailVO> filterSaleDetailByProduct(List<SaleDetailVO> list, String name);

    List<SaleDetailVO> filterSaleDetailByCustomer(List<SaleDetailVO> list, Integer id);

    List<SaleDetailVO> filterSaleDetailBySalesman(List<SaleDetailVO> list, String name);
}
