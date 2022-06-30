package com.nju.edu.erp.dao;

import com.nju.edu.erp.model.po.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserInfoDao {
    int createUserInfo(UserInfo userInfo);
}
