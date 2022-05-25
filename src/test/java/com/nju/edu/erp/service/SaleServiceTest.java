package com.nju.edu.erp.service;

import com.nju.edu.erp.dao.CustomerDao;
import com.nju.edu.erp.dao.ProductDao;
import com.nju.edu.erp.dao.SaleSheetDao;
import com.nju.edu.erp.dao.WarehouseOutputSheetDao;
import com.nju.edu.erp.enums.Role;
import com.nju.edu.erp.enums.sheetState.SaleSheetState;
import com.nju.edu.erp.enums.sheetState.WarehouseOutputSheetState;
import com.nju.edu.erp.model.po.SaleSheetContentPO;
import com.nju.edu.erp.model.po.SaleSheetPO;
import com.nju.edu.erp.model.po.WarehouseOutputSheetPO;
import com.nju.edu.erp.model.vo.Sale.SaleSheetContentVO;
import com.nju.edu.erp.model.vo.Sale.SaleSheetVO;
import com.nju.edu.erp.model.vo.UserVO;
import com.nju.edu.erp.utils.IdGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.Assert.*;

@SpringBootTest
public class SaleServiceTest { // 该测试为集成测试，需要用到数据库，请大家连给定的测试数据库进行测试

    @Autowired
    WarehouseService warehouseService;
    @Autowired
    SaleService saleService;

    @Autowired
    SaleSheetDao saleSheetDao;

    @Autowired
    ProductDao productDao;

    @Autowired
    CustomerDao customerDao;

    @Autowired
    WarehouseOutputSheetDao warehouseOutputSheetDao;



    @Test
    public void warehouseServiceTest(){
        if(warehouseService==null){
            System.out.println("service也是空的");
        }else{
            System.out.println("service不是空的");
        }
    }

    @Test
    @Transactional
    @Rollback(value = true)
    public void makeSaleSheet() { // 测试销售单是否生成成功
        UserVO userVO = UserVO.builder()
                .name("xiaoshoujingli")
                .role(Role.SALE_MANAGER)
                .build();

        List<SaleSheetContentVO> saleSheetContentVOS = new ArrayList<>();
        saleSheetContentVOS.add(SaleSheetContentVO.builder()
                .pid("0000000000400000")
                .quantity(50)
                .remark("Test1-product1")
                .unitPrice(BigDecimal.valueOf(3200))
                .build());
        saleSheetContentVOS.add(SaleSheetContentVO.builder()
                .pid("0000000000400001")
                .quantity(60)
                .remark("Test1-product2")
                .unitPrice(BigDecimal.valueOf(4200))
                .build());
        SaleSheetVO saleSheetVO = SaleSheetVO.builder()
                .saleSheetContent(saleSheetContentVOS)
                .supplier(2)
                .discount(BigDecimal.valueOf(0.8))
                .voucherAmount(BigDecimal.valueOf(300))
                .remark("Test1")
                .build();
        SaleSheetPO prevSheet = saleSheetDao.getLatestSheet();
        String realSheetId = IdGenerator.generateSheetId(prevSheet == null ? null : prevSheet.getId(), "XSD");

        saleService.makeSaleSheet(userVO, saleSheetVO);
        SaleSheetPO latestSheet = saleSheetDao.getLatestSheet();
        Assertions.assertNotNull(latestSheet);
        Assertions.assertEquals(realSheetId, latestSheet.getId());
        Assertions.assertEquals(0, latestSheet.getRawTotalAmount().compareTo(BigDecimal.valueOf(412000.00)));
        Assertions.assertEquals(0, latestSheet.getFinalAmount().compareTo(BigDecimal.valueOf(329300.00)));
        Assertions.assertEquals(SaleSheetState.PENDING_LEVEL_1, latestSheet.getState());

        String sheetId = latestSheet.getId();
        Assertions.assertNotNull(sheetId);
        List<SaleSheetContentPO> content = saleSheetDao.findContentBySheetId(sheetId);
        content.sort(Comparator.comparing(SaleSheetContentPO::getPid));
        Assertions.assertEquals(2, content.size());
        Assertions.assertEquals("0000000000400000", content.get(0).getPid());
        Assertions.assertEquals(0, content.get(0).getTotalPrice().compareTo(BigDecimal.valueOf(160000.00)));
        Assertions.assertEquals("0000000000400001", content.get(1).getPid());
        Assertions.assertEquals(0, content.get(1).getTotalPrice().compareTo(BigDecimal.valueOf(252000.00)));
    }

