package com.nju.edu.erp.service;

import com.nju.edu.erp.enums.Role;
import com.nju.edu.erp.model.vo.employee.AnnualBonusVO;

import java.math.BigDecimal;
import java.util.List;

public interface AnnualBonusService {

    AnnualBonusVO addAnnualBonus(Integer eid, Role role, BigDecimal extraBonus);

    List<AnnualBonusVO> getAnnualBonusByEmployeeId(Integer eid);
}
