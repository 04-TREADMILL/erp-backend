package com.nju.edu.erp.service.Impl;

import com.nju.edu.erp.dao.*;
import com.nju.edu.erp.enums.sheetState.PurchaseReturnsSheetState;
import com.nju.edu.erp.enums.sheetState.SaleReturnsSheetState;
import com.nju.edu.erp.model.po.*;
import com.nju.edu.erp.model.vo.ProductInfoVO;
import com.nju.edu.erp.model.vo.UserVO;
import com.nju.edu.erp.model.vo.purchaseReturns.PurchaseReturnsSheetVO;
import com.nju.edu.erp.model.vo.saleReturns.SaleReturnsSheetContentVO;
import com.nju.edu.erp.model.vo.saleReturns.SaleReturnsSheetVO;
import com.nju.edu.erp.service.CustomerService;
import com.nju.edu.erp.service.ProductService;
import com.nju.edu.erp.service.SaleReturnsService;
import com.nju.edu.erp.service.WarehouseService;
import com.nju.edu.erp.utils.IdGenerator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Service
public class SaleReturnsServiceImpl implements SaleReturnsService {

    SaleReturnsSheetDao saleReturnsSheetDao;

    ProductService productService;

    ProductDao productDao;

    SaleSheetDao saleSheetDao;

    CustomerService customerService;

    WarehouseService warehouseService;

    WarehouseDao warehouseDao;

    @Autowired
    public SaleReturnsServiceImpl(SaleReturnsSheetDao saleReturnsSheetDao, ProductService productService, ProductDao productDao, SaleSheetDao saleSheetDao, CustomerService customerService, WarehouseService warehouseService, WarehouseDao warehouseDao) {
        this.saleReturnsSheetDao = saleReturnsSheetDao;
        this.productService = productService;
        this.productDao = productDao;
        this.saleSheetDao = saleSheetDao;
        this.customerService = customerService;
        this.warehouseService = warehouseService;
        this.warehouseDao = warehouseDao;
    }

    /**
     * 制定销售退货单
     *
     * @param saleReturnsSheetVO 销售退货单
     */
    @Override
    @Transactional
    public void makeSaleReturnsSheet(UserVO userVO, SaleReturnsSheetVO saleReturnsSheetVO) {
        SaleReturnsSheetPO saleReturnsSheetPO = new SaleReturnsSheetPO();
        BeanUtils.copyProperties(saleReturnsSheetVO, saleReturnsSheetPO);

        saleReturnsSheetPO.setOperator(userVO.getName());
        saleReturnsSheetPO.setCreateTime(new Date());
        SaleReturnsSheetPO latest = saleReturnsSheetDao.getLatest();
        String id = IdGenerator.generateSheetId(latest == null ? null : latest.getId(), "XSTHD");
        saleReturnsSheetPO.setId(id);
        saleReturnsSheetPO.setState(SaleReturnsSheetState.PENDING_LEVEL_1);
        BigDecimal totalAmount = BigDecimal.ZERO;
        List<SaleSheetContentPO> saleSheetContent = saleSheetDao.findContentBySheetId(saleReturnsSheetPO.getSaleSheetId());
        Map<String, SaleSheetContentPO> map = new HashMap<>();
        for (SaleSheetContentPO item : saleSheetContent) {
            map.put(item.getPid(), item);
        }

        SaleSheetPO saleSheetPO = saleSheetDao.findSheetById(saleReturnsSheetPO.getSaleSheetId());
        BigDecimal discount = saleSheetPO.getDiscount();
        BigDecimal rawTotalAmount = saleSheetPO.getRawTotalAmount();
        BigDecimal voucherAmount = saleSheetPO.getVoucherAmount();
        List<SaleReturnsSheetContentPO> sContentPOList = new ArrayList<>();
        for (SaleReturnsSheetContentVO content : saleReturnsSheetVO.getSaleReturnsSheetContent()) {
            SaleReturnsSheetContentPO sContentPO = new SaleReturnsSheetContentPO();
            BeanUtils.copyProperties(content, sContentPO);
            sContentPO.setSaleReturnsSheetId(id);
            SaleSheetContentPO item = map.get(sContentPO.getPid());
            sContentPO.setUnitPrice(item.getUnitPrice());

            BigDecimal unitPrice = sContentPO.getUnitPrice();
            BigDecimal unitVoucherAmount = voucherAmount.multiply(unitPrice).divide(rawTotalAmount, 2, RoundingMode.HALF_UP);
            unitPrice = unitPrice.multiply(discount).subtract(unitVoucherAmount);
            sContentPO.setTotalPrice(BigDecimal.valueOf(sContentPO.getQuantity()).multiply(unitPrice));
            sContentPOList.add(sContentPO);
            totalAmount = totalAmount.add(sContentPO.getTotalPrice());
        }
        saleReturnsSheetDao.saveBatch(sContentPOList);
        saleReturnsSheetPO.setTotalAmount(totalAmount);
        saleReturnsSheetDao.save(saleReturnsSheetPO);
    }

