package com.nju.edu.erp.model.vo.promotion;

import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CustomerPromotionVO extends PromotionVO {

    int level;

    BigDecimal discount;

    BigDecimal amount;

    @Builder(builderMethodName = "childBuilder")
    public CustomerPromotionVO(int id, Date beginTime, Date endTime, int level, BigDecimal discount,BigDecimal amount) {
        super(id, beginTime, endTime);
        this.level = level;
        this.amount = amount;
        this.discount = discount;
    }

    @Builder(builderMethodName = "childBuilder")
    public CustomerPromotionVO() {
        super();
    }
}
