package com.nju.edu.erp.web.controller;

import com.nju.edu.erp.enums.sheetState.PurchaseSheetState;
import com.nju.edu.erp.model.vo.purchase.PurchaseSheetVO;
import com.nju.edu.erp.web.Response;
import org.springframework.web.bind.annotation.*;

/**
 * 用于测试枚举类的序列化与反序列化
 */
@RestController
@RequestMapping(path = "/api/test")
public class TestEnumController {
    @PostMapping(value = "/")
    public Response test1(@RequestBody PurchaseSheetVO purchaseSheetVO)  {
        System.out.println(purchaseSheetVO);
        return Response.buildSuccess(purchaseSheetVO);
    }

    @GetMapping(value = "/")
    public Response test2(@RequestParam(value = "state", required = false) PurchaseSheetState state)  {
        System.out.println(Response.buildSuccess(state));
        return Response.buildSuccess(state);
    }
}
