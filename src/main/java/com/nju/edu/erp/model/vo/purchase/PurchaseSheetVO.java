package com.nju.edu.erp.model.vo.purchase;


import com.nju.edu.erp.enums.sheetState.PurchaseSheetState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PurchaseSheetVO {
    /**
     * 进货单单据编号（格式为：JHD-yyyyMMdd-xxxxx), 新建单据时前端传null
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
     * 总额合计, 新建单据时前端传null(在后端计算总金额
     */
    private BigDecimal totalAmount;
    /**
     * 单据状态, 新建单据时前端传null
     */
    private PurchaseSheetState state;
    /**
     * 进货单具体内容
     */
    List<PurchaseSheetContentVO> purchaseSheetContent;
}
