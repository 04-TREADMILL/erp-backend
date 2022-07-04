package com.nju.edu.erp.model.po;

import com.nju.edu.erp.enums.sheetState.PaymentSheetState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentSheetPO {
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
     * 公司银行账户
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
    /**
     * 单据状态
     */
    private PaymentSheetState state;
    /**
     * 创建时间
     */
    private Date createTime;
}
