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
public class WarehouseInputFormContentVO { // 入库单内部结构
    private String pid;
    private BigDecimal purchasePrice;
    private Integer quantity;
    private Date productionDate;
    private String remark;

    public String toString() {
        return "'" + pid + ", " + purchasePrice.toPlainString() + ", " + quantity + ", " + remark + "'";
    }
}
