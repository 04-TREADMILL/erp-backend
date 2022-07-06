package com.nju.edu.erp.service;

import com.nju.edu.erp.model.vo.finance.AccountVO;

import java.math.BigDecimal;
import java.util.List;

public interface AccountService {

    void createAccount(String name, BigDecimal amount);

    List<AccountVO> queryAllAccounts();

    AccountVO queryAccount(String name);

    List<AccountVO> queryAccountFzf(String key);

    void increaseAccountAmount(String name, BigDecimal amount);

    void decreaseAccountAmount(String name, BigDecimal amount);

    void deleteAccount(String name);
}
