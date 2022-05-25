package com.nju.edu.erp.service;

import com.nju.edu.erp.enums.sheetState.PurchaseSheetState;
import com.nju.edu.erp.model.vo.UserVO;
import com.nju.edu.erp.model.vo.purchase.PurchaseSheetVO;

import java.util.List;

// 制定进货单 + 销售经理审批/总经理二级审批 + 更新客户表/制定入库单草稿 + 库存管理人员确认入库单/总经理审批 + 更新库存
public interface PurchaseService {
    /**
     * 制定进货单
     * @param purchaseSheetVO 进货单
     */
    void makePurchaseSheet(UserVO userVO, PurchaseSheetVO purchaseSheetVO);

    /**
     * 根据状态获取进货单(state == null 则获取所有进货单)
     * @param state 进货单状态
     * @return 进货单
     */
    List<PurchaseSheetVO> getPurchaseSheetByState(PurchaseSheetState state);

    /**
     * 根据进货单id进行审批(state == "待二级审批"/"审批完成"/"审批失败")
     * 在controller层进行权限控制
     * @param purchaseSheetId 进货单id
     * @param state 进货单修改后的状态
     */
    void approval(String purchaseSheetId, PurchaseSheetState state);

    /**
     * 根据进货单Id搜索进货单信息
     * @param purchaseSheetId 进货单Id
     * @return 进货单全部信息
     */
    PurchaseSheetVO getPurchaseSheetById(String purchaseSheetId);




}