    @Override
    public List<SaleReturnsSheetVO> getSaleReturnsSheetByState(SaleReturnsSheetState state) {
        List<SaleReturnsSheetVO> res = new ArrayList<>();
        List<SaleReturnsSheetPO> all;
        if (state == null) {
            all = saleReturnsSheetDao.findAll();
        } else {
            all = saleReturnsSheetDao.findAllByState(state);
        }
        for (SaleReturnsSheetPO po : all) {
            SaleReturnsSheetVO vo = new SaleReturnsSheetVO();
            BeanUtils.copyProperties(po, vo);
            List<SaleReturnsSheetContentPO> alll = saleReturnsSheetDao.findContentBySaleReturnsSheetId(po.getId());
            List<SaleReturnsSheetContentVO> vos = new ArrayList<>();
            for (SaleReturnsSheetContentPO p : alll) {
                SaleReturnsSheetContentVO v = new SaleReturnsSheetContentVO();
                BeanUtils.copyProperties(p, v);
                vos.add(v);
            }
            vo.setSaleReturnsSheetContent(vos);
            res.add(vo);
        }
        return res;
    }

    @Override
    public void approval(String saleReturnsSheetId, SaleReturnsSheetState state) {
        SaleReturnsSheetPO saleReturnsSheet = saleReturnsSheetDao.findOneById(saleReturnsSheetId);
        if (state.equals(SaleReturnsSheetState.FAILURE)) {
            if (saleReturnsSheet.getState() == SaleReturnsSheetState.SUCCESS)
                throw new RuntimeException("状态更新失败");
            int effectLines = saleReturnsSheetDao.updateState(saleReturnsSheetId, state);
            if (effectLines == 0) throw new RuntimeException("状态更新失败");
        } else {
            SaleReturnsSheetState prevState;
            if (state.equals(SaleReturnsSheetState.SUCCESS)) {
                prevState = SaleReturnsSheetState.PENDING_LEVEL_2;
            } else if (state.equals(SaleReturnsSheetState.PENDING_LEVEL_2)) {
                prevState = SaleReturnsSheetState.PENDING_LEVEL_1;
            } else {
                throw new RuntimeException("状态更新失败");
            }
            int effectLines = saleReturnsSheetDao.updateStateV2(saleReturnsSheetId, prevState, state);
            if (effectLines == 0) throw new RuntimeException("状态更新失败");
            if (state.equals(SaleReturnsSheetState.SUCCESS)) {
                // TODO 审批完成, 修改一系列状态
                // 销售退货单id， 关联的进货单id 【   进货退货单id->进货单id->入库单id->批次id】
                Integer batchId = saleReturnsSheetDao.findBatchId(saleReturnsSheetId);

                //- 销售退货单id-pid， quantity 【批次id+pid -> 定位到库存的一个条目->库存减去quantity】
                //- 【 pid -> 定位到单位进价->Σ单位进价*quantity=要收回的钱->客户payable减去要收回的钱】
                List<SaleReturnsSheetContentPO> contents = saleReturnsSheetDao.findContentBySaleReturnsSheetId(saleReturnsSheetId);
                BigDecimal payableToAdd = saleReturnsSheet.getTotalAmount();
                for (SaleReturnsSheetContentPO content : contents) {
                    String pid = content.getPid();
                    Integer quantity = content.getQuantity();
                    WarehousePO warehousePO = warehouseDao.findOneByPidAndBatchId(pid, batchId);
                    if (warehousePO == null) throw new RuntimeException("单据发生错误！请联系管理员！");
                    warehousePO.setQuantity(quantity);
                    warehouseDao.addQuantity(warehousePO);
                    ProductInfoVO productInfoVO = productService.getOneProductByPid(pid);
                    productInfoVO.setQuantity(productInfoVO.getQuantity() + quantity);
                    productService.updateProduct(productInfoVO);
                }

                SaleSheetPO saleSheetPO = saleSheetDao.findSheetById(saleReturnsSheet.getSaleSheetId());
                Integer supplier = saleSheetPO.getSupplier();
                CustomerPO customer = customerService.findCustomerById(supplier);

                customer.setPayable(customer.getPayable().add(payableToAdd));
                customerService.updateCustomer(customer);
            }
        }
    }
}
