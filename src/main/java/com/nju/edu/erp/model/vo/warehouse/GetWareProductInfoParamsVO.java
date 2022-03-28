package com.nju.edu.erp.model.vo.warehouse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetWareProductInfoParamsVO {   // 1. 填商品ID,数量,备注   pid, quantity, remark -> GetWareProductInfo
                                    // 2. 系统返回该型号商品不同批次的库存信息 -> WarehouseOneProductInfo
    private String pid;
    private Integer quantity;
    private String remark;
}
