package com.nju.edu.erp.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.nju.edu.erp.auth.Authorized;
import com.nju.edu.erp.enums.Role;
import com.nju.edu.erp.exception.MyServiceException;
import com.nju.edu.erp.model.vo.promotion.CustomerPromotionVO;
import com.nju.edu.erp.model.vo.promotion.TotalPromotionVO;
import com.nju.edu.erp.service.strategy.PromotionStrategy;
import com.nju.edu.erp.web.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/promotion")
public class PromotionController {

    private final PromotionStrategy promotionStrategy;

    @Autowired
    public PromotionController(PromotionStrategy promotionStrategy) {
        this.promotionStrategy = promotionStrategy;
    }

//    @PostMapping("/add")
//    @Authorized(roles = {Role.GM, Role.ADMIN})
//    public Response addPromotion(@RequestParam("promotionType") String promotionType,
//                                 @RequestBody Object promotionVO) {
//        try {
//            promotionStrategy.addPromotion(promotionType, promotionVO);
//            return Response.buildSuccess();
//        } catch (MyServiceException e) {
//            return Response.buildFailed(e.getCode(), e.getMessage());
//        } catch (Exception e) {
//            return Response.buildFailed("111111", "Unknown Exception");
//        }
//    }

    @PostMapping("/add-total")
    @Authorized(roles = {Role.GM, Role.ADMIN})
    public Response addTotalPromotion(@RequestBody TotalPromotionVO promotionVO) {
        try {
            promotionStrategy.addPromotion("total", promotionVO);
            return Response.buildSuccess();
        } catch (MyServiceException e) {
            return Response.buildFailed(e.getCode(), e.getMessage());
        } catch (Exception e) {
            return Response.buildFailed("111111", "Unknown Exception");
        }
    }

    @PostMapping("/add-customer")
    @Authorized(roles = {Role.GM, Role.ADMIN})
    public Response addCustomerPromotion(@RequestBody CustomerPromotionVO promotionVO) {
        try {
            promotionStrategy.addPromotion("customer", promotionVO);
            return Response.buildSuccess();
        } catch (MyServiceException e) {
            return Response.buildFailed(e.getCode(), e.getMessage());
        } catch (Exception e) {
            return Response.buildFailed("111111", "Unknown Exception");
        }
    }

    @GetMapping("/delete")
    @Authorized(roles = {Role.GM, Role.ADMIN})
    public Response deletePromotion(@RequestParam("promotionType") String promotionType,
                                    @RequestParam("promotionId") int promotionId) {
        try {
            promotionStrategy.deletePromotionById(promotionType, promotionId);
            return Response.buildSuccess();
        } catch (MyServiceException e) {
            return Response.buildFailed(e.getCode(), e.getMessage());
        } catch (Exception e) {
            return Response.buildFailed("111111", "Unknown Exception");
        }
    }

    @GetMapping("/show")
    @Authorized(roles = {Role.GM, Role.ADMIN})
    public Response showPromotion(@RequestParam("promotionType") String promotionType) {
        try {
            return Response.buildSuccess(promotionStrategy.getPromotions(promotionType));
        } catch (MyServiceException e) {
            return Response.buildFailed(e.getCode(), e.getMessage());
        } catch (Exception e) {
            return Response.buildFailed("111111", "Unknown Exception");
        }
    }
}
