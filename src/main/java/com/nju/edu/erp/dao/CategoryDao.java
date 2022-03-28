package com.nju.edu.erp.dao;

import com.nju.edu.erp.model.po.CategoryPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface CategoryDao {

    int createCategory(CategoryPO categoryPO);

    CategoryPO findByCategoryId(Integer categoryId);

    // 查询所有商品分类
    List<CategoryPO> findAll();

    // 修改商品分类
    int updateById(CategoryPO categoryPO);

    // 删除商品分类
    int deleteById(Integer id);
}
