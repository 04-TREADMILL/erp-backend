package com.nju.edu.erp.model.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TotalPromotionPO {

    int id;

    Date beginTime;

    Date endTime;

    BigDecimal condition;

    BigDecimal amount;
}
