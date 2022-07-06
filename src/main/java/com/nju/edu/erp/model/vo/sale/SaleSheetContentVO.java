package com.nju.edu.erp.model.vo.sale;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SaleSheetContentVO {
    /**
     * 商品id
     */
    private String pid;
    /**
     * 数量
     */
    private Integer quantity;
    /**
     * 单价
     */
    private BigDecimal unitPrice;
    /**
     * 金额
     */
    private BigDecimal totalPrice;
    /**
     * 备注
     */
    private String remark;
}
