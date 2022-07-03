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
public class CustomerPromotionPO {

    int id;

    Date beginTime;

    Date endTime;

    int level;

    BigDecimal discount;

    BigDecimal amount;
}
