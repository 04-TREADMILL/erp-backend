package com.nju.edu.erp.model.vo.promotion;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PromotionVO {

    int id;

    Date beginTime;

    Date endTime;
}
