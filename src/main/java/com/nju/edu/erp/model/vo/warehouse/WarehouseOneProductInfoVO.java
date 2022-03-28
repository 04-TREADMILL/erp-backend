package com.nju.edu.erp.model.vo.warehouse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WarehouseOneProductInfoVO { //商品/批次/单价/已选数量/总数量/总金额
    // 1. 填商品ID,数量,备注   pid, quantity, remark -> GetWareProductInfo
    // 2. 系统返回该型号商品不同批次的库存信息 -> WarehouseOneProductInfo
    private String productId;
    private Integer batchId;
    private BigDecimal purchasePrice;
    private Integer selectedQuantity;
    private Integer totalQuantity;
    private BigDecimal sumPrice;
    private String remark;
}
