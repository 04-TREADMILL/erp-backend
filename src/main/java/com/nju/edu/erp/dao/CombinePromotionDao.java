package com.nju.edu.erp.dao;

import com.nju.edu.erp.model.po.CombinePromotionPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface CombinePromotionDao {

    int createPromotion(CombinePromotionPO promotionPO);

    int deletePromotionById(Integer id);

    List<CombinePromotionPO> showPromotions();

    CombinePromotionPO getPromotionById(Integer id);
}
