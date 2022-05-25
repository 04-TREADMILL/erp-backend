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
public class WarehouseOutputFormVO { // 出库单
    private List<WarehouseOutputFormContentVO> list;
    private String operator;
    private String saleSheetId;

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for(WarehouseOutputFormContentVO item: list) {
            sb.append(item.toString());
            sb.append(" | ");
        }
        sb.append("]  ");
        sb.append(operator).append("\n");
        return sb.toString();
    }
}
