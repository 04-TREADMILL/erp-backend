package com.nju.edu.erp.web.controller;

import com.nju.edu.erp.auth.Authorized;
import com.nju.edu.erp.enums.Role;
import com.nju.edu.erp.exception.MyServiceException;
import com.nju.edu.erp.service.FinanceService;
import com.nju.edu.erp.web.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Date;

@RestController
@RequestMapping(path = "/finance")
public class FinanceController {
    private final FinanceService financeService;

    @Autowired
    public FinanceController(FinanceService financeService) {
        this.financeService = financeService;
    }

    @GetMapping("/sale-detail")
    @Authorized(roles = {Role.GM, Role.ADMIN, Role.FINANCIAL_STAFF})
    public Response getSaleDetail(
            @RequestParam(value = "from", required = false) Date from,
            @RequestParam(value = "to", required = false) Date to,
            @RequestParam(value = "product", required = false) String product,
            @RequestParam(value = "salesman", required = false) String salesman,
            @RequestParam(value = "customerId", required = false) Integer customerId) {
        return Response.buildSuccess(
                financeService.filterSaleDetailByCustomer(
                        financeService.filterSaleDetailBySalesman(
                                financeService.filterSaleDetailByProduct(
                                        financeService.filterSaleDetailByDate(
                                                financeService.fetchAllSaleDetail(), from, to),
                                        product),
                                salesman),
                        customerId));
    }
}
