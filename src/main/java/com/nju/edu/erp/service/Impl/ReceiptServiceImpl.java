package com.nju.edu.erp.service.Impl;

import com.nju.edu.erp.dao.ReceiptSheetDao;
import com.nju.edu.erp.enums.sheetState.ReceiptSheetState;
import com.nju.edu.erp.model.po.CustomerPO;
import com.nju.edu.erp.model.po.ReceiptSheetPO;
import com.nju.edu.erp.model.vo.UserVO;
import com.nju.edu.erp.model.vo.finance.AccountVO;
import com.nju.edu.erp.model.vo.finance.ReceiptSheetVO;
import com.nju.edu.erp.service.AccountService;
import com.nju.edu.erp.service.CustomerService;
import com.nju.edu.erp.service.ReceiptService;
import com.nju.edu.erp.utils.IdDateUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ReceiptServiceImpl implements ReceiptService {
    private final ReceiptSheetDao receiptSheetDao;

    private final CustomerService customerService;

    private final AccountService accountService;

    @Autowired
    public ReceiptServiceImpl(ReceiptSheetDao receiptSheetDao, CustomerService customerService, AccountService accountService) {
        this.receiptSheetDao = receiptSheetDao;
        this.customerService = customerService;
        this.accountService = accountService;
    }

    @Override
    public void makeReceiptSheet(UserVO userVO, ReceiptSheetVO receiptSheetVO) {
        ReceiptSheetPO receiptSheetPO = new ReceiptSheetPO();
        BeanUtils.copyProperties(receiptSheetVO, receiptSheetPO);
        receiptSheetPO.setOperator(userVO.getName());
        receiptSheetPO.setCreateTime(new Date());
        ReceiptSheetPO latest = receiptSheetDao.getLatest();
        String id = IdDateUtil.generateSheetId(latest == null ? null : latest.getId(), "SKD");
        receiptSheetPO.setId(id);
        receiptSheetPO.setState(ReceiptSheetState.PENDING);
        receiptSheetDao.save(receiptSheetPO);
    }

    @Override
    public List<ReceiptSheetVO> getReceiptSheetByState(ReceiptSheetState state) {
        List<ReceiptSheetVO> receiptSheetVOS = new ArrayList<>();
        List<ReceiptSheetPO> receiptSheetPOS;
        if (state == null) {
            receiptSheetPOS = receiptSheetDao.findAll();
        } else {
            receiptSheetPOS = receiptSheetDao.findAllByState(state);
        }
        for (ReceiptSheetPO receiptSheetPO : receiptSheetPOS) {
            ReceiptSheetVO receiptSheetVO = new ReceiptSheetVO();
            BeanUtils.copyProperties(receiptSheetPO, receiptSheetVO);
            receiptSheetVOS.add(receiptSheetVO);
        }
        return receiptSheetVOS;
    }

    @Override
    public void approval(String receiptSheetId, ReceiptSheetState state) {
        if (state.equals(ReceiptSheetState.FAILURE)) {
            ReceiptSheetPO receiptSheetPO = receiptSheetDao.findOneById(receiptSheetId);
            if (receiptSheetPO == null) {
                throw new RuntimeException("单据不存在");
            }
            if (receiptSheetPO.getState().equals(ReceiptSheetState.SUCCESS)) {
                throw new RuntimeException("状态更新失败");
            }
            int effectLines = receiptSheetDao.updateState(receiptSheetId, state);
            if (effectLines == 0) {
                throw new RuntimeException("状态更新失败");
            }
        } else {
            ReceiptSheetState prevState;
            if (state.equals(ReceiptSheetState.SUCCESS)) {
                prevState = ReceiptSheetState.PENDING;
            } else {
                throw new RuntimeException("状态更新失败");
            }
            int effectLines = receiptSheetDao.updateStateV2(receiptSheetId, prevState, state);
            if (effectLines == 0) {
                throw new RuntimeException("状态更新失败");
            }

            // 更新客户表 - 应收字段
            ReceiptSheetPO receiptSheetPO = receiptSheetDao.findOneById(receiptSheetId);
            CustomerPO customerPO = customerService.findCustomerById(receiptSheetPO.getSeller());
            customerPO.setReceivable(customerPO.getReceivable().add(receiptSheetPO.getTotalAmount()));
            customerService.updateCustomer(customerPO);

            // 更新账户信息
            AccountVO accountVO = accountService.queryAccount(receiptSheetPO.getAccount());
            accountService.increaseAccountAmount(accountVO.getName(), receiptSheetPO.getTotalAmount());
        }
    }

    @Override
    public ReceiptSheetVO getReceiptSheetById(String receiptSheetId) {
        ReceiptSheetPO receiptSheetPO = receiptSheetDao.findOneById(receiptSheetId);
        if (receiptSheetPO == null) {
            return null;
        }
        ReceiptSheetVO receiptSheetVO = new ReceiptSheetVO();
        BeanUtils.copyProperties(receiptSheetPO, receiptSheetVO);
        return receiptSheetVO;
    }
}
