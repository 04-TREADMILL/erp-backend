package com.nju.edu.erp.service;

import com.nju.edu.erp.model.vo.CategoryVO;

import java.util.List;

public interface CategoryService {

    /**
     * 创建分类
     * @param parentId 父节点Id
     * @param name 分类名
     * @return 创建的分类信息
     */
    CategoryVO createCategory(Integer parentId, String name);

    /**
     * 查询分类
     * @return 所有分类信息
     */
    List<CategoryVO> queryAllCategory();

    /**
     * 修改分类(name)
     * @param id 分类id
     * @param name 分类名称
     */
    CategoryVO updateCategory(Integer id, String name);

    /**
     * 删除分类
     * @param  id 分类id
     */
    void deleteCategory(Integer id);

}
