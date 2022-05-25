package com.nju.edu.erp.model.po;

import com.nju.edu.erp.enums.sheetState.WarehouseInputSheetState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WarehouseInputSheetPO {
    /**
     * RKD + 日期 + index = 入库单编号
     */
    private String id;
    /**
     * 批次
     */
    private Integer batchId;
    /**
     * 操作员
     */
    private String operator;
    /**
     * 操作时间
     */
    private Date createTime;
    /**
     * 关联的进货单据
     */
    private String purchaseSheetId;
    /**
     * 单据状态
     */
    private WarehouseInputSheetState state;
}
