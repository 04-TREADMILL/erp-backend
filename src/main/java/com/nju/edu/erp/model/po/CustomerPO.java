package com.nju.edu.erp.model.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerPO { // 客户PO
    /**
     * 编号
     * */
    private Integer id;
    /**
     * 分类(供应商\销售商)
     */
	private String type;
    /**
     * 级别（五级，一级普通用户，五级VIP客户）
     */
    private Integer level;
    /**
     * 姓名
     */
    private String name;
    /**
     * 电话号码
     */
    private String phone;
    /**
     * 地址
     */
    private String address;
    /**
     * 邮编
     */
    private String zipcode;
    /**
     * 电子邮箱
     */
    private String email;
    /**
     * 应收额度本公司给客户的信用额度，客户欠本公司的钱的总额不能超过应收额度）
     */
    private BigDecimal lineOfCredit;
    /**
     * 应收（客户还应付给本公司但还未付的钱）
     */
    private BigDecimal receivable;
    /**
     * 应付（本公司欠客户的钱）
     */
    private BigDecimal payable;
    /**
     * 默认业务员
     */
    private String operator;
}
