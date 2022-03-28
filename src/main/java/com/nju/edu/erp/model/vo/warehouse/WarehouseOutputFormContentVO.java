package com.nju.edu.erp.model.vo.warehouse;

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
public class WarehouseOutputFormContentVO { // 出库单内部结构
    private String pid;
    private BigDecimal purchasePrice;
    private Integer quantity;
    private Integer batchId;
    private String remark;

    public String toString() {
        return "'" + pid + ", " + batchId + ", " + quantity + ", " + remark + "'";
    }
}


