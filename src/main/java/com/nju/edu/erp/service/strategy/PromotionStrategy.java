package com.nju.edu.erp.service.strategy;

import com.nju.edu.erp.exception.MyServiceException;
import com.nju.edu.erp.service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PromotionStrategy {

    @Autowired
    private final Map<String, PromotionService> promotionServiceMap = new HashMap<>();

    private PromotionService getPromotion(String type) {
        if (this.promotionServiceMap.get(type) == null) {
            throw new MyServiceException("000009", "unknown promotion type");
        }
        return this.promotionServiceMap.get(type);
    }

    public void deletePromotionById(String type, int id) {
        PromotionService promotionService = getPromotion(type);
        promotionService.deletePromotionById(id);
    }

    public void addPromotion(String type, Object promotionVO) {
        PromotionService promotionService = getPromotion(type);
        promotionService.addPromotion(promotionVO);
    }

    public List<Object> getPromotions(String type) {
        PromotionService promotionService = getPromotion(type);
        return promotionService.getPromotions();
    }

    public Object getOnePromotionByType(String type, String message) {
        PromotionService promotionService = getPromotion(type);
        return promotionService.getLatestPromotion(message);
    }
}
