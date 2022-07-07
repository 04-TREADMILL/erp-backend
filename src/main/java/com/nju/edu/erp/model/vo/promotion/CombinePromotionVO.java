package com.nju.edu.erp.model.vo.promotion;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CombinePromotionVO extends PromotionVO {

    List<String> pidList;

    BigDecimal amount;

    @Builder(builderMethodName = "childBuilder")
    public CombinePromotionVO(int id, Date beginTime, Date endTime, BigDecimal amount, List<String> pidList) {
        super(id, beginTime, endTime);
        this.pidList = pidList;
        this.amount = amount;
    }

    @Builder(builderMethodName = "childBuilder")
    public CombinePromotionVO() {
        super();
    }
}
