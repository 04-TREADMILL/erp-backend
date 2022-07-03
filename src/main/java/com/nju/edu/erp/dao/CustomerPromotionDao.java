package com.nju.edu.erp.dao;

import com.nju.edu.erp.model.po.CustomerPromotionPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface CustomerPromotionDao {

    int createPromotion(CustomerPromotionPO promotionPO);

    int deletePromotionById(Integer id);

    List<CustomerPromotionPO> showPromotions();

    CustomerPromotionPO getPromotionById(Integer id);
}
