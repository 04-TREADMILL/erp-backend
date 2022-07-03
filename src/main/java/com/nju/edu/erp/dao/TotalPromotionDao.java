package com.nju.edu.erp.dao;

import com.nju.edu.erp.model.po.TotalPromotionPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface TotalPromotionDao {

    int createPromotion(TotalPromotionPO promotionPO);

    int deletePromotionById(Integer id);

    List<TotalPromotionPO> showPromotions();

    TotalPromotionPO getPromotionById(int id);
}
