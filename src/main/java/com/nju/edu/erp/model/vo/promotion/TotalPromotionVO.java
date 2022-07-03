package com.nju.edu.erp.model.vo.promotion;

import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TotalPromotionVO extends PromotionVO {

    BigDecimal condition;

    BigDecimal amount;

    @Builder(builderMethodName = "childBuilder")
    public TotalPromotionVO(int id, Date beginTime, Date endTime, BigDecimal condition, BigDecimal amount) {
        super(id, beginTime, endTime);
        this.condition = condition;
        this.amount = amount;
    }

    @Builder(builderMethodName = "childBuilder")
    public TotalPromotionVO() {
        super();
    }
}
