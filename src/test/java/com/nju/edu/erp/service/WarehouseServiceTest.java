package com.nju.edu.erp.service;

import com.nju.edu.erp.model.vo.warehouse.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
class WarehouseServiceTest {

    @Autowired
    WarehouseService warehouseService;

    @Test
    void productOutOfWarehouse() {
        List<WarehouseOneProductInfoVO> a = warehouseService.getWareProductInfo(GetWareProductInfoParamsVO.builder().pid("0000000000500000").quantity(800).remark("lallalala").build());
        a.addAll(warehouseService.getWareProductInfo(GetWareProductInfoParamsVO.builder().pid("0000000000500001").quantity(1700).remark("lulalulalu").build()));
        a.addAll(warehouseService.getWareProductInfo(GetWareProductInfoParamsVO.builder().pid("0000000000500002").quantity(100).remark("lulalulalei").build()));
        a.addAll(warehouseService.getWareProductInfo(GetWareProductInfoParamsVO.builder().pid("0000000000400000").quantity(1100).remark("lulalulalei").build()));


        List<WarehouseOutputFormContentVO> warehouseOutputFormContentVOS = a.stream().map(x -> WarehouseOutputFormContentVO.builder().pid(x.getProductId()).batchId(x.getBatchId()).purchasePrice(x.getPurchasePrice()).quantity(x.getSelectedQuantity()).remark(x.getRemark()).build()).collect(Collectors.toList());
        WarehouseOutputFormVO warehouseOutputFormVO = WarehouseOutputFormVO.builder().list(warehouseOutputFormContentVOS).operator("zyy").build();
//        warehouseService.productOutOfWarehouse(warehouseOutputFormVO);
    }

    @Test
    void getWareProductInfo() {
        List<WarehouseOneProductInfoVO> a = warehouseService.getWareProductInfo(GetWareProductInfoParamsVO.builder().pid("0000000000500000").quantity(30).remark("lallalala").build());
        a = warehouseService.getWareProductInfo(GetWareProductInfoParamsVO.builder().pid("0000000000500000").quantity(270).remark("lallalala").build());
        a = warehouseService.getWareProductInfo(GetWareProductInfoParamsVO.builder().pid("0000000000500000").quantity(800).remark("lallalala").build());
        a = warehouseService.getWareProductInfo(GetWareProductInfoParamsVO.builder().pid("0000000000500000").quantity(10000).remark("lallalala").build());
        System.out.println(a);
    }

}