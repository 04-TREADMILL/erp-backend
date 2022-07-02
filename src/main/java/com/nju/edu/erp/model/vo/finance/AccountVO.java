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
public class AccountVO {
    /**
     * 账户名称
     */
    private String name;
    /**
     * 账户金额
     */
    private BigDecimal amount;
}
