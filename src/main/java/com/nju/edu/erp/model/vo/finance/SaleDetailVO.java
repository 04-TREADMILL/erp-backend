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
public class SaleDetailVO {
    /**
     * 时间
     */
    private String time;
    /**
     * 商品名
     */
    private String name;
    /**
     * 商品型号
     */
    private String type;
    /**
     * 商品数量
     */
    private Integer quantity;
    /**
     * 商品单价
     */
    private BigDecimal unitPrice;
    /**
     * 商品金额
     */
    private BigDecimal totalPrice;
    /**
     * 业务员
     */
    private String salesman;
    /**
     * 客户编号
     */
    private Integer seller;
}
