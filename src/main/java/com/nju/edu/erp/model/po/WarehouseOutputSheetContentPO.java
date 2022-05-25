package com.nju.edu.erp.model.po;

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
public class WarehouseOutputSheetContentPO {
    /**
     * 出库商品列表id
     */
    private Integer id;
    /**
     * 商品id
     */
    private String pid;
    /**
     * 出库单编号
     */
    private String woId;
    /**
     * 批次
     */
    private Integer batchId;
    /**
     * 商品数量
     */
    private Integer quantity;
    /**
     * 单价
     */
    private BigDecimal salePrice;
    /**
     * 备注
     */
    private String remark;
}
