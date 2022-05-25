package com.nju.edu.erp.service;

import com.nju.edu.erp.enums.sheetState.PurchaseReturnsSheetState;
import com.nju.edu.erp.model.vo.UserVO;
import com.nju.edu.erp.model.vo.purchaseReturns.PurchaseReturnsSheetVO;
import com.nju.edu.erp.model.vo.purchaseReturns.PurchaseReturnsSheetVO;

import java.util.List;

// 制定进货退货单 + 销售经理审批/总经理二级审批 + 更新客户表 + 更新库存
public interface PurchaseReturnsService {
    /**
     * 制定进货退货单
     * @param purchaseReturnsSheetVO 进货退货单
     */
    void makePurchaseReturnsSheet(UserVO userVO, PurchaseReturnsSheetVO purchaseReturnsSheetVO);

    /**
     * 根据状态获取进货退货单(state == null 则获取所有进货退货单)
     * @param state 进货退货单状态
     * @return 进货退货单
     */
    List<PurchaseReturnsSheetVO> getPurchaseReturnsSheetByState(PurchaseReturnsSheetState state);

    /**
     * 根据进货退货单id进行审批(state == "待二级审批"/"审批完成"/"审批失败")
     * 在controller层进行权限控制
     * @param purchaseReturnsSheetId 进货退货单id
     * @param state 进货退货单修改后的状态
     */
    void approval(String purchaseReturnsSheetId, PurchaseReturnsSheetState state);

}