package com.nju.edu.erp.service.Impl;

import com.nju.edu.erp.model.vo.warehouse.WarehouseInputFormContentVO;
import com.nju.edu.erp.model.vo.warehouse.WarehouseInputFormVO;
import com.nju.edu.erp.service.WarehouseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest
class WarehouseServiceImplTest {

    @Autowired
    WarehouseService warehouseService;

    @Test
    void productWarehousing() {
        WarehouseInputFormVO warehouseInputFormVO = new WarehouseInputFormVO();
        warehouseInputFormVO.setOperator("zr");
        List<WarehouseInputFormContentVO> warehouseInputFormContentVOS = new ArrayList<>();
        WarehouseInputFormContentVO warehouseInputFormContentVO = WarehouseInputFormContentVO.builder().productionDate(new Date()).pid("0000000000400000").purchasePrice(null).quantity(2000).remark("oaaa").build();
        WarehouseInputFormContentVO warehouseInputFormContentVO2 = WarehouseInputFormContentVO.builder().productionDate(new Date()).pid("0000000000500000").purchasePrice(null).quantity(1000).remark("paaa").build();
        WarehouseInputFormContentVO warehouseInputFormContentVO3 = WarehouseInputFormContentVO.builder().productionDate(new Date()).pid("0000000000500001").purchasePrice(null).quantity(2000).remark("qaaa").build();
        warehouseInputFormContentVOS.add(warehouseInputFormContentVO);
        warehouseInputFormContentVOS.add(warehouseInputFormContentVO2);
        warehouseInputFormContentVOS.add(warehouseInputFormContentVO3);
        warehouseInputFormVO.setList(warehouseInputFormContentVOS);
//        warehouseService.productWarehousing(warehouseInputFormVO);
    }
}