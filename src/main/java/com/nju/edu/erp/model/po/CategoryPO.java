package com.nju.edu.erp.model.po;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryPO {

    /**
     * 分类id
     */
    private Integer id;

    /**
     * 分类名
     */
    private String name;

    /**
     * 父分类ID
     */
    private Integer parentId;

    /**
     * 是否为叶节点
     */
    private boolean isLeaf;

    /**
     * 商品数量
     */
    private Integer itemCount;

    /**
     * 下一个商品index
     */
    private Integer itemIndex;
}
