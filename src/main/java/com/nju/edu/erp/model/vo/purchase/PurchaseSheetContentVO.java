package com.nju.edu.erp.model.vo.purchase;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PurchaseSheetContentVO {
    /**
     * 自增id, 新建单据时前端传null
     */
    private Integer id;
    /**
     * 进货单id, 新建单据时前端传null
     */
    private String purchaseSheetId;
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
