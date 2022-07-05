package com.nju.edu.erp.service;

import java.util.List;

public interface PromotionService {

    void deletePromotionById(int id);

    void addPromotion(String promotionVO);

    List<Object> getPromotions();

    Object getLatestPromotion(String message);
}
