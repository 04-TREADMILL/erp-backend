package com.nju.edu.erp.model.vo.warehouse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WarehouseInputFormVO { // 入库单
    private List<WarehouseInputFormContentVO> list;
    private String operator; // 操作员
    private String purchaseSheetId; // 关联的进货单Id
}
