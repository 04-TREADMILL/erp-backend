package com.nju.edu.erp.dao;

import com.nju.edu.erp.model.po.WarehouseInputSheetContentPO;
import com.nju.edu.erp.model.po.WarehouseInputSheetPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface WarehouseInputSheetDao {
    /**
     * 获取最近一条入库单
     * @return 最近一条入库单
     */
    WarehouseInputSheetPO getLatest();

    /**
     * 存入一条入库单记录
     * @param toSave 一条入库单记录
     * @return
     */
    int save(WarehouseInputSheetPO toSave);

    /**
     * 把入库单上的具体内容存入数据库
     * @param warehouseInputListPOSheetContent 入库单上的具体内容
     */
    void saveBatch(List<WarehouseInputSheetContentPO> warehouseInputListPOSheetContent);
}
