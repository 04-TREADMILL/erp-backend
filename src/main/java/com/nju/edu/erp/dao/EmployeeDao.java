package com.nju.edu.erp.dao;

import com.nju.edu.erp.model.po.EmployeePO;
import com.nju.edu.erp.model.po.EmployeePunchPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface EmployeeDao {

    int createEmployee(EmployeePO employeePO);

    int deleteEmployeeById(Integer id);

    int updateEmployee(EmployeePO employeePO);

    List<EmployeePO> findAllEmployees();

    EmployeePO findEmployeeById(Integer id);

    List<EmployeePunchPO> getPunchByEmployeeId(Integer eid);

    int punch(EmployeePunchPO employeePunchPO);

    EmployeePunchPO getPunchById(Integer id);
}
