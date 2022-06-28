package com.nju.edu.erp.service;


import com.nju.edu.erp.model.po.WarehouseIODetailPO;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import static com.nju.edu.erp.utils.ExcelUtil.createWorkbook;


@SpringBootTest
public class WarehouseServiceTest {

    @Autowired
    WarehouseService warehouseService;

    @Test
    @Transactional
    @Rollback
    public void getWarehouseIODetailByTime_TimeError() throws ParseException { //测试库存查看，当开始时间大于结束时间时，库存查看的结果应为null
        List<WarehouseIODetailPO> warehouseIODetailPOList = warehouseService.getWarehouseIODetailByTime("2022-05-25 00:00:00", "2022-05-24 00:00:00");
        Assertions.assertNull(warehouseIODetailPOList);
    }

    @Test
    @Transactional
    @Rollback
    public void getWarehouseIODetailByTime_EmptyResult() throws ParseException { //测试库存查看，指定时间段内没有出入库记录，库存查看的结果应为空数组[]
        List<WarehouseIODetailPO> warehouseIODetailPOList = warehouseService.getWarehouseIODetailByTime("2022-05-25 00:00:00", "2022-05-25 23:59:59");
        Assertions.assertNotNull(warehouseIODetailPOList);
        Assertions.assertEquals(0, warehouseIODetailPOList.size());

    }

    @Test
    @Transactional
    @Rollback
    public void getWarehouseIODetailByTime() throws ParseException { //测试库存查看,有记录的情况
        List<WarehouseIODetailPO> warehouseIODetailPOList = warehouseService.getWarehouseIODetailByTime("2022-05-23 23:17:40", "2022-05-24 00:33:12");
        Assertions.assertNotNull(warehouseIODetailPOList);
        Assertions.assertEquals(10, warehouseIODetailPOList.size());

    }

    @Test
    @Transactional
    @Rollback
    public void getWarehouseInputProductQuantityByTime_TimeError() { //测试查看一段时间内入库商品的数量,当开始时间大于结束时间时，数量应为0
        int quantity = warehouseService.getWarehouseInputProductQuantityByTime("2022-05-25 00:00:00", "2022-05-24 00:00:00");
        Assertions.assertEquals(0, quantity);
    }

    @Test
    @Transactional
    @Rollback
    public void getWarehouseInputProductQuantityByTime_EmptyResult() { //测试查看一段时间内入库商品的数量,当指定时间段没有入库商品时，数量应为0
        int quantity = warehouseService.getWarehouseInputProductQuantityByTime("2022-05-25 00:00:00", "2022-05-25 23:59:59");
        Assertions.assertEquals(0, quantity);
    }

    @Test
    @Transactional
    @Rollback
    public void getWarehouseInputProductQuantityByTime() { //测试查看一段时间内入库商品的数量,这段时间内有入库商品的情况
        int quantity = warehouseService.getWarehouseInputProductQuantityByTime("2022-05-23 23:17:40", "2022-05-24 00:33:12");
        Assertions.assertEquals(4000, quantity);
    }

    @Test
    @Transactional
    @Rollback
    public void getWarehouseOutProductQuantityByTime_TimeError() { //测试查看一段时间内出库商品的数量,当开始时间大于结束时间时，数量应为0
        int quantity = warehouseService.getWarehouseOutProductQuantityByTime("2022-05-25 00:00:00", "2022-05-24 00:00:00");
        Assertions.assertEquals(0, quantity);
    }

    @Test
    @Transactional
    @Rollback
    public void getWarehouseOutProductQuantityByTime_EmptyResult() { //测试查看一段时间内出库商品的数量,当指定时间段没有入库商品时，数量应为0
        int quantity = warehouseService.getWarehouseOutProductQuantityByTime("2022-05-25 00:00:00", "2022-05-25 23:59:59");
        Assertions.assertEquals(0, quantity);
    }

    @Test
    @Transactional
    @Rollback
    public void getWarehouseOutProductQuantityByTime() {  //测试查看一段时间内出库商品的数量,这段时间内有出库商品的情况
        int quantity = warehouseService.getWarehouseOutProductQuantityByTime("2022-05-23 23:17:40", "2022-05-24 00:33:12");
        Assertions.assertEquals(2000, quantity);
    }

    @Test
    public void getExcel() {
        Date date = new Date();
        Workbook wb = createWorkbook(date, warehouseService.warehouseCounting());
        try {
            OutputStream os = Files.newOutputStream(new File("/home/vgalaxy/Downloads/test.xls").toPath());
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
