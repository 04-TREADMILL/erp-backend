package com.nju.edu.erp.model.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountPO {
    /**
     * 账户名称
     */
    private String name;
    /**
     * 账户金额
     */
    private BigDecimal amount;
}
