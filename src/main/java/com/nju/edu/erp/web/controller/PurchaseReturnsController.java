package com.nju.edu.erp.web.controller;

import com.nju.edu.erp.auth.Authorized;
import com.nju.edu.erp.enums.Role;
import com.nju.edu.erp.enums.sheetState.PurchaseReturnsSheetState;
import com.nju.edu.erp.model.vo.UserVO;
import com.nju.edu.erp.model.vo.purchaseReturns.PurchaseReturnsSheetVO;
import com.nju.edu.erp.service.PurchaseReturnsService;
import com.nju.edu.erp.utils.IdUtil;
import com.nju.edu.erp.web.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping(path = "/purchase-returns")
public class PurchaseReturnsController {

    private final PurchaseReturnsService purchaseReturnsService;

    @Autowired
    public PurchaseReturnsController(PurchaseReturnsService purchaseReturnsService) {
        this.purchaseReturnsService = purchaseReturnsService;
    }

    /**
     * 销售人员制定进货退货单
     */
    @Authorized(roles = {Role.SALE_STAFF, Role.SALE_MANAGER, Role.GM, Role.ADMIN})
    @PostMapping(value = "/sheet-make")
    public Response makePurchaseOrder(UserVO userVO, @RequestBody PurchaseReturnsSheetVO purchaseReturnsSheetVO) {
        purchaseReturnsService.makePurchaseReturnsSheet(userVO, purchaseReturnsSheetVO);
        return Response.buildSuccess();
    }

    /**
     * 销售经理审批
     *
     * @param purchaseReturnsSheetId 进货退货单id
     * @param state                  修改后的状态("审批失败"/"待二级审批")
     */
    @GetMapping(value = "/first-approval")
    @Authorized(roles = {Role.SALE_MANAGER, Role.GM, Role.ADMIN})
    public Response firstApproval(@RequestParam("purchaseReturnsSheetId") String purchaseReturnsSheetId,
                                  @RequestParam("state") PurchaseReturnsSheetState state) {
        if (state.equals(PurchaseReturnsSheetState.FAILURE) || state.equals(PurchaseReturnsSheetState.PENDING_LEVEL_2)) {
            purchaseReturnsService.approval(purchaseReturnsSheetId, state);
            return Response.buildSuccess();
        } else {
            return Response.buildFailed("000000", "操作失败"); // code可能得改一个其他的
        }
    }

    /**
     * 总经理审批
     *
     * @param purchaseReturnsSheetId 进货退货单id
     * @param state                  修改后的状态("审批失败"/"审批完成")
     */
    @Authorized(roles = {Role.GM, Role.ADMIN})
    @GetMapping(value = "/second-approval")
    public Response secondApproval(@RequestParam("purchaseReturnsSheetId") String purchaseReturnsSheetId,
                                   @RequestParam("state") PurchaseReturnsSheetState state) {
        if (state.equals(PurchaseReturnsSheetState.FAILURE) || state.equals(PurchaseReturnsSheetState.SUCCESS)) {
            purchaseReturnsService.approval(purchaseReturnsSheetId, state);
            return Response.buildSuccess();
        } else {
            return Response.buildFailed("000000", "操作失败"); // code可能得改一个其他的
        }
    }

    /**
     * 根据状态查看进货退货单
     */
    @GetMapping(value = "/sheet-show")
    public Response showSheetByState(@RequestParam(value = "state", required = false) PurchaseReturnsSheetState state) {
        return Response.buildSuccess(purchaseReturnsService.getPurchaseReturnsSheetByState(state));
    }

    @GetMapping(value = "/sheet-show-filter")
    public Response showSheetFilter(
            @RequestParam(value = "from", required = false) Date from,
            @RequestParam(value = "to", required = false) Date to,
            @RequestParam(value = "operator", required = false) String operator) {
        return Response.buildSuccess(purchaseReturnsService.getPurchaseReturnsSheetByState(null).stream().filter(
                purchaseReturnsSheetVO -> {
                    Date date = IdUtil.parseDateFromSheetId(purchaseReturnsSheetVO.getId(), "JHTHD");
                    return ((from == null && to == null) || (date.after(from) && date.before(to))
                            && (operator == null || purchaseReturnsSheetVO.getOperator().equals(operator)
                    ));
                }
        ));
    }

}