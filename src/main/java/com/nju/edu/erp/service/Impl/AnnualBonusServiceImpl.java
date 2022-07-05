package com.nju.edu.erp.service.Impl;

import com.nju.edu.erp.dao.AnnualBonusDao;
import com.nju.edu.erp.enums.Role;
import com.nju.edu.erp.model.po.AnnualBonusPO;
import com.nju.edu.erp.model.vo.employee.AnnualBonusVO;
import com.nju.edu.erp.service.AnnualBonusService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AnnualBonusServiceImpl implements AnnualBonusService {

    private final AnnualBonusDao annualBonusDao;

    private final Map<Role, BigDecimal> baseBonusMap = new HashMap<>();


    @Autowired
    public AnnualBonusServiceImpl(AnnualBonusDao annualBonusDao) {
        this.annualBonusDao = annualBonusDao;
        this.baseBonusMap.put(Role.GM, BigDecimal.valueOf(100000));
        this.baseBonusMap.put(Role.SALE_MANAGER, BigDecimal.valueOf(50000));
        this.baseBonusMap.put(Role.FINANCIAL_STAFF, BigDecimal.valueOf(30000));
        this.baseBonusMap.put(Role.HR, BigDecimal.valueOf(40000));
        this.baseBonusMap.put(Role.SALE_STAFF, BigDecimal.valueOf(30000));
        this.baseBonusMap.put(Role.INVENTORY_MANAGER, BigDecimal.valueOf(40000));
        this.baseBonusMap.put(Role.ADMIN, BigDecimal.valueOf(20000));
    }

    @Override
    public AnnualBonusVO addAnnualBonus(Integer eid, Role role, BigDecimal extraBonus) {
        BigDecimal baseBonus = this.baseBonusMap.get(role);
        AnnualBonusPO annualBonusPO = AnnualBonusPO.builder()
                .eid(eid)
                .extraBonus(extraBonus)
                .baseBonus(baseBonus)
                .build();
        annualBonusDao.createAnnualBonus(annualBonusPO);
        return AnnualBonusVO.builder().eid(eid).baseBonus(baseBonus).extraBonus(extraBonus).build();
    }

    @Override
    public List<AnnualBonusVO> getAnnualBonusByEmployeeId(Integer eid) {
        List<AnnualBonusPO> annualBonusPOS = annualBonusDao.findAnnualBonusByEmployeeId(eid);
        List<AnnualBonusVO> annualBonusVOS = new ArrayList<>();
        for (AnnualBonusPO po : annualBonusPOS) {
            AnnualBonusVO vo = new AnnualBonusVO();
            BeanUtils.copyProperties(po, vo);
            annualBonusVOS.add(vo);
        }
        return annualBonusVOS;
    }
}
