package com.nju.edu.erp.web.controller;

import com.nju.edu.erp.auth.Authorized;
import com.nju.edu.erp.enums.Role;
import com.nju.edu.erp.exception.MyServiceException;
import com.nju.edu.erp.model.vo.UserVO;
import com.nju.edu.erp.model.vo.employee.AnnualBonusVO;
import com.nju.edu.erp.model.vo.employee.EmployeePunchVO;
import com.nju.edu.erp.model.vo.employee.EmployeeVO;
import com.nju.edu.erp.service.AccountService;
import com.nju.edu.erp.service.AnnualBonusService;
import com.nju.edu.erp.service.EmployeeService;
import com.nju.edu.erp.service.UserService;
import com.nju.edu.erp.web.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping(path = "/employee")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final UserService userService;
    private final AccountService accountService;
    private final AnnualBonusService annualBonusService;

    @Autowired
    public EmployeeController(EmployeeService employeeService, UserService userService, AccountService accountService, AnnualBonusService annualBonusService) {
        this.employeeService = employeeService;
        this.userService = userService;
        this.accountService = accountService;
        this.annualBonusService = annualBonusService;
    }

    @GetMapping("/show")
    public Response showEmployees() {
        try {
            return Response.buildSuccess(employeeService.getALLEmployees());
        } catch (MyServiceException e) {
            return Response.buildFailed(e.getCode(), e.getMessage());
        } catch (Exception e) {
            return Response.buildFailed("111111", "Unknown Exception");
        }
    }

    /**
     * 增加员工
     */
    @PostMapping("/add")
    @Authorized(roles = {Role.HR, Role.GM, Role.ADMIN})
    public Response addEmployee(@RequestBody EmployeeVO employeeVO) {
        try {
            employeeService.addEmployee(employeeVO);
            UserVO userVO = UserVO.builder()
                    .name(employeeVO.getPhone())
                    .role(employeeVO.getRole())
                    .password("123456")
                    .build();
            userService.register(userVO);
            accountService.createAccount(employeeVO.getAccount(), BigDecimal.ZERO);
            return Response.buildSuccess("添加员工成功");
        } catch (MyServiceException e) {
            return Response.buildFailed(e.getCode(), e.getMessage());
        } catch (Exception e) {
            return Response.buildFailed("111111", "Unknown Exception");
        }
    }

    /**
     * 更新员工信息
     */
    @PostMapping("/update")
    @Authorized(roles = {Role.HR, Role.GM, Role.ADMIN})
    public Response updateEmployee(@RequestBody EmployeeVO employeeVO) {
        try {
            employeeService.updateEmployee(employeeVO);
            return Response.buildSuccess();
        } catch (MyServiceException e) {
            return Response.buildFailed(e.getCode(), e.getMessage());
        } catch (Exception e) {
            return Response.buildFailed("111111", "Unknown Exception");
        }
    }

    /**
     * 删除员工
     */
    @GetMapping("/delete")
    @Authorized(roles = {Role.ADMIN, Role.GM, Role.HR})
    public Response deleteEmployee(@RequestParam(value = "id") int id) {
        try {
            employeeService.deleteEmployeeById(id);
            return Response.buildSuccess();
        } catch (MyServiceException e) {
            return Response.buildFailed(e.getCode(), e.getMessage());
        } catch (Exception e) {
            return Response.buildFailed("111111", "Unknown Exception");
        }
    }

    /**
     * 添加一条打卡记录
     */
    @PostMapping("/add-punch")
    @Authorized(roles = {Role.HR, Role.GM, Role.ADMIN})
    public Response addPunch(@RequestBody EmployeePunchVO employeePunchVO) {
        try {
            employeeService.addPunch(employeePunchVO);
            return Response.buildSuccess();
        } catch (MyServiceException e) {
            return Response.buildFailed(e.getCode(), e.getMessage());
        } catch (Exception e) {
            return Response.buildFailed("111111", "Unknown Exception");
        }
    }

    /**
     * 显示某个员工近一个月的打卡记录
     */
    @GetMapping("/show-punch")
    @Authorized(roles = {Role.ADMIN, Role.GM, Role.HR})
    public Response showPunch(@RequestParam(value = "id") int id) {
        try {
            return Response.buildSuccess(employeeService.showPunchByEmployeeId(id));
        } catch (MyServiceException e) {
            return Response.buildFailed(e.getCode(), e.getMessage());
        } catch (Exception e) {
            return Response.buildFailed("111111", "Unknown Exception");
        }
    }

    @GetMapping("/show-last-month-punch")
    @Authorized(roles = {Role.ADMIN, Role.GM, Role.HR})
    public Response showPunchTimesOfLastMonth(@RequestParam(value = "id") int id) {
        try {
            return Response.buildSuccess(employeeService.getPunchTimesOfLastMonthByEmployeeId(id));
        } catch (MyServiceException e) {
            return Response.buildFailed(e.getCode(), e.getMessage());
        } catch (Exception e) {
            return Response.buildFailed("111111", "Unknown Exception");
        }
    }

    @GetMapping("/show-this-month-punch")
    @Authorized(roles = {Role.ADMIN, Role.GM, Role.HR})
    public Response showPunchTimesOfThisMonth(@RequestParam(value = "id") int id) {
        try {
            return Response.buildSuccess(employeeService.getPunchTimesOfThisMonthByEmployeeId(id));
        } catch (MyServiceException e) {
            return Response.buildFailed(e.getCode(), e.getMessage());
        } catch (Exception e) {
            return Response.buildFailed("111111", "Unknown Exception");
        }
    }

    @GetMapping("/show-latest-punch")
    @Authorized(roles = {Role.ADMIN, Role.GM, Role.HR})
    public Response showLatestPunch(@RequestParam(value = "id") int id) {
        try {
            return Response.buildSuccess(employeeService.getLatestPunchByEmployeeId(id));
        } catch (MyServiceException e) {
            return Response.buildFailed(e.getCode(), e.getMessage());
        } catch (Exception e) {
            return Response.buildFailed("111111", "Unknown Exception");
        }
    }


    /**
     * 分发年终奖
     */
    @GetMapping("/allocate-annual-bonus")
    @Authorized(roles = {Role.ADMIN, Role.GM})
    public Response allocateAnnualBonusByEmployeeId(@RequestParam(value = "id") int id,
                                                    @RequestParam(value = "extraBonus") BigDecimal extraBonus) {
        try {
            EmployeeVO employeeVO = employeeService.getEmployeeById(id);
            AnnualBonusVO annualBonusVO = annualBonusService.addAnnualBonus(id, employeeVO.getRole(), extraBonus);
            BigDecimal totalBonus = annualBonusVO.getBaseBonus().add(annualBonusVO.getExtraBonus());
            accountService.increaseAccountAmount(employeeVO.getAccount(), totalBonus);
            return Response.buildSuccess();
        } catch (MyServiceException e) {
            return Response.buildFailed(e.getCode(), e.getMessage());
        } catch (Exception e) {
            return Response.buildFailed("111111", "Unknown Exception");
        }
    }

    /**
     * 查看年终奖
     */
    @GetMapping("/show-annual-bonus")
    @Authorized(roles = {Role.ADMIN, Role.GM})
    public Response showAnnualBonusByEmployeeId(@RequestParam(value = "id", required = false) Integer id) {
        try {
            return Response.buildSuccess(annualBonusService.getAnnualBonusByEmployeeId(id));
        } catch (MyServiceException e) {
            return Response.buildFailed(e.getCode(), e.getMessage());
        } catch (Exception e) {
            return Response.buildFailed("111111", "Unknown Exception");
        }
    }

    /**
     * 修改员工薪酬规则
     * */
    @GetMapping("/change-salary-calculating-mode")
    @Authorized(roles = {Role.ADMIN, Role.GM, Role.HR})
    public Response changeSalaryCalculatingModeByEmployeeId(@RequestParam(value = "id") int id,
                                                            @RequestParam(value = "mode") String mode) {
        try {
            employeeService.setEmployeeSalaryCalculatingModeById(id, mode);
            return Response.buildSuccess();
        } catch (MyServiceException e) {
            return Response.buildFailed(e.getCode(), e.getMessage());
        } catch (Exception e) {
            return Response.buildFailed("111111", "Unknown Exception");
        }
    }
}
