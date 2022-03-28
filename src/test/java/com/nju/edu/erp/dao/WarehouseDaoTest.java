package com.nju.edu.erp.dao;

import com.nju.edu.erp.model.po.WarehousePO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class WarehouseDaoTest {

    @Autowired
    WarehouseDao warehouseDao;

    @Test
    void deductQuantity() {
//        warehouseDao.deductQuantity(WarehousePO.builder().batchId(1).pid("0000000000400000").quantity(-56).build());
    }

    @Test
    void findAllNotZeroByPidSortedByBatchId() {
        List<WarehousePO> allNotZeroByPidSortedByBatchId = warehouseDao.findAllNotZeroByPidSortedByBatchId("0000000000500000");
        System.out.println(allNotZeroByPidSortedByBatchId);
    }
}