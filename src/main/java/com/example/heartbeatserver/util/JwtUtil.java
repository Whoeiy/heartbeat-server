package com.example.heartbeatserver.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.example.heartbeatserver.entity.Customer;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Jwt工具类
 * @author yy
 */
@Component
@ToString
@Slf4j
public class JwtUtil {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    @Value("${jwt.config.secret}")
    private String secret;

    @Value("${jwt.config.ttl}")
    private long ttl;

    /**
     * 生成token，设置token超时时间
     * @param customer
     * @return
     */
    public String createToken(Customer customer){
        Date expireDate = new Date(System.currentTimeMillis() + ttl * 1000);
        Map<String, Object> map = new HashMap<>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");
        String token = JWT.create()
                // 添加头部
                .withHeader(map)
                //可以将基本信息放到claims中
                .withClaim("id", customer.getCustomerId())
                .withClaim("phone", customer.getPhone())
                //超时设置,设置过期的日期
                .withExpiresAt(expireDate)
                //签发时间
                .withIssuedAt(new Date())
                //SECRET加密
                .sign(Algorithm.HMAC256(secret));
        return token;
    }

    public Map<String, Claim> verifyToken(String token) throws Exception {
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret)).build();
            DecodedJWT jwt = verifier.verify(token);
            return jwt.getClaims();
        } catch (TokenExpiredException e) {
            log.error("token已过期");
            throw new Exception("token已过期");
        } catch (JWTVerificationException e) {
            log.error("token不存在或不正确");
            throw new Exception("token不存在或不正确");
        }
    }
}
