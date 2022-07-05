package com.nju.edu.erp.service.Impl;

import com.alibaba.fastjson.JSONObject;
import com.nju.edu.erp.dao.CustomerPromotionDao;
import com.nju.edu.erp.exception.MyServiceException;
import com.nju.edu.erp.model.po.CustomerPromotionPO;
import com.nju.edu.erp.model.vo.promotion.CustomerPromotionVO;
import com.nju.edu.erp.service.PromotionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component("customer")
@Slf4j
public class CustomerPromotionServiceImpl implements PromotionService {

    private final CustomerPromotionDao promotionDao;

    @Autowired
    public CustomerPromotionServiceImpl(CustomerPromotionDao promotionDao) {
        this.promotionDao = promotionDao;
    }

    @Override
    public void deletePromotionById(int id) {
        CustomerPromotionPO promotionPO = promotionDao.getPromotionById(id);
        if (promotionPO == null) {
            throw new MyServiceException("123456", "促销策略不存在");
        }
        promotionDao.deletePromotionById(id);
    }

    @Override
    public void addPromotion(String promotionVO) {
//        String str = JSONObject.toJSONString(promotionVO);
        CustomerPromotionVO vo = JSONObject.parseObject(promotionVO, CustomerPromotionVO.class);
        CustomerPromotionPO po = new CustomerPromotionPO();
        BeanUtils.copyProperties(vo, po);
        promotionDao.createPromotion(po);
    }

    @Override
    public List<Object> getPromotions() {
        List<CustomerPromotionPO> promotionPOS = promotionDao.showPromotions();
        List<Object> promotionVOS = new ArrayList<>();
        for (CustomerPromotionPO promotionPO : promotionPOS) {
            CustomerPromotionVO promotionVO = new CustomerPromotionVO();
            BeanUtils.copyProperties(promotionPO, promotionVO);
            promotionVOS.add(promotionVO);
        }
        return promotionVOS;
    }

    @Override
    public Object getLatestPromotion(String message) {
        List<CustomerPromotionPO> promotionPOS = promotionDao.showPromotions();
        CustomerPromotionPO targetPO = null;
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        String now = format.format(new Date());
        for (CustomerPromotionPO po : promotionPOS) {
            String begin = format.format(po.getBeginTime());
            String end = format.format(po.getEndTime());
            if (po.getLevel() == Integer.parseInt(message)
                    && Integer.parseInt(now) >= Integer.parseInt(begin)
                    && Integer.parseInt(now) <= Integer.parseInt(end)) {
                if (targetPO == null) {
                    targetPO = po;
                } else {
                    targetPO = targetPO.getDiscount().compareTo(po.getDiscount()) < 0 ? targetPO : po;
                }
            }
        }
        if (targetPO == null) return null;
        CustomerPromotionVO targetVO = new CustomerPromotionVO();
        BeanUtils.copyProperties(targetPO, targetVO);
        return targetVO;
    }
}
