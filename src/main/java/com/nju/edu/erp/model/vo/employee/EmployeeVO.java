package com.nju.edu.erp.model.vo.employee;

import com.nju.edu.erp.enums.Role;
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
public class EmployeeVO {

    /**
     * 员工id
     */
    private Integer id;

    /**
     * 员工姓名
     */
    private String name;

    /**
     * 员工性别
     */
    private String gender;

    /**
     * 员工生日
     */
    private Date birthday;

    /**
     * 员工电话号码
     */
    private String phone;

    /**
     * 员工岗位
     */
    private Role role;

    /**
     * 员工基本工资
     */
    private BigDecimal basicSalary;

    /**
     * 员工岗位工资
     */
    private BigDecimal postSalary;

    /**
     * 员工薪资发放方式
     */
    private String salaryGrantingMode;

    /**
     * 员工薪资计算方式
     */
    private String salaryCalculatingMode;

    /**
     * 员工账户
     */
    private String account;
}
