package com.nju.edu.erp.dao;

import com.nju.edu.erp.model.po.AccountPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface AccountDao {
    void createAccount(AccountPO accountPO);

    AccountPO findByAccountName(String name);

    List<AccountPO> findByAccountNameFzf(String key);

    List<AccountPO> findAll();

    void updateByName(AccountPO accountPO);

    void deleteByName(String name);
}
