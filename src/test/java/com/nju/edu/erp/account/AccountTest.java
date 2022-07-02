package com.nju.edu.erp.account;

import com.nju.edu.erp.dao.AccountDao;
import com.nju.edu.erp.model.po.AccountPO;
import com.nju.edu.erp.model.vo.finance.AccountVO;
import com.nju.edu.erp.service.Impl.AccountServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class AccountTest {

    @InjectMocks
    AccountServiceImpl accountService;

    @Mock
    private AccountDao accountDao;

    @Test
    public void findAllTest() {
        AccountPO accountPO1 = AccountPO.builder()
                .name("1")
                .amount(BigDecimal.valueOf(100))
                .build();

        AccountPO accountPO2 = AccountPO.builder()
                .name("2")
                .amount(BigDecimal.valueOf(200))
                .build();

        List<AccountPO> accountPOList = new ArrayList<>();
        accountPOList.add(accountPO1);
        accountPOList.add(accountPO2);
        Mockito.when(accountDao.findAll()).thenReturn(accountPOList);

        AccountVO accountVO1 = AccountVO.builder()
                .name("1")
                .amount(BigDecimal.valueOf(100))
                .build();
        AccountVO accountVO2 = AccountVO.builder()
                .name("2")
                .amount(BigDecimal.valueOf(200))
                .build();
        List<AccountVO> accountVOList = new ArrayList<>();
        accountVOList.add(accountVO1);
        accountVOList.add(accountVO2);
        Assert.assertEquals(accountVOList, accountService.queryAllAccounts());
    }
}
