package com.nju.edu.erp.service;

import com.nju.edu.erp.enums.sheetState.WarehouseInputSheetState;
import com.nju.edu.erp.enums.sheetState.WarehouseOutputSheetState;
import com.nju.edu.erp.model.po.*;
import com.nju.edu.erp.model.po.WarehouseIODetailPO;
import com.nju.edu.erp.model.vo.UserVO;
import com.nju.edu.erp.model.vo.warehouse.*;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;

@Service
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

    /**
     * 审批入库单(仓库管理员进行确认/总经理进行审批)
     * @param warehouseInputSheetId 入库单id
     * @param state 入库单修改后的状态(state == "待审批"/"审批失败"/"审批完成")
     */
    void approvalInputSheet(UserVO user, String warehouseInputSheetId, WarehouseInputSheetState state);

    /**
     * 通过状态获取入库单(state == null 时获取全部入库单)
     * @param state 入库单状态
     * @return 入库单
     */
    List<WarehouseInputSheetPO> getWareHouseInputSheetByState(WarehouseInputSheetState state);

    /**
     * 通过状态获取出库单(state == null 时获取全部出库单)
     * @param state 出库单状态
     * @return 出库单
     */
    List<WarehouseOutputSheetPO> getWareHouseOutSheetByState(WarehouseOutputSheetState state);

    /**
     * 审批出库单(仓库管理员进行确认)
     * @param sheetId 入库单id
     * @param state 入库单修改后的状态(state == "待审批"/"审批失败"/"审批完成")
     */
    void approvalOutputSheet(UserVO user, String sheetId, WarehouseOutputSheetState state);

    /**
     * 根据单据id获取具体内容
     * @param sheetId
     * @return
     */
    List<WarehouseInputSheetContentPO> getWHISheetContentById(String sheetId);

    /**
     * 根据单据id获取具体内容
     * @param sheetId
     * @return
     */
    List<WarehouseOutputSheetContentPO> getWHOSheetContentById(String sheetId);


    /**
     * 库存查看：设定一个时间段，查看此时间段内的出/入库数量/金额/商品信息/分类信息
     * @param beginDateStr 开始时间字符串
     * @param endDateStr 结束时间字符串
     * @return
     * @throws ParseException
     */
    List<WarehouseIODetailPO> getWarehouseIODetailByTime(String beginDateStr,String endDateStr) throws ParseException;


    /**
     * 库存查看：一个时间段内的入库数量合计
     * @param beginDateStr
     * @param endDateStr
     * @return
     */
    int getWarehouseInputProductQuantityByTime(String beginDateStr,String endDateStr);

    /**
     * 库存查看：一个时间段内的出库数量合计
     * @param beginDateStr
     * @param endDateStr
     * @return
     */
    int getWarehouseOutProductQuantityByTime(String beginDateStr,String endDateStr);

    /**
     * 库存盘点
     * 盘点的是当天的库存快照，包括当天的各种商品的
     * 名称，型号，库存数量，库存均价，批次，批号，出厂日期，并且显示行号。
     * 要求可以导出Excel
     *
     */
    List<WarehouseCountingVO> warehouseCounting();
}