    @Test
    @Transactional
    @Rollback(value = true)
    public void getSaleSheetByState() { // 测试按照状态获取销售单及其content是否成功
        List<SaleSheetVO> saleSheetByState = saleService.getSaleSheetByState(SaleSheetState.PENDING_LEVEL_2);
        Assertions.assertNotNull(saleSheetByState);
        Assertions.assertEquals(1, saleSheetByState.size());
        SaleSheetVO sheet1 = saleSheetByState.get(0);
        Assertions.assertNotNull(sheet1);
        Assertions.assertEquals("XSD-20220524-00003", sheet1.getId());

        List<SaleSheetContentVO> sheet1Content = sheet1.getSaleSheetContent();
        Assertions.assertNotNull(sheet1Content);
        Assertions.assertEquals(2, sheet1Content.size());
        sheet1Content.sort(Comparator.comparing(SaleSheetContentVO::getPid));
        Assertions.assertEquals("0000000000400000", sheet1Content.get(0).getPid());
        Assertions.assertEquals(0, sheet1Content.get(0).getTotalPrice().compareTo(BigDecimal.valueOf(280000.00)));
        Assertions.assertEquals("0000000000400001", sheet1Content.get(1).getPid());
        Assertions.assertEquals(0, sheet1Content.get(1).getTotalPrice().compareTo(BigDecimal.valueOf(380000.00)));
    }

    @Test
    @Transactional
    @Rollback(value = true)
    public void approval_exceptions_1() { // 一级审批不能直接到审批完成 (提示：可以以抛出异常的方式终止流程，这样就能触发事务回滚)
        try {
            saleService.approval("XSD-20220524-00004", SaleSheetState.SUCCESS);
        } catch (Exception ignore){
        } finally {
            SaleSheetPO sheet = saleSheetDao.findSheetById("XSD-20220524-00004");
            Assertions.assertEquals(SaleSheetState.PENDING_LEVEL_1,sheet.getState());
        }
    }

    @Test
    @Transactional
    @Rollback(value = true)
    public void approval_exceptions_2() { // 二级审批不能回到一级审批
        try {
            saleService.approval("XSD-20220524-00003", SaleSheetState.PENDING_LEVEL_1);
        } catch (Exception ignore){
        } finally {
            SaleSheetPO sheet = saleSheetDao.findSheetById("XSD-20220524-00003");
            Assertions.assertEquals(SaleSheetState.PENDING_LEVEL_2,sheet.getState());
        }
    }

    @Test
    @Transactional
    @Rollback(value = true)
    public void approval_failed() { // 测试审批失败
        saleService.approval("XSD-20220524-00003", SaleSheetState.FAILURE);
        SaleSheetPO sheet = saleSheetDao.findSheetById("XSD-20220524-00003");
        Assertions.assertEquals(SaleSheetState.FAILURE,sheet.getState());
    }

    @Test
    @Transactional
    @Rollback(value = true)
    public void approval_1() { // 测试一级审批
        saleService.approval("XSD-20220524-00004", SaleSheetState.PENDING_LEVEL_2);
        SaleSheetPO sheet = saleSheetDao.findSheetById("XSD-20220524-00004");
        Assertions.assertEquals(SaleSheetState.PENDING_LEVEL_2,sheet.getState());
    }

    @Test
    @Transactional
    @Rollback(value = true)
    public void approval_2() { // 测试二级审批
        // 二级审批成功之后需要进行
        // 1. 修改单据状态
        // 2. 更新商品表
        // 3. 更新客户表
        // 4. 新建出库草稿
        saleService.approval("XSD-20220524-00003", SaleSheetState.SUCCESS);
        SaleSheetPO sheet = saleSheetDao.findSheetById("XSD-20220524-00003");
        Assertions.assertEquals(SaleSheetState.SUCCESS,sheet.getState());

        Assertions.assertEquals(0, productDao.findById("0000000000400000").getRecentRp().compareTo(BigDecimal.valueOf(2800.00)));
        Assertions.assertEquals(0, productDao.findById("0000000000400001").getRecentRp().compareTo(BigDecimal.valueOf(3800.00)));

        Assertions.assertEquals(0, customerDao.findOneById(2).getReceivable().compareTo(BigDecimal.valueOf(4959100.00)));
        List<WarehouseOutputSheetPO> draftSheets = warehouseOutputSheetDao.getDraftSheets(WarehouseOutputSheetState.DRAFT);
        Assertions.assertNotNull(draftSheets);
        Assertions.assertEquals(1, draftSheets.size());
        WarehouseOutputSheetPO draftSheet = draftSheets.get(0);
        Assertions.assertNotNull(draftSheet);
        Assertions.assertEquals("XSD-20220524-00003",draftSheet.getSaleSheetId());
    }
}