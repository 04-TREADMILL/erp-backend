package com.nju.edu.erp.model.po;

import com.nju.edu.erp.enums.sheetState.ReceiptSheetState;
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
public class ReceiptSheetPO {
    /**
     * 收款单单据编号
     */
    private String id;
    /**
     * 销售商编号
     */
    private Integer seller;
    /**
     * 操作员
     */
    private String operator;
    /**
     * 销售商银行账户
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
    private ReceiptSheetState state;
    /**
     * 创建时间
     */
    private Date createTime;
}
