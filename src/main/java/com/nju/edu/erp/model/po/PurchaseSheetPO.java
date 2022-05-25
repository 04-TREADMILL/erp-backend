package com.nju.edu.erp.model.po;

import com.nju.edu.erp.enums.sheetState.PurchaseSheetState;
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
public class PurchaseSheetPO {
    /**
     * 进货单单据编号（格式为：JHD-yyyyMMdd-xxxxx
     */
    private String id;
    /**
     * 供应商id
     */
    private Integer supplier;
    /**
     * 操作员
     */
    private String operator;
    /**
     * 备注
     */
    private String remark;
    /**
     * 总额合计
     */
    private BigDecimal totalAmount;
    /**
     * 单据状态
     */
    private PurchaseSheetState state;
    /**
     * 创建时间
     */
    private Date createTime;
}
