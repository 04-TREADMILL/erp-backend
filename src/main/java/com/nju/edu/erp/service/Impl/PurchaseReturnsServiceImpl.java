package com.nju.edu.erp.service.Impl;

import com.nju.edu.erp.dao.ProductDao;
import com.nju.edu.erp.dao.PurchaseReturnsSheetDao;
import com.nju.edu.erp.dao.PurchaseSheetDao;
import com.nju.edu.erp.dao.WarehouseDao;
import com.nju.edu.erp.enums.sheetState.PurchaseReturnsSheetState;
import com.nju.edu.erp.model.po.*;
import com.nju.edu.erp.model.vo.ProductInfoVO;
import com.nju.edu.erp.model.vo.UserVO;
import com.nju.edu.erp.model.vo.purchaseReturns.PurchaseReturnsSheetContentVO;
import com.nju.edu.erp.model.vo.purchaseReturns.PurchaseReturnsSheetVO;
import com.nju.edu.erp.model.vo.warehouse.WarehouseInputFormContentVO;
import com.nju.edu.erp.model.vo.warehouse.WarehouseInputFormVO;
import com.nju.edu.erp.service.CustomerService;
import com.nju.edu.erp.service.ProductService;
import com.nju.edu.erp.service.PurchaseReturnsService;
import com.nju.edu.erp.service.WarehouseService;
import com.nju.edu.erp.utils.IdGenerator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Service
public class PurchaseReturnsServiceImpl implements PurchaseReturnsService {

    PurchaseReturnsSheetDao purchaseReturnsSheetDao;

    ProductService productService;

    ProductDao productDao;

    PurchaseSheetDao purchaseSheetDao;

    CustomerService customerService;

    WarehouseService warehouseService;

    WarehouseDao warehouseDao;

    @Autowired
    public PurchaseReturnsServiceImpl(PurchaseReturnsSheetDao purchaseReturnsSheetDao, ProductService productService, CustomerService customerService, WarehouseService warehouseService, ProductDao productDao,PurchaseSheetDao purchaseSheetDao,WarehouseDao warehouseDao) {
        this.purchaseReturnsSheetDao = purchaseReturnsSheetDao;
        this.productService = productService;
        this.customerService = customerService;
        this.warehouseService = warehouseService;
        this.productDao = productDao;
        this.purchaseSheetDao = purchaseSheetDao;
        this.warehouseDao =  warehouseDao;
    }

    /**
     * 制定进货退货单
     *
     * @param purchaseReturnsSheetVO 进货退货单
     */
    @Override
    @Transactional
    public void makePurchaseReturnsSheet(UserVO userVO, PurchaseReturnsSheetVO purchaseReturnsSheetVO) {
        PurchaseReturnsSheetPO purchaseReturnsSheetPO = new PurchaseReturnsSheetPO();
        BeanUtils.copyProperties(purchaseReturnsSheetVO, purchaseReturnsSheetPO);
        // 此处根据制定单据人员确定操作员
        purchaseReturnsSheetPO.setOperator(userVO.getName());
        purchaseReturnsSheetPO.setCreateTime(new Date());
        PurchaseReturnsSheetPO latest = purchaseReturnsSheetDao.getLatest();
        String id = IdGenerator.generateSheetId(latest == null ? null : latest.getId(), "JHTHD");
        purchaseReturnsSheetPO.setId(id);
        purchaseReturnsSheetPO.setState(PurchaseReturnsSheetState.PENDING_LEVEL_1);
        BigDecimal totalAmount = BigDecimal.ZERO;
        List<PurchaseSheetContentPO> purchaseSheetContent = purchaseSheetDao.findContentByPurchaseSheetId(purchaseReturnsSheetPO.getPurchaseSheetId());
        Map<String, PurchaseSheetContentPO> map = new HashMap<>();
        for(PurchaseSheetContentPO item : purchaseSheetContent) {
            map.put(item.getPid(), item);
        }
        List<PurchaseReturnsSheetContentPO> pContentPOList = new ArrayList<>();
        for(PurchaseReturnsSheetContentVO content : purchaseReturnsSheetVO.getPurchaseReturnsSheetContent()) {
            PurchaseReturnsSheetContentPO pContentPO = new PurchaseReturnsSheetContentPO();
            BeanUtils.copyProperties(content,pContentPO);
            pContentPO.setPurchaseReturnsSheetId(id);
            PurchaseSheetContentPO item = map.get(pContentPO.getPid());
            pContentPO.setUnitPrice(item.getUnitPrice());

            BigDecimal unitPrice = pContentPO.getUnitPrice();
            pContentPO.setTotalPrice(unitPrice.multiply(BigDecimal.valueOf(pContentPO.getQuantity())));
            pContentPOList.add(pContentPO);
            totalAmount = totalAmount.add(pContentPO.getTotalPrice());
        }
        purchaseReturnsSheetDao.saveBatch(pContentPOList);
        purchaseReturnsSheetPO.setTotalAmount(totalAmount);
        purchaseReturnsSheetDao.save(purchaseReturnsSheetPO);
    }

