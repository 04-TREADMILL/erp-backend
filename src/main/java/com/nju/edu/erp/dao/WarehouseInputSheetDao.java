package com.nju.edu.erp.dao;

import com.nju.edu.erp.enums.sheetState.WarehouseInputSheetState;
import com.nju.edu.erp.model.po.WarehouseIODetailPO;
import com.nju.edu.erp.model.po.WarehouseInputSheetContentPO;
import com.nju.edu.erp.model.po.WarehouseInputSheetPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Date;
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

    /**
     * 查看处于各个状态的入库单
     * @param state 入库单的具体状态
     */
    List<WarehouseInputSheetPO> getDraftSheets(WarehouseInputSheetState state);

    /**
     * 查看所有入库单
     */
    List<WarehouseInputSheetPO> getAllSheets();

    /**
     * 查看指定ID的入库单
     * @param id
     */
    WarehouseInputSheetPO getSheet(String id);

    /**
     * 更新指定ID的入库单
     * @param warehouseInputSheetPO
     */
    int updateById(WarehouseInputSheetPO warehouseInputSheetPO);

    /**
     * 查询指定ID的入库单内的具体商品数据
     * @param warehouseInputSheetId
     */
    List<WarehouseInputSheetContentPO> getAllContentById(String warehouseInputSheetId);

    /**
     * 查询指定时间段内出/入库数量/金额/商品信息/分类信息
     * @param beginTime
     * @param endTime
     */
    List<WarehouseIODetailPO> getWarehouseIODetailByTime(Date beginTime,Date endTime);

    Integer getWarehouseInputProductQuantityByTime(Date beginTime,Date endTime);
}
