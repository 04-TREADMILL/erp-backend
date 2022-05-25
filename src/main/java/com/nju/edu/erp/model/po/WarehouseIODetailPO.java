package com.nju.edu.erp.model.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WarehouseIODetailPO {
    /**
     * 库存操作类型，入库或出库
     */
    private String type;

    /**
     * 出库单/入库单id
     */
    private String sheetId;

    /**
     * 商品名
     */
    private String productName;

    /**
     * 分类名
     */
    private String categoryName;

    /**
     * 商品数量
     */
    private Integer quantity;

    /**
     * 商品单价
     */
    private BigDecimal unitPrice;

    /**
     * 商品总价
     */
    private BigDecimal total_price;

    /**
     * 出库单/入库单创建时间
     */
    private Date createTime;

}
