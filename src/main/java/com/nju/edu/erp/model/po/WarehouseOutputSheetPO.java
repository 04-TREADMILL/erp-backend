package com.nju.edu.erp.model.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WarehouseOutputSheetPO {
    /**
     * CKD + 日期 + index = 入库单编号
     */
    private String id;
    /**
     * 操作员
     */
    private String operator;
    /**
     * 操作时间
     */
    private Date updateTime;
}
