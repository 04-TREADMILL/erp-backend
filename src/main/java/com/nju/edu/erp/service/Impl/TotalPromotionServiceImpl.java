package com.nju.edu.erp.service.Impl;

import com.alibaba.fastjson.JSONObject;
import com.nju.edu.erp.dao.TotalPromotionDao;
import com.nju.edu.erp.exception.MyServiceException;
import com.nju.edu.erp.model.po.TotalPromotionPO;
import com.nju.edu.erp.model.vo.promotion.TotalPromotionVO;
import com.nju.edu.erp.service.PromotionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Component("total")
@Slf4j
public class TotalPromotionServiceImpl implements PromotionService {

    private final TotalPromotionDao promotionDao;

    @Autowired
    public TotalPromotionServiceImpl(TotalPromotionDao promotionDao) {
        this.promotionDao = promotionDao;
    }

    @Override
    public void deletePromotionById(int id) {
        TotalPromotionPO promotionPO = promotionDao.getPromotionById(id);
        if (promotionPO == null) {
            throw new MyServiceException("123456", "促销策略不存在");
        }
        promotionDao.deletePromotionById(id);
    }

    @Override
    public void addPromotion(Object promotionVO) {
        String str = JSONObject.toJSONString(promotionVO);
        TotalPromotionVO vo = JSONObject.parseObject(str, TotalPromotionVO.class);
        TotalPromotionPO po = new TotalPromotionPO();
        BeanUtils.copyProperties(vo, po);
        promotionDao.createPromotion(po);
    }

    @Override
    public List<Object> getPromotions() {
        List<TotalPromotionPO> promotionPOS = promotionDao.showPromotions();
        List<Object> promotionVOS = new ArrayList<>();
        for (TotalPromotionPO promotionPO : promotionPOS) {
            TotalPromotionVO promotionVO = new TotalPromotionVO();
            BeanUtils.copyProperties(promotionPO, promotionVO);
            promotionVOS.add(promotionVO);
        }
        return promotionVOS;
    }

    @Override
    public Object getLatestPromotion(String message) {
        List<TotalPromotionPO> promotionPOS = promotionDao.showPromotions();
        TotalPromotionPO targetPO = null;
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        String now = format.format(new Date());
        for (TotalPromotionPO po : promotionPOS) {
            String begin = format.format(po.getBeginTime());
            String end = format.format(po.getEndTime());
            if (po.getCondition().compareTo(BigDecimal.valueOf(Double.parseDouble(message))) <= 0
                    && Integer.parseInt(now) >= Integer.parseInt(begin)
                    && Integer.parseInt(now) <= Integer.parseInt(end)) {
                if (targetPO == null) {
                    targetPO = po;
                } else {
                    targetPO = targetPO.getCondition().compareTo(po.getCondition()) > 0 ? targetPO : po;
                }
            }
        }
        if (targetPO == null) return null;
        TotalPromotionVO targetVO = new TotalPromotionVO();
        BeanUtils.copyProperties(targetPO, targetVO);
        return targetVO;
    }
}
