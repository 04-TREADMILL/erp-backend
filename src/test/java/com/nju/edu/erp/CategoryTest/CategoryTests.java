package com.nju.edu.erp.CategoryTest;

import com.nju.edu.erp.dao.CategoryDao;
import com.nju.edu.erp.model.po.CategoryPO;
import com.nju.edu.erp.model.vo.CategoryVO;
import com.nju.edu.erp.service.Impl.CategoryServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;


@RunWith(MockitoJUnitRunner.class)
public class CategoryTests {

    @InjectMocks
    CategoryServiceImpl categoryService1;

    @Mock
    private CategoryDao categoryDao1;

    @Test
    public void findAllTest() {
        CategoryPO categoryPO1 = CategoryPO.builder()
                .id(1)
                .isLeaf(true)
                .itemCount(1)
                .parentId(1)
                .name("test")
                .build();
        CategoryPO categoryPO2 = CategoryPO.builder()
                .id(2)
                .isLeaf(true)
                .itemCount(2)
                .parentId(2)
                .name("test1")
                .build();
        List<CategoryPO> categoryPOList = new ArrayList<>();
        categoryPOList.add(categoryPO1);
        categoryPOList.add(categoryPO2);
        Mockito.when(categoryDao1.findAll()).thenReturn(categoryPOList);

        CategoryVO categoryVO1 = CategoryVO.builder()
                .id(1)
                .isLeaf(true)
                .itemCount(1)
                .parentId(1)
                .name("test")
                .build();
        CategoryVO categoryVO2 = CategoryVO.builder()
                .id(2)
                .isLeaf(true)
                .itemCount(2)
                .parentId(2)
                .name("test1")
                .build();

        List<CategoryVO> categoryVOList = new ArrayList<>();
        categoryVOList.add(categoryVO1);
        categoryVOList.add(categoryVO2);
        Assert.assertEquals(categoryVOList,categoryService1.queryAllCategory());
    }


}
