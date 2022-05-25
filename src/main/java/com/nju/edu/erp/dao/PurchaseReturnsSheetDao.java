package com.nju.edu.erp.dao;

import com.nju.edu.erp.enums.sheetState.PurchaseReturnsSheetState;
import com.nju.edu.erp.model.po.PurchaseReturnsSheetContentPO;
import com.nju.edu.erp.model.po.PurchaseReturnsSheetPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface PurchaseReturnsSheetDao {
    /**
     * 获取最近一条进货退货单
     * @return 最近一条进货退货单
     */
    PurchaseReturnsSheetPO getLatest();

    /**
     * 存入一条进货退货单记录
     * @param toSave 一条进货退货单记录
     * @return 影响的行数
     */
    int save(PurchaseReturnsSheetPO toSave);

    /**
     * 把进货退货单上的具体内容存入数据库
     * @param PurchaseReturnsSheetContent 进货退货单上的具体内容
     */
    void saveBatch(List<PurchaseReturnsSheetContentPO> PurchaseReturnsSheetContent);

    /**
     * 返回所有进货退货单
     * @return 进货退货单列表
     */
    List<PurchaseReturnsSheetPO> findAll();

    /**
     * 根据state返回进货退货单
     * @param state 进货退货单状态
     * @return 进货退货单列表
     */
    List<PurchaseReturnsSheetPO> findAllByState(PurchaseReturnsSheetState state);

    /**
     * 根据 purchaseReturnsSheetId 找到条目， 并更新其状态为state
     * @param purchaseReturnsSheetId 进货退货单id
     * @param state 进货退货单状态
     * @return 影响的条目数
     */
    int updateState(String purchaseReturnsSheetId, PurchaseReturnsSheetState state);

    /**
     * 根据 purchaseReturnsSheetId 和 prevState 找到条目， 并更新其状态为state
     * @param purchaseReturnsSheetId 进货退货单id
     * @param prevState 进货退货单之前的状态
     * @param state 进货退货单状态
     * @return 影响的条目数
     */
    int updateStateV2(String purchaseReturnsSheetId, PurchaseReturnsSheetState prevState, PurchaseReturnsSheetState state);

    /**
     * 通过purchaseReturnsSheetId找到对应条目
     * @param purchaseReturnsSheetId 进货退货单id
     * @return
     */
    PurchaseReturnsSheetPO findOneById(String purchaseReturnsSheetId);

    /**
     * 通过purchaseReturnsSheetId找到对应的content条目
     * @param purchaseReturnsSheetId
     * @return
     */
    List<PurchaseReturnsSheetContentPO> findContentByPurchaseReturnsSheetId(String purchaseReturnsSheetId);

    /**
     * 通过purchaseReturnsSheetId找到退的货的对应批次
     * @param purchaseReturnsSheetId
     * @return 批次号
     */
    Integer findBatchId(String purchaseReturnsSheetId);
}
