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
public class WarehouseInputSheetContentPO {
    /**
     * 入库商品列表id
     */
    private Integer id;
    /**
     * 入库单编号
     */
    private String wiId;
    /**
     * 商品id
     */
    private String pid;
    /**
     * 商品数量
     */
    private Integer quantity;
    /**
     * 单价
     */
    private BigDecimal purchasePrice;
    /**
     * 出厂日期
     */
    private Date productionDate;
    /**
     * 备注
     */
    private String remark;
}