    /**
     * 根据状态获取进货退货单[不包括content信息](state == null 则获取所有进货退货单)
     *
     * @param state 进货退货单状态
     * @return 进货退货单
     */
    @Override
    public List<PurchaseReturnsSheetVO> getPurchaseReturnsSheetByState(PurchaseReturnsSheetState state) {
        List<PurchaseReturnsSheetVO> res = new ArrayList<>();
        List<PurchaseReturnsSheetPO> all;
        if(state == null) {
            all = purchaseReturnsSheetDao.findAll();
        } else {
            all = purchaseReturnsSheetDao.findAllByState(state);
        }
        for(PurchaseReturnsSheetPO po: all) {
            PurchaseReturnsSheetVO vo = new PurchaseReturnsSheetVO();
            BeanUtils.copyProperties(po, vo);
            List<PurchaseReturnsSheetContentPO> alll = purchaseReturnsSheetDao.findContentByPurchaseReturnsSheetId(po.getId());
            List<PurchaseReturnsSheetContentVO> vos = new ArrayList<>();
            for (PurchaseReturnsSheetContentPO p : alll) {
                PurchaseReturnsSheetContentVO v = new PurchaseReturnsSheetContentVO();
                BeanUtils.copyProperties(p, v);
                vos.add(v);
            }
            vo.setPurchaseReturnsSheetContent(vos);
            res.add(vo);
        }
        return res;
    }

    /**
     * 根据进货退货单id进行审批(state == "待二级审批"/"审批完成"/"审批失败")
     * 在controller层进行权限控制
     *
     * @param purchaseReturnsSheetId 进货退货单id
     * @param state           进货退货单要达到的状态
     */
    @Override
    @Transactional
    public void approval(String purchaseReturnsSheetId, PurchaseReturnsSheetState state) { // TODO
        PurchaseReturnsSheetPO purchaseReturnsSheet = purchaseReturnsSheetDao.findOneById(purchaseReturnsSheetId);
        if(state.equals(PurchaseReturnsSheetState.FAILURE)) {
            if(purchaseReturnsSheet.getState() == PurchaseReturnsSheetState.SUCCESS) throw new RuntimeException("状态更新失败");
            int effectLines = purchaseReturnsSheetDao.updateState(purchaseReturnsSheetId, state);
            if(effectLines == 0) throw new RuntimeException("状态更新失败");
        } else {
            PurchaseReturnsSheetState prevState;
            if(state.equals(PurchaseReturnsSheetState.SUCCESS)) {
                prevState = PurchaseReturnsSheetState.PENDING_LEVEL_2;
            } else if(state.equals(PurchaseReturnsSheetState.PENDING_LEVEL_2)) {
                prevState = PurchaseReturnsSheetState.PENDING_LEVEL_1;
            } else {
                throw new RuntimeException("状态更新失败");
            }
            int effectLines = purchaseReturnsSheetDao.updateStateV2(purchaseReturnsSheetId, prevState, state);
            if(effectLines == 0) throw new RuntimeException("状态更新失败");
            if(state.equals(PurchaseReturnsSheetState.SUCCESS)) {
                // TODO 审批完成, 修改一系列状态
                // 进货退货单id， 关联的进货单id 【   进货退货单id->进货单id->入库单id->批次id】
                Integer batchId = purchaseReturnsSheetDao.findBatchId(purchaseReturnsSheetId);

                //- 进货退货单id-pid， quantity 【批次id+pid -> 定位到库存的一个条目->库存减去quantity】
                //- 【 pid -> 定位到单位进价->Σ单位进价*quantity=要收回的钱->客户payable减去要收回的钱】
                List<PurchaseReturnsSheetContentPO> contents = purchaseReturnsSheetDao.findContentByPurchaseReturnsSheetId(purchaseReturnsSheetId);
                BigDecimal payableToDeduct = BigDecimal.ZERO;
                for (PurchaseReturnsSheetContentPO content:
                        contents) {
                    String pid = content.getPid();
                    Integer quantity = content.getQuantity();
                    WarehousePO warehousePO = warehouseDao.findOneByPidAndBatchId(pid, batchId);
                    if(warehousePO == null) throw new RuntimeException("单据发生错误！请联系管理员！");
                    if(warehousePO.getQuantity() >= quantity) {
                        warehousePO.setQuantity(quantity);
                        warehouseDao.deductQuantity(warehousePO);
                        ProductInfoVO productInfoVO = productService.getOneProductByPid(pid);
                        productInfoVO.setQuantity(productInfoVO.getQuantity()-quantity);
                        productService.updateProduct(productInfoVO);
                        payableToDeduct = payableToDeduct.add(content.getUnitPrice().multiply(BigDecimal.valueOf(quantity))) ;
                    } else {
                        purchaseReturnsSheetDao.updateState(purchaseReturnsSheetId, PurchaseReturnsSheetState.FAILURE);
                        throw new RuntimeException("商品数量不足！审批失败！");
                    }
                }

                PurchaseSheetPO purchaseSheetPO = purchaseSheetDao.findOneById(purchaseReturnsSheet.getPurchaseSheetId());
                Integer supplier = purchaseSheetPO.getSupplier();
                CustomerPO customer = customerService.findCustomerById(supplier);

                customer.setPayable(customer.getPayable().subtract(payableToDeduct));
                customerService.updateCustomer(customer);
            }
        }
    }
}