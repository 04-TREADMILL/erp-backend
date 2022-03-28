package com.nju.edu.erp.config;

import com.nju.edu.erp.model.po.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Data
public class JwtConfig {

    private static String secret;

    private  static long expire;

    @Value("${jwt.secret}")
    public void setJwtSecret(String jwtSecret) { //通过set让static方法读取配置文件中的值
        JwtConfig.secret = jwtSecret;
    }

    @Value("${jwt.expire}")
    public void setExpire(long expire) { //通过set让static方法读取配置文件中的值
        JwtConfig.expire = expire;
    }

    /**
     * 签发jwt
     *
     * @param user
     * @return
     */
    public String createJWT(User user) {
        Date date = new Date();
        Date expireDate = new Date(date.getTime() + expire);
        Map<String, Object> claims = new HashMap<>();
        claims.put("name", user.getName());
        claims.put("role", user.getRole());
        String jwt = Jwts.builder()
                // 设置装载内容
                .setClaims(claims)
                // 签发时间
                .setIssuedAt(date)
                // 过期时间
                .setExpiration(expireDate)
                // jwt主体，用来存放jwt的所有人，可以存用户id或者角色id
                .setSubject(user.getName())
                .compact();
        return jwt;
    }

    /**
     * 解析JWT
     *
     * @param jwt
     * @return
     */
    public Claims parseJWT(String jwt) {
        Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJwt(jwt).getBody();
        return claims;
    }

}