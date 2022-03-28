package com.nju.edu.erp.model.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVO {

    /**
     * 用户姓名
     */
    private String name;

    /**
     * 用户身份
     */
    private String role;

    /**
     * 用户密码
     */
    private String password;

}
