package com.nju.edu.erp.dao;

import com.nju.edu.erp.enums.sheetState.SaleReturnsSheetState;
import com.nju.edu.erp.model.po.SaleReturnsSheetContentPO;
import com.nju.edu.erp.model.po.SaleReturnsSheetPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface SaleReturnsSheetDao {
    /**
     * 获取最近一条进货退货单
     *
     * @return 最近一条进货退货单
     */
    SaleReturnsSheetPO getLatest();

    /**
     * 存入一条进货退货单记录
     *
     * @param toSave 一条进货退货单记录
     * @return 影响的行数
     */
    int save(SaleReturnsSheetPO toSave);

    /**
     * 把进货退货单上的具体内容存入数据库
     *
     * @param saleReturnsSheetContent 进货退货单上的具体内容
     */
    void saveBatch(List<SaleReturnsSheetContentPO> saleReturnsSheetContent);

    /**
     * 返回所有进货退货单
     *
     * @return 进货退货单列表
     */
    List<SaleReturnsSheetPO> findAll();

    /**
     * 根据state返回进货退货单
     *
     * @param state 进货退货单状态
     * @return 进货退货单列表
     */
    List<SaleReturnsSheetPO> findAllByState(SaleReturnsSheetState state);

    /**
     * 根据 purchaseReturnsSheetId 找到条目， 并更新其状态为state
     *
     * @param saleReturnsSheetId 进货退货单id
     * @param state                  进货退货单状态
     * @return 影响的条目数
     */
    int updateState(String saleReturnsSheetId, SaleReturnsSheetState state);

    /**
     * 根据 purchaseReturnsSheetId 和 prevState 找到条目， 并更新其状态为state
     *
     * @param saleReturnsSheetId 进货退货单id
     * @param prevState              进货退货单之前的状态
     * @param state                  进货退货单状态
     * @return 影响的条目数
     */
    int updateStateV2(String saleReturnsSheetId, SaleReturnsSheetState prevState, SaleReturnsSheetState state);

    /**
     * 通过purchaseReturnsSheetId找到对应条目
     *
     * @param saleReturnsSheetId 进货退货单id
     * @return
     */
    SaleReturnsSheetPO findOneById(String saleReturnsSheetId);

    /**
     * 通过saleReturnsSheetId找到对应的content条目
     *
     * @param saleReturnsSheetId
     * @return
     */
    List<SaleReturnsSheetContentPO> findContentBySaleReturnsSheetId(String saleReturnsSheetId);
}
