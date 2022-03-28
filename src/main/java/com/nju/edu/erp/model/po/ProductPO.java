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
public class ProductPO {

    /**
     * 商品id
     */
    private String id;

    /**
     * 商品名
     */
    private String name;

    /**
     * 分类ID
     */
    private Integer categoryId;

    /**
     * 商品型号
     */
    private String type;

    /**
     * 商品数量
     */
    private Integer quantity;

    /**
     *  进价
     */
    private BigDecimal purchasePrice;

    /**
     *  零售价
     */
    private BigDecimal retailPrice;

    /**
     *  最近进价
     */
    private BigDecimal recentPp;

    /**
     *  最近零售价
     */
    private BigDecimal recentRp;
}
