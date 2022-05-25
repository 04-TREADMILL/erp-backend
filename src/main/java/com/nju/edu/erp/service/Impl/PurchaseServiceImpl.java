package com.nju.edu.erp.service.Impl;

import com.nju.edu.erp.dao.ProductDao;
import com.nju.edu.erp.dao.PurchaseSheetDao;
import com.nju.edu.erp.enums.sheetState.PurchaseSheetState;
import com.nju.edu.erp.model.po.CustomerPO;
import com.nju.edu.erp.model.po.ProductPO;
import com.nju.edu.erp.model.po.PurchaseSheetContentPO;
import com.nju.edu.erp.model.po.PurchaseSheetPO;
import com.nju.edu.erp.model.vo.ProductInfoVO;
import com.nju.edu.erp.model.vo.UserVO;
import com.nju.edu.erp.model.vo.purchase.PurchaseSheetContentVO;
import com.nju.edu.erp.model.vo.purchase.PurchaseSheetVO;
import com.nju.edu.erp.model.vo.warehouse.WarehouseInputFormContentVO;
import com.nju.edu.erp.model.vo.warehouse.WarehouseInputFormVO;
import com.nju.edu.erp.service.CustomerService;
import com.nju.edu.erp.service.ProductService;
import com.nju.edu.erp.service.PurchaseService;
import com.nju.edu.erp.service.WarehouseService;
import com.nju.edu.erp.utils.IdGenerator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class PurchaseServiceImpl implements PurchaseService {

    PurchaseSheetDao purchaseSheetDao;

    ProductService productService;

    ProductDao productDao;

    CustomerService customerService;

    WarehouseService warehouseService;

    @Autowired
    public PurchaseServiceImpl(PurchaseSheetDao purchaseSheetDao, ProductService productService, CustomerService customerService, WarehouseService warehouseService,ProductDao productDao) {
        this.purchaseSheetDao = purchaseSheetDao;
        this.productService = productService;
        this.customerService = customerService;
        this.warehouseService = warehouseService;
        this.productDao = productDao;
    }

    /**
     * 制定进货单
     *
     * @param purchaseSheetVO 进货单
     */
    @Override
    @Transactional
    public void makePurchaseSheet(UserVO userVO, PurchaseSheetVO purchaseSheetVO) {
        PurchaseSheetPO purchaseSheetPO = new PurchaseSheetPO();
        BeanUtils.copyProperties(purchaseSheetVO, purchaseSheetPO);
        // 此处根据制定单据人员确定操作员
        purchaseSheetPO.setOperator(userVO.getName());
        purchaseSheetPO.setCreateTime(new Date());
        PurchaseSheetPO latest = purchaseSheetDao.getLatest();
        String id = IdGenerator.generateSheetId(latest == null ? null : latest.getId(), "JHD");
        purchaseSheetPO.setId(id);
        purchaseSheetPO.setState(PurchaseSheetState.PENDING_LEVEL_1);
        BigDecimal totalAmount = BigDecimal.ZERO;
        List<PurchaseSheetContentPO> pContentPOList = new ArrayList<>();
        for(PurchaseSheetContentVO content : purchaseSheetVO.getPurchaseSheetContent()) {
            PurchaseSheetContentPO pContentPO = new PurchaseSheetContentPO();
            BeanUtils.copyProperties(content,pContentPO);
            pContentPO.setPurchaseSheetId(id);
            BigDecimal unitPrice = pContentPO.getUnitPrice();
            if(unitPrice == null) {
                ProductPO product = productDao.findById(content.getPid());
                unitPrice = product.getPurchasePrice();
                pContentPO.setUnitPrice(unitPrice);
            }
            pContentPO.setTotalPrice(unitPrice.multiply(BigDecimal.valueOf(pContentPO.getQuantity())));
            pContentPOList.add(pContentPO);
            totalAmount = totalAmount.add(pContentPO.getTotalPrice());
        }
        purchaseSheetDao.saveBatch(pContentPOList);
        purchaseSheetPO.setTotalAmount(totalAmount);
        purchaseSheetDao.save(purchaseSheetPO);
    }

    /**
     * 根据状态获取进货单[不包括content信息](state == null 则获取所有进货单)
     *
     * @param state 进货单状态
     * @return 进货单
     */
    @Override
    public List<PurchaseSheetVO> getPurchaseSheetByState(PurchaseSheetState state) {
        List<PurchaseSheetVO> res = new ArrayList<>();
        List<PurchaseSheetPO> all;
        if(state == null) {
            all = purchaseSheetDao.findAll();
        } else {
            all = purchaseSheetDao.findAllByState(state);
        }
        for(PurchaseSheetPO po: all) {
            PurchaseSheetVO vo = new PurchaseSheetVO();
            BeanUtils.copyProperties(po, vo);
            List<PurchaseSheetContentPO> alll = purchaseSheetDao.findContentByPurchaseSheetId(po.getId());
            List<PurchaseSheetContentVO> vos = new ArrayList<>();
            for (PurchaseSheetContentPO p : alll) {
                PurchaseSheetContentVO v = new PurchaseSheetContentVO();
                BeanUtils.copyProperties(p, v);
                vos.add(v);
            }
            vo.setPurchaseSheetContent(vos);
            res.add(vo);
        }
        return res;
    }

    /**
     * 根据进货单id进行审批(state == "待二级审批"/"审批完成"/"审批失败")
     * 在controller层进行权限控制
     *
     * @param purchaseSheetId 进货单id
     * @param state           进货单要达到的状态
     */
    @Override
    @Transactional
    public void approval(String purchaseSheetId, PurchaseSheetState state) {
        if(state.equals(PurchaseSheetState.FAILURE)) {
            PurchaseSheetPO purchaseSheet = purchaseSheetDao.findOneById(purchaseSheetId);
            if(purchaseSheet.getState() == PurchaseSheetState.SUCCESS) throw new RuntimeException("状态更新失败");
            int effectLines = purchaseSheetDao.updateState(purchaseSheetId, state);
            if(effectLines == 0) throw new RuntimeException("状态更新失败");
        } else {
            PurchaseSheetState prevState;
            if(state.equals(PurchaseSheetState.SUCCESS)) {
                prevState = PurchaseSheetState.PENDING_LEVEL_2;
            } else if(state.equals(PurchaseSheetState.PENDING_LEVEL_2)) {
                prevState = PurchaseSheetState.PENDING_LEVEL_1;
            } else {
                throw new RuntimeException("状态更新失败");
            }
            int effectLines = purchaseSheetDao.updateStateV2(purchaseSheetId, prevState, state);
            if(effectLines == 0) throw new RuntimeException("状态更新失败");
            if(state.equals(PurchaseSheetState.SUCCESS)) {
                // TODO 审批完成, 修改一系列状态
                // 更新商品表的最新进价
                    // 根据purchaseSheetId查到对应的content -> 得到商品id和单价
                    // 根据商品id和单价更新商品最近进价recentPp
                List<PurchaseSheetContentPO> purchaseSheetContent =  purchaseSheetDao.findContentByPurchaseSheetId(purchaseSheetId);
                List<WarehouseInputFormContentVO> warehouseInputFormContentVOS = new ArrayList<>();

                for(PurchaseSheetContentPO content : purchaseSheetContent) {
                    ProductInfoVO productInfoVO = new ProductInfoVO();
                    productInfoVO.setId(content.getPid());
                    productInfoVO.setRecentPp(content.getUnitPrice());
                    productService.updateProduct(productInfoVO);

                    WarehouseInputFormContentVO wiContentVO = new WarehouseInputFormContentVO();
                    wiContentVO.setPurchasePrice(content.getUnitPrice());
                    wiContentVO.setQuantity(content.getQuantity());
                    wiContentVO.setRemark(content.getRemark());
                    wiContentVO.setPid(content.getPid());
                    warehouseInputFormContentVOS.add(wiContentVO);
                }
                // 更新客户表(更新应付字段)
                    // 更新应付 payable
                PurchaseSheetPO purchaseSheet = purchaseSheetDao.findOneById(purchaseSheetId);
                CustomerPO customerPO = customerService.findCustomerById(purchaseSheet.getSupplier());
                customerPO.setPayable(customerPO.getPayable().add(purchaseSheet.getTotalAmount()));
                customerService.updateCustomer(customerPO);

                // 制定入库单草稿(在这里关联进货单)
                    // 调用创建入库单的方法
                WarehouseInputFormVO warehouseInputFormVO = new WarehouseInputFormVO();
                warehouseInputFormVO.setOperator(null); // 暂时不填操作人(确认草稿单的时候填写)
                warehouseInputFormVO.setPurchaseSheetId(purchaseSheetId);
                warehouseInputFormVO.setList(warehouseInputFormContentVOS);
                warehouseService.productWarehousing(warehouseInputFormVO);
            }
        }
    }

    /**
     * 根据进货单Id搜索进货单信息
     * @param purchaseSheetId 进货单Id
     * @return 进货单全部信息
     */
    @Override
    public PurchaseSheetVO getPurchaseSheetById(String purchaseSheetId) {
        PurchaseSheetPO purchaseSheetPO = purchaseSheetDao.findOneById(purchaseSheetId);
        if(purchaseSheetPO == null) return null;
        List<PurchaseSheetContentPO> contentPO = purchaseSheetDao.findContentByPurchaseSheetId(purchaseSheetId);
        PurchaseSheetVO pVO = new PurchaseSheetVO();
        BeanUtils.copyProperties(purchaseSheetPO, pVO);
        List<PurchaseSheetContentVO> purchaseSheetContentVOList = new ArrayList<>();
        for (PurchaseSheetContentPO content:
             contentPO) {
            PurchaseSheetContentVO pContentVO = new PurchaseSheetContentVO();
            BeanUtils.copyProperties(content, pContentVO);
            purchaseSheetContentVOList.add(pContentVO);
        }
        pVO.setPurchaseSheetContent(purchaseSheetContentVOList);
        return pVO;
    }
}
