package com.nju.edu.erp.web.controller;

import com.nju.edu.erp.auth.Authorized;
import com.nju.edu.erp.enums.Role;
import com.nju.edu.erp.enums.sheetState.ReceiptSheetState;
import com.nju.edu.erp.model.vo.UserVO;
import com.nju.edu.erp.model.vo.finance.ReceiptSheetVO;
import com.nju.edu.erp.service.ReceiptService;
import com.nju.edu.erp.utils.IdDateUtil;
import com.nju.edu.erp.web.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/receipt")
public class ReceiptController {
    private final ReceiptService receiptService;

    @Autowired
    public ReceiptController(ReceiptService receiptService) {
        this.receiptService = receiptService;
    }

    @Authorized(roles = {Role.GM, Role.ADMIN, Role.FINANCIAL_STAFF})
    @PostMapping(value = "/sheet-make")
    public Response makeReceiptSheet(UserVO userVO, @RequestBody ReceiptSheetVO receiptSheetVO) {
        receiptService.makeReceiptSheet(userVO, receiptSheetVO);
        return Response.buildSuccess();
    }

    @GetMapping(value = "/approval")
    @Authorized(roles = {Role.GM, Role.ADMIN, Role.FINANCIAL_STAFF})
    public Response firstApproval(@RequestParam("receiptSheetId") String receiptSheetId,
                                  @RequestParam("state") ReceiptSheetState state) {
        if (state.equals(ReceiptSheetState.FAILURE) || state.equals(ReceiptSheetState.SUCCESS)) {
            receiptService.approval(receiptSheetId, state);
            return Response.buildSuccess();
        } else {
            return Response.buildFailed("000000", "操作失败");
        }
    }

    @Authorized(roles = {Role.GM, Role.ADMIN, Role.FINANCIAL_STAFF})
    @GetMapping(value = "/sheet-show")
    public Response showSheetByState(@RequestParam(value = "state", required = false) ReceiptSheetState state) {
        return Response.buildSuccess(receiptService.getReceiptSheetByState(state));
    }

    @GetMapping(value = "/sheet-show-filter")
    public Response showSheetFilter(
            @RequestParam(value = "from", required = false) String from,
            @RequestParam(value = "to", required = false) String to,
            @RequestParam(value = "operator", required = false) String operator,
            @RequestParam(value = "customerId", required = false) Integer customerId) {
        return Response.buildSuccess(receiptService.getReceiptSheetByState(null).stream().filter(
                receiptSheetVO -> {
                    Date date = IdDateUtil.parseDateFromSheetId(receiptSheetVO.getId(), "SKD");
                    return (((from == null && to == null) || (date.after(IdDateUtil.parseDateFromStr(from)) && date.before(IdDateUtil.parseDateFromStr(to))))
                            && (operator == null || receiptSheetVO.getOperator().equals(operator))
                            && (customerId == null || receiptSheetVO.getSeller().equals(customerId))
                    );
                }
        ).collect(Collectors.toList()));
    }

    @Authorized(roles = {Role.GM, Role.ADMIN, Role.FINANCIAL_STAFF})
    @GetMapping(value = "/find-sheet")
    public Response findBySheetId(@RequestParam(value = "id") String id) {
        return Response.buildSuccess(receiptService.getReceiptSheetById(id));
    }
}
