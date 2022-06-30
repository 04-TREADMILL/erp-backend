package com.nju.edu.erp.model.vo;

import com.nju.edu.erp.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoVO {
    private String name;
    private String gender;
    private Date birthday;
    private String phone;
    private Role role;
    private BigDecimal basicSalary;
    private String account;
}
