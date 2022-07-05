package com.nju.edu.erp.web.controller;

import com.nju.edu.erp.auth.Authorized;
import com.nju.edu.erp.enums.Role;
import com.nju.edu.erp.exception.MyServiceException;
import com.nju.edu.erp.model.vo.finance.AccountVO;
import com.nju.edu.erp.service.AccountService;
import com.nju.edu.erp.web.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping(path = "/account")
public class AccountController {
    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/show")
    @Authorized(roles = {Role.GM, Role.ADMIN})
    public Response getAllAccounts() {
        try {
            return Response.buildSuccess(accountService.queryAllAccounts());
        } catch (MyServiceException e) {
            return Response.buildFailed(e.getCode(), e.getMessage());
        } catch (Exception e) {
            return Response.buildFailed("114514", "未知错误");
        }
    }

    @GetMapping("/get")
    @Authorized(roles = {Role.GM, Role.ADMIN})
    public Response getAccount(@RequestBody String name) {
        try {
            return Response.buildSuccess(accountService.queryAccount(name));
        } catch (MyServiceException e) {
            return Response.buildFailed(e.getCode(), e.getMessage());
        } catch (Exception e) {
            return Response.buildFailed("114514", "未知错误");
        }
    }

    @GetMapping("/add")
    @Authorized(roles = {Role.GM, Role.ADMIN})
    public Response addAccount(@RequestBody AccountVO accountVO) {
        try {
            accountService.createAccount(accountVO.getName(), accountVO.getAmount());
            return Response.buildSuccess("添加账户成功");
        } catch (MyServiceException e) {
            return Response.buildFailed(e.getCode(), e.getMessage());
        } catch (Exception e) {
            return Response.buildFailed("114514", "未知错误");
        }
    }

    @GetMapping("/delete")
    @Authorized(roles = {Role.GM, Role.ADMIN})
    public Response deleteAccount(@RequestBody String name) {
        try {
            accountService.deleteAccount(name);
            return Response.buildSuccess("删除账户成功");
        } catch (MyServiceException e) {
            return Response.buildFailed(e.getCode(), e.getMessage());
        } catch (Exception e) {
            return Response.buildFailed("114514", "未知错误");
        }
    }
}
