package com.nju.edu.erp.dao;

import com.nju.edu.erp.model.po.WarehousePO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface WarehouseDao {
    void saveBatch(List<WarehousePO> warehousePOList);

    void deductQuantity(WarehousePO warehousePO);

    List<WarehousePO> findAllNotZeroByPidSortedByBatchId(String pid);
}
