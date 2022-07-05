package com.nju.edu.erp.model.vo.employee;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AnnualBonusVO {

    int id;

    int eid;

    BigDecimal baseBonus;

    BigDecimal extraBonus;
}
