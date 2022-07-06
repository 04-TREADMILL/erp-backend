package com.nju.edu.erp.utils;

import com.nju.edu.erp.model.vo.finance.SaleDetailVO;
import com.nju.edu.erp.model.vo.product.ProductInfoVO;
import com.nju.edu.erp.model.vo.warehouse.WarehouseCountingVO;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class ExcelUtil {

    public static void exportSaleDetailExcel(HttpServletResponse response, List<SaleDetailVO> items) {
        Date date = new Date();
        Workbook wb = createSaleDetailWorkbook(date, items);
        try {
            response.reset();
            response.setHeader("Content-Disposition", "attachment;filename=sale-detail-snapshot");
            OutputStream os = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/vnd.ms-excel;charset=gb2312");
            wb.write(os);
            os.flush();
            os.close();
            System.out.println("导出库存快照excel成功!");

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("导出库存快照excel失败!");
        }
    }

    public static Workbook createSaleDetailWorkbook(Date date, List<SaleDetailVO> items) {
        Workbook wb = new HSSFWorkbook();
        Sheet sheet = wb.createSheet("sale-detail-snapshot-" + date.toString());
        Row titleRow = sheet.createRow(0);
        titleRow.createCell(0).setCellValue("时间");
        titleRow.createCell(1).setCellValue("商品名");
        titleRow.createCell(2).setCellValue("商品型号");
        titleRow.createCell(3).setCellValue("商品数量");
        titleRow.createCell(4).setCellValue("商品单价");
        titleRow.createCell(5).setCellValue("商品金额");
        titleRow.createCell(6).setCellValue("业务员");
        titleRow.createCell(7).setCellValue("客户编号");
        for (int i = 0; i < items.size(); ++i) {
            SaleDetailVO item = items.get(i);
            Row row = sheet.createRow(i + 1);
            row.createCell(0).setCellValue(item.getTime() == null ? "" : item.getTime());
            row.createCell(1).setCellValue(item.getName() == null ? "" : item.getName());
            row.createCell(2).setCellValue(item.getType() == null ? "" : item.getType());
            row.createCell(3).setCellValue(item.getQuantity() == null ? "" : item.getQuantity().toString());
            row.createCell(4).setCellValue(item.getUnitPrice() == null ? "" : item.getUnitPrice().toString());
            row.createCell(5).setCellValue(item.getTotalPrice() == null ? "" : item.getTotalPrice().toString());
            row.createCell(6).setCellValue(item.getSalesman() == null ? "" : item.getSalesman());
            row.createCell(7).setCellValue(item.getSeller() == null ? "" : item.getSeller().toString());
        }
        return wb;
    }

    public static void exportWarehouseExcel(HttpServletResponse response, List<WarehouseCountingVO> items) {
        Date date = new Date();
        Workbook wb = createWarehouseWorkbook(date, items);
        try {
            response.reset();
            response.setHeader("Content-Disposition", "attachment;filename=warehouse-snapshot");
            OutputStream os = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/vnd.ms-excel;charset=gb2312");
            wb.write(os);
            os.flush();
            os.close();
            System.out.println("导出库存快照excel成功!");

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("导出库存快照excel失败!");
        }
    }

    public static Workbook createWarehouseWorkbook(Date date, List<WarehouseCountingVO> items) {
        Workbook wb = new HSSFWorkbook();
        Sheet sheet = wb.createSheet("warehouse-snapshot-" + date.toString());
        Row titleRow = sheet.createRow(0);
        Cell titleCell = titleRow.createCell(0);
        titleCell.setCellValue("库存id");
        titleCell = titleRow.createCell(1);
        titleCell.setCellValue("商品编号");
        titleCell = titleRow.createCell(2);
        titleCell.setCellValue("数量");
        titleCell = titleRow.createCell(3);
        titleCell.setCellValue("进价");
        titleCell = titleRow.createCell(4);
        titleCell.setCellValue("批次");
        titleCell = titleRow.createCell(5);
        titleCell.setCellValue("出厂日期");
        for (int i = 0; i < items.size(); ++i) {
            WarehouseCountingVO item = items.get(i);
            Integer id = item.getId();
            ProductInfoVO pVO = item.getProduct();
            Integer quantity = item.getQuantity();
            BigDecimal price = item.getPurchasePrice();
            Integer batchId = item.getBatchId();
            Date productionDate = item.getProductionDate();

            Row row = sheet.createRow(i + 1);
            Cell cell = row.createCell(0);
            cell.setCellValue(id == null ? "" : id.toString());
            cell = row.createCell(1);
            cell.setCellValue(pVO == null ? "" : pVO.getId());
            cell = row.createCell(2);
            cell.setCellValue(quantity == null ? "" : quantity.toString());
            cell = row.createCell(3);
            cell.setCellValue(price == null ? "" : price.toString());
            cell = row.createCell(4);
            cell.setCellValue(batchId == null ? "" : batchId.toString());
            cell = row.createCell(5);
            cell.setCellValue(productionDate == null ? "" : productionDate.toString());
        }
        return wb;
    }
}
