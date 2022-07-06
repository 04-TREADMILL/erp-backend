package com.nju.edu.erp.web.controller;

import com.nju.edu.erp.auth.Authorized;
import com.nju.edu.erp.enums.Role;
import com.nju.edu.erp.service.FinanceService;
import com.nju.edu.erp.utils.ExcelUtil;
import com.nju.edu.erp.utils.IdDateUtil;
import com.nju.edu.erp.web.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.stream.Collectors;

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
            @RequestParam(value = "from", required = false) String from,
            @RequestParam(value = "to", required = false) String to,
            @RequestParam(value = "product", required = false) String product,
            @RequestParam(value = "salesman", required = false) String salesman,
            @RequestParam(value = "customerId", required = false) Integer customerId) {
        return Response.buildSuccess(
                financeService.fetchAllSaleDetail().stream().filter(
                                saleDetailVO -> (((from == null && to == null) ||
                                        (IdDateUtil.parseDateFromStr(saleDetailVO.getTime()).after(IdDateUtil.parseDateFromStr(from)) &&
                                                IdDateUtil.parseDateFromStr(saleDetailVO.getTime()).before(IdDateUtil.parseDateFromStr(to))))
                                        && (salesman == null || saleDetailVO.getSalesman().equals(salesman))
                                        && (customerId == null || saleDetailVO.getSeller().equals(customerId))
                                        && (product == null || saleDetailVO.getName().equals(product))
                                ))
                        .collect(Collectors.toList()));
    }

    @GetMapping("/sale-detail-excel")
    @Authorized(roles = {Role.GM, Role.ADMIN, Role.FINANCIAL_STAFF})
    public void getSaleDetailExcel(HttpServletRequest request, HttpServletResponse response) {
        ExcelUtil.exportSaleDetailExcel(response, financeService.fetchAllSaleDetail());
    }


    @GetMapping("/profit")
    @Authorized(roles = {Role.GM, Role.ADMIN, Role.FINANCIAL_STAFF})
    public Response getProfit(
            @RequestParam(value = "from") String from,
            @RequestParam(value = "to") String to) {
        return Response.buildSuccess(financeService.calculateProfit(IdDateUtil.parseDateFromStr(from), IdDateUtil.parseDateFromStr(to)));
    }
}
