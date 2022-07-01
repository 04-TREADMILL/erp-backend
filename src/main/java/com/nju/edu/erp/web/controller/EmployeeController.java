package com.nju.edu.erp.web.controller;

import com.nju.edu.erp.auth.Authorized;
import com.nju.edu.erp.enums.Role;
import com.nju.edu.erp.model.vo.UserVO;
import com.nju.edu.erp.model.vo.employee.EmployeePunchVO;
import com.nju.edu.erp.model.vo.employee.EmployeeVO;
import com.nju.edu.erp.service.EmployeeService;
import com.nju.edu.erp.service.UserService;
import com.nju.edu.erp.web.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/employee")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final UserService userService;

    @Autowired
    public EmployeeController(EmployeeService employeeService, UserService userService) {
        this.employeeService = employeeService;
        this.userService = userService;
    }

    @GetMapping("/show")
    public Response showEmployees() {
        return Response.buildSuccess(employeeService.getALLEmployees());
    }

    /**
     * 增加员工
     * */
    @PostMapping("/add")
    @Authorized(roles = {Role.HR, Role.GM, Role.ADMIN})
    public Response addEmployee(@RequestBody EmployeeVO employeeVO) {
        employeeService.addEmployee(employeeVO);
        UserVO userVO = UserVO.builder()
                .name(employeeVO.getPhone())
                .role(employeeVO.getRole())
                .password("123456")
                .build();
        userService.register(userVO);
        return Response.buildSuccess();
    }

    /**
     * 更新员工信息
     * */
    @PostMapping("/update")
    @Authorized(roles = {Role.HR, Role.GM, Role.ADMIN})
    public Response updateEmployee(@RequestBody EmployeeVO employeeVO) {
        employeeService.updateEmployee(employeeVO);
        return Response.buildSuccess();
    }

    /**
     * 删除员工
     * */
    @GetMapping("/delete")
    @Authorized(roles = {Role.ADMIN, Role.GM, Role.HR})
    public Response deleteEmployee(@RequestParam(value = "id") int id) {
        employeeService.deleteEmployeeById(id);
        return Response.buildSuccess();
    }

    /**
     * 添加一条打卡记录
     * */
    @PostMapping("/add-punch")
    @Authorized(roles = {Role.HR, Role.GM, Role.ADMIN})
    public Response addPunch(@RequestBody EmployeePunchVO employeePunchVO) {
        employeeService.addPunch(employeePunchVO);
        return Response.buildSuccess();
    }

    /**
     * 显示某个员工的打卡记录
     * */
    @GetMapping("/show-punch")
    @Authorized(roles = {Role.ADMIN, Role.GM, Role.HR})
    public Response showPunch(@RequestParam(value = "id") int id) {
        return Response.buildSuccess(employeeService.showPunchByEmployeeId(id));
    }
}
