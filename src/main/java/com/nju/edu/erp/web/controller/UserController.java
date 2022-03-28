package com.nju.edu.erp.web.controller;


import com.nju.edu.erp.config.JwtConfig;
import com.nju.edu.erp.dao.UserDao;
import com.nju.edu.erp.exception.MyServiceException;
import com.nju.edu.erp.model.po.User;
import com.nju.edu.erp.model.vo.UserVO;
import com.nju.edu.erp.service.ProductService;
import com.nju.edu.erp.web.Response;
import io.jsonwebtoken.Claims;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {

    private final UserDao userDao;

    private JwtConfig jwtConfig;

    @Autowired
    public UserController(UserDao userDao, JwtConfig jwtConfig) {
        this.userDao = userDao;
        this.jwtConfig = jwtConfig;
    }

    @PostMapping("/login")
    public Response userLogin(@RequestBody UserVO userVO) {
        User user = userDao.findByUsernameAndPassword(userVO.getName(), userVO.getPassword());
        if (null == user ) {
            throw new MyServiceException("A0000", "用户名或密码错误");
        }
        Map<String, String> authToken = new HashMap<>();

        String token = jwtConfig.createJWT(user);
        authToken.put("token", token);

        return Response.buildSuccess(authToken);
    }

    @PostMapping("/register")
    public Response userRegister(@RequestBody UserVO userVO) {
        User user = userDao.findByUsername(userVO.getName());
        if (user != null) {
            throw new MyServiceException("A0000", "用户名已存在");
        }
        User userSave = new User();
        BeanUtils.copyProperties(userVO, userSave);
        userDao.createUser(userSave);
        return Response.buildSuccess();
    }

    @GetMapping("/auth")
    public Response userAuth(@RequestParam(name = "token") String token) {
        Claims claims = jwtConfig.parseJWT(token);
        String name = claims.get("name", String.class);
        String role = claims.get("role", String.class);
        return Response.buildSuccess(new UserVO(name, role, ""));
    }
}
