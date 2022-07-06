package com.nju.edu.erp.service.Impl;

import com.nju.edu.erp.dao.PaymentSheetDao;
import com.nju.edu.erp.enums.sheetState.PaymentSheetState;
import com.nju.edu.erp.model.po.CustomerPO;
import com.nju.edu.erp.model.po.PaymentSheetPO;
import com.nju.edu.erp.model.vo.UserVO;
import com.nju.edu.erp.model.vo.finance.AccountVO;
import com.nju.edu.erp.model.vo.finance.PaymentSheetVO;
import com.nju.edu.erp.service.AccountService;
import com.nju.edu.erp.service.CustomerService;
import com.nju.edu.erp.service.PaymentService;
import com.nju.edu.erp.utils.IdDateUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentSheetDao paymentSheetDao;

    private final CustomerService customerService;

    private final AccountService accountService;

    @Autowired
    public PaymentServiceImpl(PaymentSheetDao paymentSheetDao, CustomerService customerService, AccountService accountService) {
        this.paymentSheetDao = paymentSheetDao;
        this.customerService = customerService;
        this.accountService = accountService;
    }

    @Override
    public void makePaymentSheet(UserVO userVO, PaymentSheetVO paymentSheetVO) {
        PaymentSheetPO paymentSheetPO = new PaymentSheetPO();
        BeanUtils.copyProperties(paymentSheetVO, paymentSheetPO);
        paymentSheetPO.setOperator(userVO.getName());
        paymentSheetPO.setCreateTime(new Date());
        PaymentSheetPO latest = paymentSheetDao.getLatest();
        String id = IdDateUtil.generateSheetId(latest == null ? null : latest.getId(), "FKD");
        paymentSheetPO.setId(id);
        paymentSheetPO.setState(PaymentSheetState.PENDING);
        paymentSheetDao.save(paymentSheetPO);
    }

    @Override
    public List<PaymentSheetVO> getPaymentSheetByState(PaymentSheetState state) {
        List<PaymentSheetVO> paymentSheetVOS = new ArrayList<>();
        List<PaymentSheetPO> paymentSheetPOS;
        if (state == null) {
            paymentSheetPOS = paymentSheetDao.findAll();
        } else {
            paymentSheetPOS = paymentSheetDao.findAllByState(state);
        }
        for (PaymentSheetPO paymentSheetPO : paymentSheetPOS) {
            PaymentSheetVO paymentSheetVO = new PaymentSheetVO();
            BeanUtils.copyProperties(paymentSheetPO, paymentSheetVO);
            paymentSheetVOS.add(paymentSheetVO);
        }
        return paymentSheetVOS;
    }

    @Override
    public void approval(String paymentSheetId, PaymentSheetState state) {
        if (state.equals(PaymentSheetState.FAILURE)) {
            PaymentSheetPO paymentSheetPO = paymentSheetDao.findOneById(paymentSheetId);
            if (paymentSheetPO == null) {
                throw new RuntimeException("单据不存在");
            }
            if (paymentSheetPO.getState().equals(PaymentSheetState.SUCCESS)) {
                throw new RuntimeException("状态更新失败");
            }
            int effectLines = paymentSheetDao.updateState(paymentSheetId, state);
            if (effectLines == 0) {
                throw new RuntimeException("状态更新失败");
            }
        } else {
            PaymentSheetState prevState;
            if (state.equals(PaymentSheetState.SUCCESS)) {
                prevState = PaymentSheetState.PENDING;
            } else {
                throw new RuntimeException("状态更新失败");
            }
            int effectLines = paymentSheetDao.updateStateV2(paymentSheetId, prevState, state);
            if (effectLines == 0) {
                throw new RuntimeException("状态更新失败");
            }

            // 更新客户表 - 应付字段
            PaymentSheetPO paymentSheetPO = paymentSheetDao.findOneById(paymentSheetId);
            CustomerPO customerPO = customerService.findCustomerById(paymentSheetPO.getSupplier());
            customerPO.setPayable(customerPO.getPayable().subtract(paymentSheetPO.getTotalAmount()));
            customerService.updateCustomer(customerPO);

            // 更新账户信息
            AccountVO accountVO = accountService.queryAccount(paymentSheetPO.getAccount());
            accountService.decreaseAccountAmount(accountVO.getName(), paymentSheetPO.getTotalAmount());
        }
    }

    @Override
    public PaymentSheetVO getPaymentSheetById(String paymentSheetId) {
        PaymentSheetPO paymentSheetPO = paymentSheetDao.findOneById(paymentSheetId);
        if (paymentSheetPO == null) {
            return null;
        }
        PaymentSheetVO paymentSheetVO = new PaymentSheetVO();
        BeanUtils.copyProperties(paymentSheetPO, paymentSheetVO);
        return paymentSheetVO;
    }
}
