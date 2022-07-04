package com.nju.edu.erp.controller;

import com.alibaba.fastjson.JSONObject;
import com.nju.edu.erp.model.vo.promotion.CustomerPromotionVO;
import com.nju.edu.erp.model.vo.promotion.TotalPromotionVO;
import com.nju.edu.erp.service.strategy.PromotionStrategy;
import com.nju.edu.erp.web.Response;
import com.nju.edu.erp.web.controller.PromotionController;
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
import java.util.Date;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
public class PromotionControllerTest {

    @Autowired
    PromotionStrategy promotionStrategy;

    MockMvc mockMvc;

    @BeforeEach
    void setUp(WebApplicationContext wac) {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new PromotionController(promotionStrategy)).build();
    }

    @Test
    @Transactional
    @Rollback
    public void addPromotionTest1() throws Exception {
        TotalPromotionVO promotionVO = TotalPromotionVO.childBuilder()
                .condition(BigDecimal.valueOf(5000))
                .amount(BigDecimal.valueOf(200))
                .beginTime(new Date())
                .endTime(new Date())
                .build();
        String promotionVOJSONStr = JSONObject.toJSONString(promotionVO);
        MvcResult result = this.mockMvc.perform(
                post("/promotion/add")
                        .param("promotionType", "total")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(promotionVOJSONStr)
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn();
        String responseJSONStr = result.getResponse().getContentAsString();
        Response response = JSONObject.parseObject(responseJSONStr, Response.class);
        Assertions.assertEquals("Success", response.getMsg());
    }

    @Test
    @Transactional
    @Rollback
    public void addPromotionTest2() throws Exception {
        CustomerPromotionVO promotionVO = CustomerPromotionVO.childBuilder()
                .level(2)
                .discount(BigDecimal.valueOf(0.9))
                .amount(BigDecimal.valueOf(200))
                .beginTime(new Date())
                .endTime(new Date())
                .build();
        String promotionVOJSONStr = JSONObject.toJSONString(promotionVO);
        MvcResult result = this.mockMvc.perform(
                post("/promotion/add")
                        .param("promotionType", "customer")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(promotionVOJSONStr)
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn();
        String responseJSONStr = result.getResponse().getContentAsString();
        Response response = JSONObject.parseObject(responseJSONStr, Response.class);
        Assertions.assertEquals("Success", response.getMsg());
    }

    @Test
    @Transactional
    @Rollback
    public void addPromotionTest3() throws Exception {
        TotalPromotionVO promotionVO = TotalPromotionVO.childBuilder()
                .condition(BigDecimal.valueOf(5000))
                .amount(BigDecimal.valueOf(200))
                .beginTime(new Date())
                .endTime(new Date())
                .build();
        String promotionVOJSONStr = JSONObject.toJSONString(promotionVO);
        MvcResult result = this.mockMvc.perform(
                post("/promotion/add")
                        .param("promotionType", "xxx")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(promotionVOJSONStr)
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn();
        String responseJSONStr = result.getResponse().getContentAsString();
        Response response = JSONObject.parseObject(responseJSONStr, Response.class);
        Assertions.assertEquals("unknown promotion type", response.getMsg());
    }

    @Test
    @Transactional
    @Rollback
    public void deletePromotionTest1() throws Exception {
        MvcResult result = this.mockMvc.perform(
                get("/promotion/delete")
                        .param("promotionType", "customer")
                        .param("promotionId", "22")
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn();
        String responseJSONStr = result.getResponse().getContentAsString();
        Response response = JSONObject.parseObject(responseJSONStr, Response.class);
        Assertions.assertEquals("Success", response.getMsg());
    }

    @Test
    @Transactional
    @Rollback
    public void deletePromotionTest2() throws Exception {
        MvcResult result = this.mockMvc.perform(
                get("/promotion/delete")
                        .param("promotionType", "total")
                        .param("promotionId", "11")
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn();
        String responseJSONStr = result.getResponse().getContentAsString();
        Response response = JSONObject.parseObject(responseJSONStr, Response.class);
        Assertions.assertEquals("Success", response.getMsg());
    }

    @Test
    @Transactional
    @Rollback
    public void deletePromotionTest3() throws Exception {
        MvcResult result = this.mockMvc.perform(
                get("/promotion/delete")
                        .param("promotionType", "total")
                        .param("promotionId", "100")
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn();
        String responseJSONStr = result.getResponse().getContentAsString();
        Response response = JSONObject.parseObject(responseJSONStr, Response.class);
        Assertions.assertEquals("123456", response.getCode());
    }

    @Test
    @Transactional
    @Rollback
    public void deletePromotionTest4() throws Exception {
        MvcResult result = this.mockMvc.perform(
                get("/promotion/delete")
                        .param("promotionType", "???")
                        .param("promotionId", "3")
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn();
        String responseJSONStr = result.getResponse().getContentAsString();
        Response response = JSONObject.parseObject(responseJSONStr, Response.class);
        Assertions.assertEquals("unknown promotion type", response.getMsg());
    }


    @Test
    @Transactional
    @Rollback
    public void showPromotionsTest() throws Exception {
        MvcResult result = this.mockMvc.perform(
                get("/promotion/show")
                        .param("promotionType", "total")
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn();
        String responseJSONStr = result.getResponse().getContentAsString();
        Response response = JSONObject.parseObject(responseJSONStr, Response.class);
        Assertions.assertEquals("Success", response.getMsg());
        System.out.println(response.getResult());
        List<Object> resultList = (List<Object>) response.getResult();
        Assertions.assertEquals(1, resultList.size());
    }

    @Test
    public void getOnePromotionTest() {
        Object obj1 = promotionStrategy.getOnePromotionByType("total", "4000");
        Object obj2 = promotionStrategy.getOnePromotionByType("total", "7000");
        Assertions.assertNull(obj1);
        Assertions.assertNotNull(obj2);
        Object obj3 = promotionStrategy.getOnePromotionByType("customer", "3");
        Object obj4 = promotionStrategy.getOnePromotionByType("customer", "2");
        Assertions.assertNull(obj3);
        Assertions.assertNotNull(obj4);
    }
}
