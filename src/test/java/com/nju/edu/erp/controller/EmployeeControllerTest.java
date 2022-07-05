package com.nju.edu.erp.controller;

import com.alibaba.fastjson.JSONObject;
import com.nju.edu.erp.enums.Role;
import com.nju.edu.erp.model.vo.employee.EmployeePunchVO;
import com.nju.edu.erp.model.vo.employee.EmployeeVO;
import com.nju.edu.erp.service.AccountService;
import com.nju.edu.erp.service.AnnualBonusService;
import com.nju.edu.erp.service.EmployeeService;
import com.nju.edu.erp.service.UserService;
import com.nju.edu.erp.web.Response;
import com.nju.edu.erp.web.controller.EmployeeController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.math.BigDecimal;
import java.util.Date;

@SpringBootTest
public class EmployeeControllerTest {

    @Autowired
    EmployeeService employeeService;
    @Autowired
    UserService userService;
    @Autowired
    AccountService accountService;
    @Autowired
    AnnualBonusService annualBonusService;

    MockMvc mockMvc;

    @BeforeEach
    void setUp(WebApplicationContext wac) {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new EmployeeController(employeeService, userService, accountService, annualBonusService)).build();
    }

    @Test
    @Transactional
    @Rollback
    public void showEmployeesTest() throws Exception {
        MvcResult result = this.mockMvc.perform(
                get("/employee/show").accept(MediaType.APPLICATION_JSON)
        ).andReturn();
        String responseJSONStr = result.getResponse().getContentAsString();
        Response response = JSONObject.parseObject(responseJSONStr, Response.class);
        Assertions.assertEquals("Success", response.getMsg());
    }

    @Test
    @Transactional
    @Rollback
    public void addEmployeeTest() throws Exception {
        EmployeeVO employeeVO = EmployeeVO.builder()
                .name("赵四")
                .gender("男")
                .birthday(new Date())
                .phone("13323332000")
                .role(Role.SALE_STAFF)
                .basicSalary(BigDecimal.valueOf(3000))
                .postSalary(BigDecimal.valueOf(4000))
                .salaryGrantingMode("month")
                .salaryCalculatingMode("default")
                .account("abcdefg")
                .build();
        String employeeJSONStr = JSONObject.toJSONString(employeeVO);
        MvcResult result = this.mockMvc.perform(
                post("/employee/add")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(employeeJSONStr)
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn();
        String responseJSONStr = result.getResponse().getContentAsString();
        Response response = JSONObject.parseObject(responseJSONStr, Response.class);
        Assertions.assertEquals("00000", response.getCode());
    }

    @Test
    @Transactional
    @Rollback
    public void deleteEmployeeTest() throws Exception {
        MvcResult result = this.mockMvc.perform(
                get("/employee/delete")
                        .param("id", String.valueOf(17))
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn();
        String responseJSONStr = result.getResponse().getContentAsString();
        Response response = JSONObject.parseObject(responseJSONStr, Response.class);
        Assertions.assertEquals("Success", response.getMsg());
    }

    @Test
    @Transactional
    @Rollback
    public void updateEmployeeTest() throws Exception {
        EmployeeVO employeeVO = EmployeeVO.builder()
                .id(17)
                .name("lyc")
                .birthday(new Date())
                .phone("120")
                .role(Role.SALE_STAFF)
                .basicSalary(BigDecimal.valueOf(1000))
                .postSalary(BigDecimal.valueOf(200))
                .salaryGrantingMode("year")
                .salaryCalculatingMode("超级加倍")
                .build();
        String employeeJSONStr = JSONObject.toJSONString(employeeVO);
        MvcResult result = this.mockMvc.perform(
                post("/employee/update")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(employeeJSONStr)
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn();
        String responseJSONStr = result.getResponse().getContentAsString();
        Response response = JSONObject.parseObject(responseJSONStr, Response.class);
        Assertions.assertEquals("Success", response.getMsg());
    }

    @Test
    @Transactional
    @Rollback
    public void punchTest() throws Exception {
        EmployeePunchVO punchVO = EmployeePunchVO.builder()
                .eid(17)
                .punchTime(new Date())
                .build();
        String punchVOJSONStr = JSONObject.toJSONString(punchVO);
        MvcResult result = this.mockMvc.perform(
                post("/employee/add-punch")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(punchVOJSONStr)
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn();
        String responseJSONStr = result.getResponse().getContentAsString();
        Response response = JSONObject.parseObject(responseJSONStr, Response.class);
        Assertions.assertEquals("Success", response.getMsg());
    }

    @Test
    @Transactional
    @Rollback
    public void showPunchTest() throws Exception {
        MvcResult result = this.mockMvc.perform(
                get("/employee/show-punch")
                        .param("id", String.valueOf(17))
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn();
        String responseJSONStr = result.getResponse().getContentAsString();
        Response response = JSONObject.parseObject(responseJSONStr, Response.class);
        Assertions.assertEquals("Success", response.getMsg());
    }

    @Test
    public void getLatestPunchTimesTest() {
        int times = employeeService.getPunchedTimesInLast30DaysByEmployeeId(17);
        Assertions.assertEquals(1, times);
    }

    @Test
    @Transactional
    @Rollback
    public void addAnnualBonusTest() throws Exception {
        MvcResult result = this.mockMvc.perform(
                get("/employee/allocate-annual-bonus")
                        .param("id", String.valueOf(59))
                        .param("extraBonus", String.valueOf(10000))
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn();
        String responseJSONStr = result.getResponse().getContentAsString();
        Response response = JSONObject.parseObject(responseJSONStr, Response.class);
        Assertions.assertEquals("Success", response.getMsg());
    }
}
