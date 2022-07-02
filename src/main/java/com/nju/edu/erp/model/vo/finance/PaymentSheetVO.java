package com.nju.edu.erp.model.vo.finance;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentSheetVO {
    /**
     * 付款单单据编号
     */
    private String id;
    /**
     * 供应商编号
     */
    private Integer supplier;
    /**
     * 操作员
     */
    private String operator;
    /**
     * 供应商银行账户
     */
    private String account;
    /**
     * 转账总金额
     */
    private BigDecimal totalAmount;
    /**
     * 备注
     */
    private String comment;
}
