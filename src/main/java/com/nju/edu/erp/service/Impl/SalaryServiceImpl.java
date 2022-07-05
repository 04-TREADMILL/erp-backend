package com.nju.edu.erp.service.Impl;

import com.nju.edu.erp.dao.EmployeeDao;
import com.nju.edu.erp.dao.SalarySheetDao;
import com.nju.edu.erp.enums.sheetState.SalarySheetState;
import com.nju.edu.erp.model.po.*;
import com.nju.edu.erp.model.po.SalarySheetPO;
import com.nju.edu.erp.model.vo.finance.AccountVO;
import com.nju.edu.erp.model.vo.finance.SalarySheetVO;
import com.nju.edu.erp.service.AccountService;
import com.nju.edu.erp.service.SalaryService;
import com.nju.edu.erp.utils.IdGenerator;
import com.nju.edu.erp.utils.Triplet;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.BiFunction;

@Service
public class SalaryServiceImpl implements SalaryService {

    private final SalarySheetDao salarySheetDao;

    private final EmployeeDao employeeDao;

    private final AccountService accountService;

    private static final List<Triplet<Double, BigDecimal, BigDecimal>> taxMap = new ArrayList<>();

    private static final Map<String, BiFunction<EmployeePO, SalarySheetPO, SalarySheetPO>> originSalaryMap = new HashMap<>();

    private static SalarySheetPO calculateOriginalSalaryDefault(EmployeePO employeePO, SalarySheetPO salarySheetPO) {
        salarySheetPO.setOriginalSalary(employeePO.getBasicSalary());
        return salarySheetPO;
    }

    private static SalarySheetPO calculateOriginalSalaryCommission(EmployeePO employeePO, SalarySheetPO salarySheetPO) {
        salarySheetPO.setOriginalSalary(employeePO.getBasicSalary().add(employeePO.getPostSalary()));
        return salarySheetPO;
    }

    static {
        originSalaryMap.put("default", SalaryServiceImpl::calculateOriginalSalaryDefault);
        originSalaryMap.put("commission", SalaryServiceImpl::calculateOriginalSalaryCommission);

        taxMap.add(Triplet.of(0.00, null, BigDecimal.valueOf(5000)));
        taxMap.add(Triplet.of(0.03, BigDecimal.valueOf(5000), BigDecimal.valueOf(8000)));
        taxMap.add(Triplet.of(0.10, BigDecimal.valueOf(8000), BigDecimal.valueOf(17000)));
        taxMap.add(Triplet.of(0.20, BigDecimal.valueOf(17000), BigDecimal.valueOf(30000)));
        taxMap.add(Triplet.of(0.25, BigDecimal.valueOf(30000), null));
    }

    private SalarySheetPO calculateRealSalary(SalarySheetPO salarySheetPO) {
        BigDecimal originalSalary = salarySheetPO.getOriginalSalary();
        for (Triplet<Double, BigDecimal, BigDecimal> rule : taxMap) {
            if ((rule.getMid() == null || rule.getMid().compareTo(originalSalary) <= 0) &&
                    (rule.getRight() == null || rule.getRight().compareTo(originalSalary) >= 0)) {
                salarySheetPO.setTax(originalSalary.multiply(BigDecimal.valueOf(rule.getLeft())));
                break;
            }
        }
        salarySheetPO.setRealSalary(originalSalary.subtract(salarySheetPO.getTax()));
        return salarySheetPO;
    }

    private SalarySheetPO calculateOriginalSalary(SalarySheetPO salarySheetPO) {
        EmployeePO employeePO = employeeDao.findEmployeeById(salarySheetPO.getEmployeeId());
        // TODO -> punch time
        return originSalaryMap.get(employeePO.getSalaryCalculatingMode()).apply(employeePO, salarySheetPO);
    }

    @Autowired
    public SalaryServiceImpl(SalarySheetDao salarySheetDao, EmployeeDao employeeDao, AccountService accountService) {
        this.salarySheetDao = salarySheetDao;
        this.employeeDao = employeeDao;
        this.accountService = accountService;
    }

    @Override
    public void makeSalarySheet(SalarySheetVO salarySheetVO) {
        SalarySheetPO salarySheetPO = new SalarySheetPO();
        BeanUtils.copyProperties(salarySheetVO, salarySheetPO);
        salarySheetPO.setCreateTime(new Date());
        SalarySheetPO latest = salarySheetDao.getLatest();
        String id = IdGenerator.generateSheetId(latest == null ? null : latest.getId(), "GZD");
        salarySheetPO.setId(id);
        salarySheetPO.setState(SalarySheetState.PENDING);
        salarySheetPO = calculateRealSalary(calculateOriginalSalary(salarySheetPO));
        salarySheetDao.save(salarySheetPO);
    }

    @Override
    public List<SalarySheetVO> getSalarySheetByState(SalarySheetState state) {
        List<SalarySheetVO> salarySheetVOS = new ArrayList<>();
        List<SalarySheetPO> salarySheetPOS;
        if (state == null) {
            salarySheetPOS = salarySheetDao.findAll();
        } else {
            salarySheetPOS = salarySheetDao.findAllByState(state);
        }
        for (SalarySheetPO salarySheetPO : salarySheetPOS) {
            SalarySheetVO salarySheetVO = new SalarySheetVO();
            BeanUtils.copyProperties(salarySheetPO, salarySheetVO);
            salarySheetVOS.add(salarySheetVO);
        }
        return salarySheetVOS;
    }

    @Override
    public void approval(String salarySheetId, SalarySheetState state) {
        if (state.equals(SalarySheetState.FAILURE)) {
            SalarySheetPO salarySheetPO = salarySheetDao.findOneById(salarySheetId);
            if (salarySheetPO == null) {
                throw new RuntimeException("单据不存在");
            }
            if (salarySheetPO.getState().equals(SalarySheetState.SUCCESS)) {
                throw new RuntimeException("状态更新失败");
            }
            int effectLines = salarySheetDao.updateState(salarySheetId, state);
            if (effectLines == 0) {
                throw new RuntimeException("状态更新失败");
            }
        } else {
            SalarySheetState prevState;
            if (state.equals(SalarySheetState.SUCCESS)) {
                prevState = SalarySheetState.PENDING;
            } else {
                throw new RuntimeException("状态更新失败");
            }
            int effectLines = salarySheetDao.updateStateV2(salarySheetId, prevState, state);
            if (effectLines == 0) {
                throw new RuntimeException("状态更新失败");
            }

            // 更新账户信息
            SalarySheetPO salarySheetPO = salarySheetDao.findOneById(salarySheetId);
            AccountVO accountVO = accountService.queryAccount(salarySheetPO.getAccount());
            accountService.decreaseAccountAmount(accountVO.getName(), salarySheetPO.getRealSalary());
        }
    }

    @Override
    public SalarySheetVO getSalarySheetById(String salarySheetId) {
        SalarySheetPO salarySheetPO = salarySheetDao.findOneById(salarySheetId);
        if (salarySheetPO == null) {
            return null;
        }
        SalarySheetVO salarySheetVO = new SalarySheetVO();
        BeanUtils.copyProperties(salarySheetPO, salarySheetVO);
        return salarySheetVO;
    }
}
