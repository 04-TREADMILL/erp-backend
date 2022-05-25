package com.nju.edu.erp.auth;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.nju.edu.erp.config.JwtConfig;
import com.nju.edu.erp.exception.MyServiceException;
import com.nju.edu.erp.model.po.User;
import com.nju.edu.erp.model.vo.UserVO;
import com.nju.edu.erp.service.UserService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Aspect
@Configuration
@Order(1)
public class AuthAspect {

    private final UserService userService;

    private final JwtConfig jwtConfig;

    @Autowired
    public AuthAspect(UserService userService, JwtConfig jwtConfig) {
        this.userService = userService;
        this.jwtConfig = jwtConfig;
    }

    @Before(value = "execution(public * com.nju.edu.erp.web.controller.*.*(..)) && @annotation(authorized)")
    public void authCheck(JoinPoint joinPoint, Authorized authorized) {
        try {
            HttpServletRequest httpServletRequest = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
            String token = Optional.ofNullable(httpServletRequest.getHeader("Authorization")).
                    orElseThrow(() -> new MyServiceException("A0002", "用户未获得第三方登录授权"));
            UserVO user = userService.auth(token);

            // 判断切面方法是否包含当前token对应的角色
            if (!Arrays.stream(authorized.roles()).collect(Collectors.toList()).contains(user.getRole())) {
                throw new MyServiceException("A0003", "访问未授权");
            } else {
                // 将token的对象赋值给切面方法的user参数
                Object[] objects = joinPoint.getArgs();
                for (Object o : objects) {
                    if (o instanceof UserVO) {
                        BeanUtils.copyProperties(user, o);
                        break;
                    }
                }
            }
        }catch (MyServiceException e) {
            throw new MyServiceException("A0004", "认证失败");
        }
    }
}
