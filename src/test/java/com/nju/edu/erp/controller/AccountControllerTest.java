package com.nju.edu.erp.controller;

import com.alibaba.fastjson.JSONObject;
import com.nju.edu.erp.model.vo.finance.AccountVO;
import com.nju.edu.erp.service.AccountService;
import com.nju.edu.erp.web.controller.AccountController;
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

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
public class AccountControllerTest {
    @Autowired
    AccountService accountService;

    MockMvc mockMvc;

    @BeforeEach
    void setUp(WebApplicationContext wac) {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new AccountController(accountService)).build();
    }

    @Test
    @Transactional
    @Rollback
    public void showAccountTest() throws Exception {
        MvcResult result = this.mockMvc.perform(
                get("/account/show").accept(MediaType.APPLICATION_JSON)
        ).andReturn();
        Assertions.assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    @Transactional
    @Rollback
    public void getAccountFzfTest() throws Exception {
        MvcResult result = this.mockMvc.perform(
                get("/account/get-fzf")
                        .param("key", "c")
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn();
        Assertions.assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    @Transactional
    @Rollback
    public void addAccountTest() throws Exception {
        AccountVO accountVO = AccountVO.builder()
                .name("test")
                .amount(BigDecimal.ZERO)
                .build();
        String jsonString = JSONObject.toJSONString(accountVO);
        MvcResult result = this.mockMvc.perform(
                post("/account/add")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(jsonString)
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn();
        Assertions.assertEquals(200, result.getResponse().getStatus());
    }
}
