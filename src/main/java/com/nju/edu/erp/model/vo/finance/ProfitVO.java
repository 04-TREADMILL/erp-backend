package com.nju.edu.erp.model.vo.finance;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfitVO {
    /**
     * 折让前总收入
     */
    private BigDecimal incomingRaw;
    /**
     * 折让后总收入
     */
    private BigDecimal incomingReal;
    /**
     * 销售支出
     */
    private BigDecimal outgoingPurchase;
    /**
     * 人力支出
     */
    private BigDecimal outgoingHuman;
    /**
     * 利润
     */
    private BigDecimal profit;
}
