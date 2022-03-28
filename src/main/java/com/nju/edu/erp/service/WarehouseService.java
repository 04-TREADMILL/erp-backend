package com.nju.edu.erp.service;

import com.nju.edu.erp.model.vo.warehouse.GetWareProductInfoParamsVO;
import com.nju.edu.erp.model.vo.warehouse.WarehouseInputFormVO;
import com.nju.edu.erp.model.vo.warehouse.WarehouseOneProductInfoVO;
import com.nju.edu.erp.model.vo.warehouse.WarehouseOutputFormVO;

import java.util.List;

public interface WarehouseService {
    /**
     * 商品入库
     * @param warehouseInputFormVO 入库单
     */
    void productWarehousing(WarehouseInputFormVO warehouseInputFormVO);

    /**
     * 商品出库
     * @param warehouseOutputFormListVO 出库单
     */
    void productOutOfWarehouse(WarehouseOutputFormVO warehouseOutputFormListVO);

    /**
     * 通过商品id、批次和数量来从库存中取物品
     * @param params 商品id、批次和数量
     * @return 不同批次的相同物品列表
     */
    List<WarehouseOneProductInfoVO> getWareProductInfo(GetWareProductInfoParamsVO params);
}
