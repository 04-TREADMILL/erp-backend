package com.nju.edu.erp.controller;

import com.alibaba.fastjson.JSONObject;
import com.nju.edu.erp.model.vo.promotion.CombinePromotionVO;
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
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

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
                post("/promotion/add-total")
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
                post("/promotion/add-customer")
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
        CombinePromotionVO promotionVO = CombinePromotionVO.childBuilder()
                .pidList(Arrays.asList("0000000000400000 0000000000400001".split(" ")))
                .amount(BigDecimal.valueOf(2000))
                .beginTime(new Date())
                .endTime(new Date())
                .build();
        String promotionVOJSONStr = JSONObject.toJSONString(promotionVO);
        MvcResult result = this.mockMvc.perform(
                post("/promotion/add-combine")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(promotionVOJSONStr)
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn();
        String responseJSONStr = result.getResponse().getContentAsString();
        Response response = JSONObject.parseObject(responseJSONStr, Response.class);
        Assertions.assertEquals("Success", response.getMsg());
    }

//    @Test
//    @Transactional
//    @Rollback
//    public void addPromotionTest3() throws Exception {
//        TotalPromotionVO promotionVO = TotalPromotionVO.childBuilder()
//                .condition(BigDecimal.valueOf(5000))
//                .amount(BigDecimal.valueOf(200))
//                .beginTime(new Date())
//                .endTime(new Date())
//                .build();
//        String promotionVOJSONStr = JSONObject.toJSONString(promotionVO);
//        MvcResult result = this.mockMvc.perform(
//                post("/promotion/add")
//                        .param("promotionType", "xxx")
//                        .contentType(MediaType.APPLICATION_JSON_VALUE)
//                        .content(promotionVOJSONStr)
//                        .accept(MediaType.APPLICATION_JSON)
//        ).andReturn();
//        String responseJSONStr = result.getResponse().getContentAsString();
//        Response response = JSONObject.parseObject(responseJSONStr, Response.class);
//        Assertions.assertEquals("unknown promotion type", response.getMsg());
//    }

    @Test
    @Transactional
    @Rollback
    public void deletePromotionTest1() throws Exception {
        MvcResult result = this.mockMvc.perform(
                get("/promotion/delete")
                        .param("promotionType", "customer")
                        .param("promotionId", "12")
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
    public void deletePromotionTest5() throws Exception {
        MvcResult result = this.mockMvc.perform(
                get("/promotion/delete")
                        .param("promotionType", "combine")
                        .param("promotionId", "3")
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn();
        String responseJSONStr = result.getResponse().getContentAsString();
        Response response = JSONObject.parseObject(responseJSONStr, Response.class);
        Assertions.assertEquals("Success", response.getMsg());
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
}
