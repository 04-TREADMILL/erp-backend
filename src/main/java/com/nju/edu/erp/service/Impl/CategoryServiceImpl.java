package com.nju.edu.erp.service.Impl;

import com.nju.edu.erp.dao.CategoryDao;
import com.nju.edu.erp.exception.MyServiceException;
import com.nju.edu.erp.model.po.CategoryPO;
import com.nju.edu.erp.model.vo.CategoryVO;
import com.nju.edu.erp.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {


    private final CategoryDao categoryDao;

    @Autowired
    public CategoryServiceImpl(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    @Override
    public CategoryVO createCategory(Integer parentId, String name) {
        // parentId 为0则无法插入
        if (parentId <= 0) {
            throw new MyServiceException("A0000", "无效父类");
        }
        // 获取父节点，判断是否能够进行插入
        CategoryPO fatherPO = categoryDao.findByCategoryId(parentId);
        // 当前分类下存在商品
        if (fatherPO == null || fatherPO.getItemCount() > 0) {
            throw new MyServiceException("A0001", "父分类不存在或者当前父分类下存在商品 无法添加分类!");
        }
        // 创建PO并存入数据库
        CategoryPO savePO = new CategoryPO(null, name, parentId, true, 0, 0);
        int ans = categoryDao.createCategory(savePO);
        if (ans == 0) {
            throw new MyServiceException("A0002", "插入失败!");
        }
        // 修改父节点的isLeaf
        fatherPO.setLeaf(false);
        categoryDao.updateById(fatherPO);
        // 构建返回值
        CategoryVO responseVO = new CategoryVO();
        BeanUtils.copyProperties(savePO, responseVO);
        return responseVO;
    }

    @Override
    public List<CategoryVO> queryAllCategory() {
        List<CategoryPO> queryAns = categoryDao.findAll();
        List<CategoryVO> responseVO = queryAns.stream().map(categoryPO -> {
            CategoryVO categoryVO = new CategoryVO();
            BeanUtils.copyProperties(categoryPO, categoryVO);
            return  categoryVO;
        }).collect(Collectors.toList());
        return responseVO;
    }

    @Override
    public CategoryVO updateCategory(Integer id, String name) {
        CategoryPO categoryPO = categoryDao.findByCategoryId(id);
        categoryPO.setName(name);
        int ans = categoryDao.updateById(categoryPO);
        if (ans == 0) {
            throw new MyServiceException("A0003","修改失败！");
        }
        CategoryPO queryAns = categoryDao.findByCategoryId(id);
        CategoryVO responseVO = new CategoryVO();
        BeanUtils.copyProperties(queryAns, responseVO);
        return responseVO;
    }

    @Override
    public void deleteCategory(Integer id) {
        CategoryPO categoryToDelete = categoryDao.findByCategoryId(id);
        if (categoryToDelete == null) {
            throw new MyServiceException("A0004", "不存在该分类 删除失败！");
        }
        if (!categoryToDelete.isLeaf()) {
            throw new MyServiceException("A0005", "非叶子节点 不可删除！");
        }
        if (categoryToDelete.getItemCount() > 0) {
            throw new MyServiceException("A0006", "分类下存在商品，无法删除");
        }
        categoryDao.deleteById(id);
    }


}
