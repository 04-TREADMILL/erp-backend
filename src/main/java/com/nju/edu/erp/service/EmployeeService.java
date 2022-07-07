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

    void setEmployeeSalaryCalculatingModeById(Integer id, String mode);

    int getPunchedTimesInLast30DaysByEmployeeId(Integer eid);

    List<EmployeePunchVO> showPunchByEmployeeId(Integer id);

    EmployeeVO getEmployeeById(Integer id);

    int getPunchTimesOfLastMonthByEmployeeId(Integer id);

    int getPunchTimesOfThisMonthByEmployeeId(Integer id);

    String getLatestPunchByEmployeeId(Integer id);
}
