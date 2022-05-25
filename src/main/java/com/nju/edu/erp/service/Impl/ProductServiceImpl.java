package com.nju.edu.erp.service.Impl;

import com.nju.edu.erp.dao.CategoryDao;
import com.nju.edu.erp.dao.ProductDao;
import com.nju.edu.erp.exception.MyServiceException;
import com.nju.edu.erp.model.po.CategoryPO;
import com.nju.edu.erp.model.po.ProductPO;
import com.nju.edu.erp.model.vo.CreateProductVO;
import com.nju.edu.erp.model.vo.ProductInfoVO;
import com.nju.edu.erp.service.ProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductDao productDao;

    private final CategoryDao categoryDao;

    @Autowired
    public ProductServiceImpl(ProductDao productDao, CategoryDao categoryDao) {
        this.productDao = productDao;
        this.categoryDao = categoryDao;
    }

    @Override
    @Transactional
    public ProductInfoVO createProduct(CreateProductVO inputVO) {
        // 查找商品对应的分类
        CategoryPO category = categoryDao.findByCategoryId(inputVO.getCategoryId());
        // 无法增加商品的情况
        if (category == null) {
            throw new MyServiceException("B0001", "当前分类不存在");
        }
        if (category.isLeaf() == false) {
            throw new MyServiceException("B0002", "当前分类无法增加商品");
        }
        // 生成商品ID
        String productId = generateProductId(category);
        // 增加商品信息
        ProductPO savePO = new ProductPO();
        BeanUtils.copyProperties(inputVO, savePO);
        savePO.setId(productId);
        productDao.createProduct(savePO);
        // 同步修改分类下的商品数量和商品index
        category.setItemCount(category.getItemCount() + 1);
        category.setItemIndex(category.getItemIndex() + 1);
        categoryDao.updateById(category);
        // 获取返回值
        ProductPO responsePO = productDao.findById(productId);
        ProductInfoVO ans = new ProductInfoVO();
        BeanUtils.copyProperties(responsePO, ans);
        return ans;
    }

    @Override
    @Transactional
    public ProductInfoVO updateProduct(ProductInfoVO productInfoVO) {
        ProductPO savePO = new ProductPO();
        BeanUtils.copyProperties(productInfoVO, savePO);
        int ans = productDao.updateById(savePO);
        if (ans == 0) {
            throw new MyServiceException("B0003", "更新失败！");
        }
        savePO = productDao.findById(savePO.getId());
        ProductInfoVO res = new ProductInfoVO();
        BeanUtils.copyProperties(savePO, res);
        return res;
    }

    @Override
    @Transactional
    public List<ProductInfoVO> queryAllProduct() {
        List<ProductPO> queryAns = productDao.findAll();
        List<ProductInfoVO> responseVO = queryAns.stream().map(productPO -> {
            ProductInfoVO productInfoVO = new ProductInfoVO();
            BeanUtils.copyProperties(productPO, productInfoVO);
            return  productInfoVO;
        }).collect(Collectors.toList());
        return responseVO;
    }

    @Override
    public void deleteById(String id) {
        // 获取所在分类
        ProductPO productPO = productDao.findById(id);
        if (productPO == null) {
            throw new MyServiceException("B0004", "删除失败！");
        }
        CategoryPO category = categoryDao.findByCategoryId(productPO.getCategoryId());
        // 删除商品
        int ans = productDao.deleteById(id);
        if (ans == 0) {
            throw new MyServiceException("B0004", "删除失败！");
        }
        // 修改分类相关信息
        category.setItemCount(category.getItemCount() - 1);
        int categoryAns = categoryDao.updateById(category);
        if (ans == 0) {
            throw new MyServiceException("B0005", "修改分类失败！");
        }
    }

    /**
     * 通过pid获取商品详情
     *
     * @param pid 商品id
     * @return 商品详情
     */
    @Override
    public ProductInfoVO getOneProductByPid(String pid) {
        ProductPO productPO = productDao.findById(pid);
        ProductInfoVO productInfoVO = new ProductInfoVO();
        BeanUtils.copyProperties(productPO, productInfoVO);
        return productInfoVO;
    }


    private String generateProductId(CategoryPO categoryPO) {
        StringBuffer ans = new StringBuffer();
        String categoryStr = categoryPO.getId().toString();
        String indexStr = categoryPO.getItemIndex().toString();
        if (indexStr.length() > 5) {
            throw new MyServiceException("B0006", "当前分类下商品编号已用完！");
        }
        for (int i = 0; i < 11 - categoryStr.length(); i++)
            ans.append('0');
        ans.append(categoryStr);
        for (int i = 0; i < 5 - indexStr.length(); i++)
            ans.append('0');
        ans.append(indexStr);
        return ans.toString();
    }
}
