package com.nju.edu.erp.utils;

import com.nju.edu.erp.model.vo.ProductInfoVO;
import com.nju.edu.erp.model.vo.warehouse.WarehouseCountingVO;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

public class ExcelUtil {

    public static void exportWarehouseExcel(HttpServletResponse response, List<WarehouseCountingVO> items) {
        Date date = new Date();
        Workbook wb = createWorkbook(date, items);
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

    public static Workbook createWorkbook(Date date, List<WarehouseCountingVO> items) {
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

            Row row = sheet.createRow(i+1);
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
