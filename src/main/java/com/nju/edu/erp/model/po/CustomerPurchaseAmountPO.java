package com.nju.edu.erp.model.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerPurchaseAmountPO {

    /**
     * 客户
     */
    private  CustomerPO customerPO;

    /**
     * 用户在某时间段内通过某销售人员购买的商品的总金额(不考虑退货情况,销售单不需要审批通过)
     */
    private BigDecimal totalFinalAmount;
}
