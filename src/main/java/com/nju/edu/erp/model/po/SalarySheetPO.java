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
public class SalarySheetPO {
    /**
     * 工资单单据编号
     */
    private String id;
    /**
     * 员工编号
     */
    private String employeeId;
    /**
     * 员工姓名
     */
    private String name;
    /**
     * 员工银行账户
     */
    private String account;
    /**
     * 员工工资
     */
    private BigDecimal salary;
}
