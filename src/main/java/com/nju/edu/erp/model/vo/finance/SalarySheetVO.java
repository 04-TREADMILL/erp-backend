package com.nju.edu.erp.model.vo.finance;

import com.nju.edu.erp.enums.sheetState.SalarySheetState;
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
public class SalarySheetVO {
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
     * 应发工资
     */
    private BigDecimal originalSalary;
    /**
     * 扣除税款
     */
    private BigDecimal tax;
    /**
     * 实发工资
     */
    private BigDecimal realSalary;
    /**
     * 单据状态
     */
    private SalarySheetState state;
    /**
     * 创建时间
     */
    private Date createTime;
}
