package com.nju.edu.erp.service;

import java.util.List;

public interface PromotionService {

    void deletePromotionById(int id);

    void addPromotion(Object promotionVO);

    List<Object> getPromotions();

    Object getLatestPromotion(String message);
}
