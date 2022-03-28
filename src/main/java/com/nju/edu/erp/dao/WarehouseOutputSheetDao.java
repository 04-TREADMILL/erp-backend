package com.nju.edu.erp.dao;

import com.nju.edu.erp.model.po.WarehouseOutputSheetContentPO;
import com.nju.edu.erp.model.po.WarehouseOutputSheetPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface WarehouseOutputSheetDao {
    /**
     * 获取最近一条出库单
     * @return 最近一条出库单
     */
    WarehouseOutputSheetPO getLatest();

    /**
     * 存入一条出库单记录
     * @param toSave 一条出库单记录
     * @return
     */
    void save(WarehouseOutputSheetPO toSave);

    /**
     * 把出库单上的具体内容存入数据库
     * @param warehouseInputListPOList 出库单上的具体内容
     */
    void saveBatch(List<WarehouseOutputSheetContentPO> warehouseOutputListPOSheetContent);
}
