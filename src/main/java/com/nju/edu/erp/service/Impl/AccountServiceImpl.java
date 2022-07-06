package com.nju.edu.erp.service.Impl;

import com.nju.edu.erp.dao.AccountDao;
import com.nju.edu.erp.exception.MyServiceException;
import com.nju.edu.erp.model.po.AccountPO;
import com.nju.edu.erp.model.vo.finance.AccountVO;
import com.nju.edu.erp.service.AccountService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {
    private final AccountDao accountDao;

    @Autowired
    public AccountServiceImpl(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @Override
    public void createAccount(String name, BigDecimal amount) {
        AccountPO accountPO = accountDao.findByAccountName(name);
        if (accountPO != null) {
            throw new MyServiceException("D0001", "账户已存在");
        }
        AccountPO res = AccountPO.builder().name(name).amount(amount).build();
        accountDao.createAccount(res);
    }

    @Override
    public AccountVO queryAccount(String name) {
        AccountPO accountPO = accountDao.findByAccountName(name);
        if (accountPO == null) {
            throw new MyServiceException("D0002", "账户不存在");
        }
        AccountVO accountVO = new AccountVO();
        BeanUtils.copyProperties(accountPO, accountVO);
        return accountVO;
    }

    @Override
    public List<AccountVO> queryAccountFzf(String key) {
        List<AccountPO> queryAns = accountDao.findByAccountNameFzf(key);
        return queryAns.stream().map(AccountPO -> {
            AccountVO AccountVO = new AccountVO();
            BeanUtils.copyProperties(AccountPO, AccountVO);
            return AccountVO;
        }).collect(Collectors.toList());
    }

    @Override
    public List<AccountVO> queryAllAccounts() {
        List<AccountPO> queryAns = accountDao.findAll();
        return queryAns.stream().map(AccountPO -> {
            AccountVO AccountVO = new AccountVO();
            BeanUtils.copyProperties(AccountPO, AccountVO);
            return AccountVO;
        }).collect(Collectors.toList());
    }

    @Override
    public void increaseAccountAmount(String name, BigDecimal amount) {
        AccountPO accountPO = accountDao.findByAccountName(name);
        if (accountPO == null) {
            throw new MyServiceException("D0002", "账户不存在");
        }
        accountPO.setAmount(accountPO.getAmount().add(amount));
        accountDao.updateByName(accountPO);
    }

    @Override
    public void decreaseAccountAmount(String name, BigDecimal amount) {
        AccountPO accountPO = accountDao.findByAccountName(name);
        if (accountPO == null) {
            throw new MyServiceException("D0002", "账户不存在");
        }
        if (accountPO.getAmount().compareTo(amount) < 0) {
            throw new MyServiceException("D0003", "账户余额不足");
        }
        accountPO.setAmount(accountPO.getAmount().subtract(amount));
        accountDao.updateByName(accountPO);
    }

    @Override
    public void deleteAccount(String name) {
        AccountPO accountPO = accountDao.findByAccountName(name);
        if (accountPO == null) {
            throw new MyServiceException("D0002", "账户不存在");
        }
        accountDao.deleteByName(name);
    }
}
