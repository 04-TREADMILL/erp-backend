package com.nju.edu.erp.controller;

import com.nju.edu.erp.service.FinanceService;
import com.nju.edu.erp.utils.ExcelUtil;
import com.nju.edu.erp.web.controller.FinanceController;
import org.apache.poi.ss.usermodel.Workbook;
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

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
public class FinanceControllerTest {
    @Autowired
    FinanceService financeService;

    MockMvc mockMvc;

    @BeforeEach
    void setUp(WebApplicationContext wac) {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new FinanceController(financeService)).build();
    }

    @Test
    @Transactional
    @Rollback
    public void showAccountTest() throws Exception {
        MvcResult result = this.mockMvc.perform(
                get("/finance/sale-detail").accept(MediaType.APPLICATION_JSON)
        ).andReturn();
        Assertions.assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    @Transactional
    @Rollback
    public void showAccountTestWithDate() throws Exception {
        MvcResult result = this.mockMvc.perform(
                get("/finance/sale-detail")
                        .param("from", "20220523")
                        .param("to", "20220604")
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn();
        Assertions.assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    @Transactional
    @Rollback
    public void showProfitTest() throws Exception {
        MvcResult result = this.mockMvc.perform(
                get("/finance/profit")
                        .param("from", "20220523")
                        .param("to", "20220604")
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn();
        Assertions.assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    @Transactional
    @Rollback
    public void getSaleDetailExcel() {
        Date date = new Date();
        Workbook wb = ExcelUtil.createSaleDetailWorkbook(date, financeService.fetchAllSaleDetail());
        try {
            String path = System.getProperty("user.home");
            path += "/sale-detail-snapshot.xls";
            OutputStream os = Files.newOutputStream(new File(path).toPath());
            wb.write(os);
            os.flush();
            os.close();
            System.out.println("导出库存快照excel成功!");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("导出库存快照excel失败!");
        }
    }
}
