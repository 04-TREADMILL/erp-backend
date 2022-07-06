package com.nju.edu.erp.service.Impl;

import com.nju.edu.erp.model.vo.finance.SaleDetailVO;
import com.nju.edu.erp.model.vo.product.ProductInfoVO;
import com.nju.edu.erp.model.vo.sale.SaleSheetContentVO;
import com.nju.edu.erp.model.vo.sale.SaleSheetVO;
import com.nju.edu.erp.service.FinanceService;
import com.nju.edu.erp.service.ProductService;
import com.nju.edu.erp.service.SaleService;
import com.nju.edu.erp.utils.IdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FinanceServiceImpl implements FinanceService {
    private final SaleService saleService;

    private final ProductService productService;

    @Autowired
    public FinanceServiceImpl(SaleService saleService, ProductService productService) {
        this.saleService = saleService;
        this.productService = productService;
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
}
