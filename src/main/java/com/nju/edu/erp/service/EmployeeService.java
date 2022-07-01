package com.nju.edu.erp.service;

import com.nju.edu.erp.model.vo.employee.EmployeePunchVO;
import com.nju.edu.erp.model.vo.employee.EmployeeVO;

import java.util.List;

public interface EmployeeService {

    void addEmployee(EmployeeVO employeeVO);

    void deleteEmployeeById(Integer id);

    void updateEmployee(EmployeeVO employeeVO);

    void addPunch(EmployeePunchVO employeePunchVO);

    List<EmployeeVO> getALLEmployees();

    String getEmployeeSalaryGrantingModeById(Integer id);

    String getEmployeeSalaryCalculatingModeById(Integer id);

    int getPunchedTimesByEmployeeId(Integer eid);

    List<EmployeePunchVO> showPunchByEmployeeId(Integer id);
}
