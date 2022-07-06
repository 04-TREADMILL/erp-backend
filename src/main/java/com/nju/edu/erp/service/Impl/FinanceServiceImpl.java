package com.nju.edu.erp.service.Impl;

import com.nju.edu.erp.enums.sheetState.PurchaseSheetState;
import com.nju.edu.erp.enums.sheetState.SalarySheetState;
import com.nju.edu.erp.enums.sheetState.SaleSheetState;
import com.nju.edu.erp.model.vo.finance.ProfitVO;
import com.nju.edu.erp.model.vo.finance.SalarySheetVO;
import com.nju.edu.erp.model.vo.finance.SaleDetailVO;
import com.nju.edu.erp.model.vo.product.ProductInfoVO;
import com.nju.edu.erp.model.vo.purchase.PurchaseSheetVO;
import com.nju.edu.erp.model.vo.sale.SaleSheetContentVO;
import com.nju.edu.erp.model.vo.sale.SaleSheetVO;
import com.nju.edu.erp.service.*;
import com.nju.edu.erp.utils.IdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class FinanceServiceImpl implements FinanceService {
    private final SaleService saleService;

    private final ProductService productService;

    private final PurchaseService purchaseService;

    private final SalaryService salaryService;

    @Autowired
    public FinanceServiceImpl(SaleService saleService, ProductService productService, PurchaseService purchaseService, SalaryService salaryService) {
        this.saleService = saleService;
        this.productService = productService;
        this.purchaseService = purchaseService;
        this.salaryService = salaryService;
    }

    @Override
    public List<SaleDetailVO> fetchAllSaleDetail() {
        List<SaleDetailVO> list = new ArrayList<>();
        List<SaleSheetVO> saleSheetVOS = saleService.getSaleSheetByState(null);
        for (SaleSheetVO saleSheetVO : saleSheetVOS) {
            for (SaleSheetContentVO saleSheetContentVO : saleSheetVO.getSaleSheetContent()) {
                ProductInfoVO productInfoVO = productService.getOneProductByPid(saleSheetContentVO.getPid());
                SaleDetailVO saleDetailVO;
                saleDetailVO = SaleDetailVO.builder()
                        .name(productInfoVO.getName())
                        .type(productInfoVO.getType())
                        .time(IdUtil.parseDateFromSheetId(saleSheetVO.getId(), "XSD"))
                        .quantity(saleSheetContentVO.getQuantity())
                        .unitPrice(saleSheetContentVO.getUnitPrice())
                        .totalPrice(saleSheetContentVO.getTotalPrice())
                        .salesman(saleSheetVO.getSalesman())
                        .seller(saleSheetVO.getSupplier())
                        .build();
                list.add(saleDetailVO);
            }
        }

        return list;
    }

    @Override
    public ProfitVO calculateProfit(Date from, Date to) {
        assert from.before(to);

        BigDecimal incomingRaw = BigDecimal.ZERO;
        BigDecimal incomingReal = BigDecimal.ZERO;
        BigDecimal outgoingPurchase = BigDecimal.ZERO;
        BigDecimal outgoingHuman = BigDecimal.ZERO;

        for (SaleSheetVO saleSheetVO : saleService.getSaleSheetByState(SaleSheetState.SUCCESS)) {
            Date date = IdUtil.parseDateFromSheetId(saleSheetVO.getId(), "XSD");
            if (date.after(from) && date.before(to)) {
                incomingRaw = incomingRaw.add(saleSheetVO.getRawTotalAmount());
                incomingReal = incomingReal.add(saleSheetVO.getFinalAmount());
            }
        }

        for (PurchaseSheetVO purchaseSheetVO : purchaseService.getPurchaseSheetByState(PurchaseSheetState.SUCCESS)) {
            Date date = IdUtil.parseDateFromSheetId(purchaseSheetVO.getId(), "JHD");
            if (date.after(from) && date.before(to)) {
                outgoingPurchase = outgoingPurchase.add(purchaseSheetVO.getTotalAmount());
            }
        }

        for (SalarySheetVO salarySheetVO : salaryService.getSalarySheetByState(SalarySheetState.SUCCESS)) {
            Date date = IdUtil.parseDateFromSheetId(salarySheetVO.getId(), "GZD");
            if (date.after(from) && date.before(to)) {
                outgoingHuman = outgoingHuman.add(salarySheetVO.getRealSalary());
            }
        }

        return ProfitVO.builder()
                .incomingRaw(incomingRaw)
                .incomingReal(incomingReal)
                .outgoingPurchase(outgoingPurchase)
                .outgoingHuman(outgoingHuman)
                .profit(incomingReal.subtract(outgoingPurchase.add(outgoingHuman)))
                .build();
    }
}
