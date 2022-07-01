package com.nju.edu.erp.web.controller;

import com.nju.edu.erp.auth.Authorized;
import com.nju.edu.erp.enums.Role;
import com.nju.edu.erp.model.vo.employee.EmployeeVO;
import com.nju.edu.erp.service.EmployeeService;
import com.nju.edu.erp.web.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) { this.employeeService = employeeService; }

    @GetMapping("/show")
    public Response showEmployees() {
        return Response.buildSuccess(employeeService.getALLEmployees());
    }

    @PostMapping("/add")
    @Authorized(roles = {Role.HR, Role.GM, Role.ADMIN})
    public Response addEmployee(@RequestBody EmployeeVO employeeVO) {
        employeeService.addEmployee(employeeVO);
        return Response.buildSuccess();
    }

    @PostMapping("/update")
    @Authorized(roles = {Role.HR, Role.GM, Role.ADMIN})
    public Response updateEmployee(@RequestBody EmployeeVO employeeVO) {
        employeeService.updateEmployee(employeeVO);
        return Response.buildSuccess();
    }

    @GetMapping("/delete")
    @Authorized(roles = {Role.ADMIN, Role.GM, Role.HR})
    public Response deleteCustomer(@RequestParam(value = "id") int id) {
        employeeService.deleteEmployeeById(id);
        return Response.buildSuccess();
    }
}
