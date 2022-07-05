package com.nju.edu.erp.dao;

import com.nju.edu.erp.model.po.AnnualBonusPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AnnualBonusDao {

    int createAnnualBonus(AnnualBonusPO annualBonusPO);

    List<AnnualBonusPO> findAnnualBonusByEmployeeId(Integer eid);
}
