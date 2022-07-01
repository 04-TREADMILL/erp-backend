package com.nju.edu.erp.model.vo.employee;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeePunchVO {

    /**
     * 打卡记录id
     */
    private Integer id;

    /**
     * 打卡员工id
     */
    private Integer eid;

    /**
     * 打卡时间
     */
    private Date punchTime;
}
