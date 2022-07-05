package com.nju.edu.erp.service.Impl;

import com.nju.edu.erp.dao.EmployeeDao;
import com.nju.edu.erp.exception.MyServiceException;
import com.nju.edu.erp.model.po.EmployeePO;
import com.nju.edu.erp.model.po.EmployeePunchPO;
import com.nju.edu.erp.model.vo.employee.EmployeePunchVO;
import com.nju.edu.erp.model.vo.employee.EmployeeVO;
import com.nju.edu.erp.service.EmployeeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeDao employeeDao;

    @Autowired
    public EmployeeServiceImpl(EmployeeDao employeeDao) { this.employeeDao = employeeDao; }

    @Override
    public void addEmployee(EmployeeVO employeeVO) {
        EmployeePO employeeSave = new EmployeePO();
        BeanUtils.copyProperties(employeeVO, employeeSave);
        employeeDao.createEmployee(employeeSave);
    }

    @Override
    public void deleteEmployeeById(Integer id) {
        EmployeePO employeePO = employeeDao.findEmployeeById(id);
        if (employeePO != null) {
            employeeDao.deleteEmployeeById(id);
        }
    }

    @Override
    public void updateEmployee(EmployeeVO employeeVO) {
        EmployeePO employeePO = employeeDao.findEmployeeById(employeeVO.getId());
        if (employeePO == null) {
            throw new MyServiceException("A0003", "员工不存在");
        }
        EmployeePO employeeUpdate = new EmployeePO();
        BeanUtils.copyProperties(employeeVO, employeeUpdate);
        employeeDao.updateEmployee(employeeUpdate);
    }

    @Override
    public void addPunch(EmployeePunchVO employeePunchVO) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String date = format.format(employeePunchVO.getPunchTime());
        List<EmployeePunchPO> employeePunchPOS = employeeDao.getPunchByEmployeeId(employeePunchVO.getEid());
        for (EmployeePunchPO punchPO : employeePunchPOS) {
            String punchDate = format.format(punchPO.getPunchTime());
            if (date.equals(punchDate)) {
                throw new MyServiceException("A0004", "员工"+employeePunchVO.getEid()+"今日已打卡");
            }
        }
        EmployeePunchPO employeePunchSave = new EmployeePunchPO();
        BeanUtils.copyProperties(employeePunchVO, employeePunchSave);
        employeeDao.punch(employeePunchSave);
    }

    @Override
    public List<EmployeeVO> getALLEmployees() {
        List<EmployeePO> employeePOS = employeeDao.findAllEmployees();
        List<EmployeeVO> employeeVOS = new ArrayList<>();
        for (EmployeePO employeePO : employeePOS) {
            EmployeeVO employeeVO = new EmployeeVO();
            BeanUtils.copyProperties(employeePO, employeeVO);
            employeeVOS.add(employeeVO);
        }
        return employeeVOS;
    }

    @Override
    public String getEmployeeSalaryGrantingModeById(Integer id) {
        EmployeePO employeePO = employeeDao.findEmployeeById(id);
        return employeePO.getSalaryGrantingMode();
    }

    @Override
    public String getEmployeeSalaryCalculatingModeById(Integer id) {
        EmployeePO employeePO = employeeDao.findEmployeeById(id);
        return employeePO.getSalaryCalculatingMode();
    }

    @Override
    public int getPunchedTimesInLast30DaysByEmployeeId(Integer eid) {
        List<EmployeePunchPO> employeePunchPOS = employeeDao.getPunchByEmployeeId(eid);
        int times = 0;
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        for (EmployeePunchPO po : employeePunchPOS) {
            String punchDateStr = format.format(po.getPunchTime());
            if (inLast30Days(punchDateStr, format.format(new Date()))) {
                ++times;
            }
        }
        return times;
    }

    private boolean inLast30Days(String punchDate, String today) {
        int year = Integer.parseInt(today.substring(0, 4));
        int month = Integer.parseInt(today.substring(4, 6));
        int day = Integer.parseInt(today.substring(6, 8)) - 29;
        if (day <= 0) {
            --month;
            if (month == 0) {
                --year;
                month = 12;
            }
            if (month == 4 || month == 6 || month == 9 || month == 11) {
                day += 30;
            } else if (month == 2) {
                if ((year % 4 == 0 && year % 100 == 0 && year % 400 == 0)
                        || (year % 4 == 0 && year % 100 != 0)) {
                    day += 29;
                } else {
                    day += 28;
                }
            }
        }
        String yearStr = String.valueOf(year);
        String monthStr = String.valueOf(month);
        monthStr = monthStr.length() == 1 ? "0" + monthStr : monthStr;
        String dayStr = String.valueOf(day);
        dayStr = dayStr.length() == 1 ? "0" + dayStr : dayStr;
        int left = Integer.parseInt(yearStr + monthStr + dayStr);
        int right = Integer.parseInt(today);
        int punch = Integer.parseInt(punchDate);
        return punch >= left && punch <= right;
    }

    @Override
    public List<EmployeePunchVO> showPunchByEmployeeId(Integer eid) {
        List<EmployeePunchPO> employeePunchPOS = employeeDao.getPunchByEmployeeId(eid);
        List<EmployeePunchVO> employeePunchVOS = new ArrayList<>();
        for (EmployeePunchPO employeePunchPO : employeePunchPOS) {
            EmployeePunchVO employeePunchVO = new EmployeePunchVO();
            BeanUtils.copyProperties(employeePunchPO, employeePunchVO);
            employeePunchVOS.add(employeePunchVO);
        }
        return employeePunchVOS;
    }
}
