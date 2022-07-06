package com.nju.edu.erp.web.controller;

import com.nju.edu.erp.auth.Authorized;
import com.nju.edu.erp.enums.Role;
import com.nju.edu.erp.enums.sheetState.SaleReturnsSheetState;
import com.nju.edu.erp.model.vo.UserVO;
import com.nju.edu.erp.model.vo.saleReturns.SaleReturnsSheetVO;
import com.nju.edu.erp.service.SaleReturnsService;
import com.nju.edu.erp.utils.IdUtil;
import com.nju.edu.erp.web.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/sale-returns")
public class SaleReturnsController {

    private final SaleReturnsService saleReturnsService;

    @Autowired
    public SaleReturnsController(SaleReturnsService saleReturnsService) {
        this.saleReturnsService = saleReturnsService;
    }

    /**
     * 销售人员制定销售退货单
     */
    @Authorized(roles = {Role.SALE_STAFF, Role.SALE_MANAGER, Role.GM, Role.ADMIN})
    @PostMapping(value = "/sheet-make")
    public Response makeSaleOrder(UserVO userVO, @RequestBody SaleReturnsSheetVO saleReturnsSheetVO) {
        saleReturnsService.makeSaleReturnsSheet(userVO, saleReturnsSheetVO);
        return Response.buildSuccess();
    }

    /**
     * 销售经理审批
     *
     * @param saleReturnsSheetId 销售退货单id
     * @param state              修改后的状态("审批失败"/"待二级审批")
     */
    @GetMapping(value = "/first-approval")
    @Authorized(roles = {Role.SALE_MANAGER, Role.GM, Role.ADMIN})
    public Response firstApproval(@RequestParam("saleReturnsSheetId") String saleReturnsSheetId,
                                  @RequestParam("state") SaleReturnsSheetState state) {
        if (state.equals(SaleReturnsSheetState.FAILURE) || state.equals(SaleReturnsSheetState.PENDING_LEVEL_2)) {
            saleReturnsService.approval(saleReturnsSheetId, state);
            return Response.buildSuccess();
        } else {
            return Response.buildFailed("000000", "操作失败"); // code可能得改一个其他的
        }
    }

    /**
     * 总经理审批
     *
     * @param saleReturnsSheetId 销售退货单id
     * @param state              修改后的状态("审批失败"/"审批完成")
     */
    @Authorized(roles = {Role.GM, Role.ADMIN})
    @GetMapping(value = "/second-approval")
    public Response secondApproval(@RequestParam("saleReturnsSheetId") String saleReturnsSheetId,
                                   @RequestParam("state") SaleReturnsSheetState state) {
        if (state.equals(SaleReturnsSheetState.FAILURE) || state.equals(SaleReturnsSheetState.SUCCESS)) {
            saleReturnsService.approval(saleReturnsSheetId, state);
            return Response.buildSuccess();
        } else {
            return Response.buildFailed("000000", "操作失败"); // code可能得改一个其他的
        }
    }

    /**
     * 根据状态查看销售退货单
     */
    @GetMapping(value = "/sheet-show")
    public Response showSheetByState(@RequestParam(value = "state", required = false) SaleReturnsSheetState state) {
        return Response.buildSuccess(saleReturnsService.getSaleReturnsSheetByState(state));
    }

    @GetMapping(value = "/sheet-show-filter")
    public Response showSheetFilter(
            @RequestParam(value = "from", required = false) Date from,
            @RequestParam(value = "to", required = false) Date to,
            @RequestParam(value = "salesman", required = false) String salesman) {
        return Response.buildSuccess(saleReturnsService.getSaleReturnsSheetByState(null).stream().filter(
                saleReturnsSheetVO -> {
                    Date date = IdUtil.parseDateFromSheetId(saleReturnsSheetVO.getId(), "XSTHD");
                    return (((from == null && to == null) || (date.after(from) && date.before(to)))
                            && (salesman == null || saleReturnsSheetVO.getSalesman().equals(salesman))
                    );
                }
        ).collect(Collectors.toList()));
    }
}
